/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('IdeaController', ['$rootScope', '$scope', '$state', '$timeout' /*, '$stateParam'*/ , 'IdeaService', 'TagService',
    function ($rootScope, $scope, $state, $timeout /*, $stateParam*/ , IdeaService, TagService) {

        $scope.idea = {};
        $scope.ideas = [];
        $scope.success = false;

        $scope.postIdea = function () {
            $scope.idea.tags = [];
            $('#tags').val().forEach(function(tag) {
                $scope.idea.tags.push({name : tag});
            });
            // $scope.idea.tags = $('#tags').chosen().val();
            IdeaService.addIdea($scope.idea, function (response) {
                if (response.data === true) {
                    console.log("the post has successfully been sent");
                    $scope.idea = {};
                    $scope.sumbitMessage = "The Idea was successfully posted";
                    $scope.success = true;
                } else {
                    $scope.sumbitMessage = "An error occurred while trying to post the idea. Please try again";
                    $scope.success = false;
                }

            }, function (response) {
                console.error(response);
                $scope.sumbitMessage = "An error occurred while trying to post the idea. Please try again";
                $scope.success = false;
            });
        };

        $scope.getRecentIdeas = function () {
            IdeaService.getRecentIdeas(function (response) {
                $scope.ideas = response.data;
            }, function (response) {
                console.error(response);
            });
        };

        $scope.getTags = function () {
            TagService.getTags(function (response) {
                $scope.tags = response.data;
                $timeout(function () {
                    $('#tags').chosen({
                        width: '100%'
                    });
                });
            }, function (response) {
                console.log("an error occurred while trying to fetch the tags");
            });
        };
    }
]);

app.service('IdeaService', ['APIService', function (APIService) {

    this.addIdea = function (idea, successHandler, errorHandler) {
        APIService.post('/api/idea', idea, successHandler, errorHandler);
    };

    this.getRecentIdeas = function (successHandler, errorHandler) {
        APIService.get('/api/idea', successHandler, errorHandler);
    };
}]);