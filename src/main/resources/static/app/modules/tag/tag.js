(function () {
    app.factory('TagService', ['APIService', 'tagUrl', function (APIService, tagUrl) {

        return {
            getTags: function (successHandler, errorHandler) {
                APIService.get(tagUrl, successHandler, errorHandler);
            }
        };
    }]);
})();