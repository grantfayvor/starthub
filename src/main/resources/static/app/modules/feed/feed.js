/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('FeedController', ['$rootScope', '$scope', '$state', '$stateParam', 'FeedService',
    function ($rootScope, $scope, $state, $stateParam, FeedService) {

        $scope.feeds = [];

        $scope.getIdeasFeed = function () {
            FeedService.getIdeasFeed(function (response) {
                $scope.feeds = response;
            }, function (response) {
                console.log(response);
            });
        };
    }
]);

app.service('FeedService', ['APIService', function (APIService) {

    this.getIdeasFeed = function (successHandler, errorHandler) {
        APIService.get('/api/feed', successHandler, errorHandler);
    };
}]);