/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('FeedController', ['$rootScope', '$scope', '$state', '$timeout', 'FeedService',
    function ($rootScope, $scope, $state, $timeout, FeedService) {

        $scope.feeds = [];
        var SUBSCRIBER_ID = 'feed-subscriber-007';
        
        $timeout(function() {
            $(".note-editable").attr("contenteditable","false");
        }, 10);

        FeedService.subscribeToService('/exchange/feed', {id: SUBSCRIBER_ID}, function (feed) {
            $scope.feed = JSON.parse(feed.body);
            $scope.feeds.content = $scope.feeds.content.map(function (feed) {
                return feed.id == $scope.feed.id ? $scope.feed : feed;
            });
            $timeout(function() {
                $scope.$apply();
            });
        });

        $scope.getIdeasFeed = function (pageNumber, pageSize) {
            FeedService.getIdeasFeed(pageNumber, pageSize, function (response) {
                $scope.feeds = response.data;
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

        $scope.viewPost = function(post) {
            $scope.post = post;
        };

        $scope.$on('$destroy', function() {
            FeedService.unsubscribeFromService(SUBSCRIBER_ID, {});
            FeedService.disconnectSocket();
        });
    }
]);

app.service('FeedService', ['APIService', 'feedUrl', 'socketProvider', function (APIService, feedUrl, socketProvider) {

    var self = this;

    this.getIdeasFeed = function (pageNumber, pageSize, successHandler, errorHandler) {
        APIService.get(feedUrl + '?pageNumber=' + pageNumber + '&pageSize=' + pageSize,
            successHandler, errorHandler);
    };

    this.vote = function (data) {
        socketProvider.sendMessage('/ws/vote', JSON.stringify(data), {});
    };

    this.subscribeToService = function (subscriber, options, onMessageReceived) {
        socketProvider.subscribe(subscriber, options, onMessageReceived);
    };

    this.unsubscribeFromService = function(id, options) {
        socketProvider.unsubscribe(id, options);
    };

    this.disconnectSocket = function() {
        socketProvider.disconnectSocket();
    };

    this.setSubscription = function (id, callback) {
        socketProvider.getStompClient().subscriptions[id] = callback;
    };

    this.getStompClient = function() {
        return socketProvider.getStompClient();
    };

    this.getSocket = function () {
        return socketProvider.getSocket();
    };

    this.executeRequest = function (subscriber, onMessageReceived, recipient, data, options) {
        socketProvider.executeRequest(subscriber, onMessageReceived, recipient, data, options);
    };
}]);