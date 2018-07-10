/**
 * Created by Harrison on 03/03/2018.
 */

app.controller('IdeaController', ['$rootScope', '$scope', '$state', '$timeout', 'IdeaService', 'TagService', 'AlertService',
    function ($rootScope, $scope, $state, $timeout, IdeaService, TagService, AlertService) {

        $scope.idea = {};
        $scope.ideas = [];
        $scope.success = false;

        $scope.postIdea = function () {
            $scope.idea.tags = [];
            for (var i = 0; i < $('#tags').val().length; i++) {
                var data = $('#tags').val()[i].split(",");
                $scope.idea['tags[' + i + '].id'] = data[0];
                $scope.idea['tags[' + i + '].name'] = data[1];
            }
            // $('#tags').val().forEach(function (tag) {
            //     var data = tag.split(",");
            //     $scope.idea.tags.push({
            //         id: data[0],
            //         name: data[1]
            //     });
            // });
            Pace.restart();
            IdeaService.addIdea($scope.idea, function (response) {
                if (Boolean(response.data) === true || response.data === true) {
                    $scope.idea = {};
                    $('.chosen-choices').html('<li class="search-field"><input type="text" value="Choose related tags..." class="default" autocomplete="off" style="width: 152.203px;" tabindex="4"></li>');
                    AlertService.alertify("The Idea was successfully posted", false);
                } else {
                    AlertService.alertify("An error occurred while trying to post the idea. Please try again", false);
                }

            }, function (response) {
                console.error(response);
                AlertService.alertify("An error occurred while trying to post the idea. Please try again", false);
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

app.service('IdeaService', ['Upload', 'APIService', 'ideaUrl', function (Upload, APIService, ideaUrl) {

    // this.addIdea = function (idea, successHandler, errorHandler) {
    //     APIService.post(ideaUrl, idea, successHandler, errorHandler);
    // };

    this.addIdea = function (idea, successHandler, errorHandler) {
        Upload.upload({
            url: ideaUrl,
            data: idea,
            method: 'POST'
        }).then(successHandler, errorHandler, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

    this.getRecentIdeas = function (successHandler, errorHandler) {
        APIService.get(ideaUrl, successHandler, errorHandler);
    };
}]);