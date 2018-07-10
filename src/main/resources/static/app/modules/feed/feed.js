/**
 * Created by Harrison on 03/03/2018.
 */
(function () {
    app.controller('FeedController', ['$rootScope', '$scope', '$state', '$timeout', 'FeedService', 'RankService', 'AlertService',
        function ($rootScope, $scope, $state, $timeout, FeedService, RankService, AlertService) {

            $scope.feeds = {
                content: []
            };
            var SUBSCRIBER_ID = 'feed-subscriber-' + Math.floor(Math.random() * Math.floor(999));

            FeedService.subscribeToService('/exchange/feed', {
                id: SUBSCRIBER_ID
            }, function (feed) {
                $scope.feed = JSON.parse(feed.body);
                $scope.feeds.content = $scope.feeds.content.map(function (feed) {
                    return feed.id == $scope.feed.id ? $scope.feed : feed;
                });
                $timeout(function () {
                    $scope.$apply();
                });
            });

            $scope.getIdeasFeed = function (pageNumber, pageSize) {
                FeedService.getIdeasFeed(pageNumber, pageSize, function (response) {
                    if (response && response.data && response.data.content.length > 0) {
                        var previousContents = $scope.feeds.content;
                        $scope.feeds = response.data;
                        if (previousContents.length > 0) {
                            if (previousContents.length >= 30) {
                                $scope.feeds.content.splice(0, 10);
                            }
                            $scope.feeds.content.unshift(previousContents);
                        }
                    } else {
                        if (document.getElementsByClassName('alert-primary')) {
                            $('.alert-primary').remove();
                        }
                        $timeout(function () {
                            AlertService.alertify("you've reached the end of the page", true);
                        }, 10);
                    }
                }, function (response) {
                    console.log(response);
                });
            };

            $scope.vote = function (upVote, feedId) {
                Pace.restart();
                FeedService.vote({
                    upVote: upVote,
                    feedId: feedId
                });
            };

            $scope.viewPost = function (feed) {
                Pace.restart();
                $scope.feed = feed;
                $scope.feed.idea.createdAt = $scope.convertDate(feed.idea.createdAt);
                $scope.noOfStars = RankService.calcRankMean($scope.feed.rank);
                $scope.starArray = [];
                for (var i = 0; i < 5; i++) {
                    $scope.starArray.push(i);
                }
                $('#feed-modal').modal('show');
            };

            $scope.transferFunds = function() {
                FeedService.transferFunds(); // complete the function to transfer funds
            };

            $scope.convertDate = function (date) {
                return new Date(parseInt(date)).toUTCString();
            };

            $scope.getTimeSincePost = function (date) {
                date = new Date(parseInt(date));
                var today = new Date();
                var millisecondsDifference = (today - date); // milliseconds between now & date
                var secondsDifference = Math.round(millisecondsDifference / 1000); //seconds
                var minutesDifference = Math.round(millisecondsDifference / 60000); // minutes
                var hoursDifference = Math.floor(millisecondsDifference / 3600000); // hours
                var daysDifference = Math.floor(millisecondsDifference / 86400000); // days
                if (daysDifference >= 1) {
                    return Math.round(daysDifference) + " days";
                } else if (hoursDifference >= 1) {
                    return Math.round(hoursDifference) + " hours";
                } else if (minutesDifference >= 1) {
                    return Math.round(minutesDifference) + " minutes";
                } else {
                    return Math.round(secondsDifference) + " seconds";
                }
            };

            $scope.$on('$destroy', function () {
                FeedService.unsubscribeFromService(SUBSCRIBER_ID, {});
                FeedService.disconnectSocket();
            });
        }
    ]);

    app.service('FeedService', ['APIService', 'feedUrl', 'walletUrl', 'socketProvider', function (APIService, feedUrl, walletUrl, socketProvider) {

        var self = this;

        this.getIdeasFeed = function (pageNumber, pageSize, successHandler, errorHandler) {
            APIService.get(feedUrl + '?pageNumber=' + pageNumber + '&pageSize=' + pageSize,
                successHandler, errorHandler);
        };

        this.transferFunds = function (wallet, amount, recipient, successHandler, errorHandler) {
            APIService.post(`${walletUrl}/transfer-funds?amount=${amount}&recipient=${recipient}`,
                wallet, successHandler, errorHandler);
        };

        this.vote = function (data) {
            socketProvider.sendMessage('/ws/vote', JSON.stringify(data), {});
        };

        this.subscribeToService = function (subscriber, options, onMessageReceived) {
            socketProvider.subscribe(subscriber, options, onMessageReceived);
        };

        this.unsubscribeFromService = function (id, options) {
            socketProvider.unsubscribe(id, options);
        };

        this.disconnectSocket = function () {
            socketProvider.disconnectSocket();
        };

        this.setSubscription = function (id, callback) {
            socketProvider.getStompClient().subscriptions[id] = callback;
        };

        this.getStompClient = function () {
            return socketProvider.getStompClient();
        };

        this.getSocket = function () {
            return socketProvider.getSocket();
        };

        this.executeRequest = function (subscriber, onMessageReceived, recipient, data, options) {
            socketProvider.executeRequest(subscriber, onMessageReceived, recipient, data, options);
        };
    }]);
})();