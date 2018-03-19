/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('IdeaController', ['$rootScope', '$scope', '$state'/*, '$stateParam'*/, 'IdeaService', 'TagService',
    function ($rootScope, $scope, $state/*, $stateParam*/, IdeaService, TagService) {

        $scope.idea = {};
        $scope.ideas = [];

        $scope.postIdea = function () {
            IdeaService.addIdea($scope.idea, function (response) {

            }, function (response) {
                console.error(response);
            });
        };

        $scope.getRecentIdeas = function () {
            IdeaService.getRecentIdeas(function (response) {
                $scope.ideas = response.data;
            }, function (response) {
                console.error(response);
            });
        };

        $scope.getTags = function() {
            TagService.getTags(function(response) {
                $scope.tags = response.data;
            }, function (response) {
                console.log("an error occurred while trying to fetch the tags");
            });
        };
}]);

app.service('IdeaService', ['APIService', function (APIService) {

    this.addIdea = function (idea, successHandler, errorHandler) {
        APIService.post('/api/idea', idea, successHandler, errorHandler);
    };

    this.getRecentIdeas = function (successHandler, errorHandler) {
        APIService.get('/api/idea', successHandler, errorHandler);
    };
}]);