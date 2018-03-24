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
                var data = tag.split(",");
                $scope.idea.tags.push({
                    id: data[0], 
                    name : data[1]
                });
            });
            IdeaService.addIdea($scope.idea, function (response) {
                if (Boolean(response.data) === true || response.data === true) {
                    $scope.idea = {};
                    $('.chosen-choices').html('<li class="search-field"><input type="text" value="Choose related tags..." class="default" autocomplete="off" style="width: 152.203px;" tabindex="4"></li>');
                    $scope.submitMessage = "The Idea was successfully posted";
                    $scope.success = true;
                } else {
                    $scope.submitMessage = "An error occurred while trying to post the idea. Please try again";
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

app.service('IdeaService', ['APIService', 'ideaUrl', function (APIService, ideaUrl) {

    this.addIdea = function (idea, successHandler, errorHandler) {
        APIService.post(ideaUrl, idea, successHandler, errorHandler);
    };

    this.getRecentIdeas = function (successHandler, errorHandler) {
        APIService.get(ideaUrl, successHandler, errorHandler);
    };
}]);