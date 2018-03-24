/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('FeedController', ['$rootScope', '$scope', '$state', 'FeedService',
    function ($rootScope, $scope, $state, FeedService) {

        $scope.feeds = [];

        FeedService.subscribeSocket(function (feed) {
            $scope.feed = JSON.parse(feed);
            $scope.feeds.filter(function(feed) {
                feed.id == $scope.feed.id;
            }).map(function(feed) {
                feed = $scope.feed;
            });
            console.log("######################33 the particular feed is");
            console.log($scope.feed);
            console.log("=========== i just updated feeds"); 
            console.log($scope.feeds);
        });

        $scope.getIdeasFeed = function () {
            FeedService.getIdeasFeed(function (response) {
                $scope.feeds = response.data;
            }, function (response) {
                console.log(response);
            });
        };

        $scope.vote = function (upVote, feedId) {
            console.log("the current vote is " + upVote);
            FeedService.vote(upVote, feedId);
        };
    }
]);

app.service('FeedService', ['APIService', 'feedUrl', 'socketProvider', function (APIService, feedUrl, socketProvider) {

    var self = this;
    
    this.getIdeasFeed = function (successHandler, errorHandler) {
        APIService.get(feedUrl, successHandler, errorHandler);
    };

    this.vote = function (upVote, feedId) {
        socketProvider.sendMessage('/ws/vote', { upVote: upVote, feedId: feedId });
    };

    this.subscribeSocket = function (publisher) {
        socketProvider.subscribe('/topic/feed', publisher);
    };
}]);