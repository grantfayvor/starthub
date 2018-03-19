/**
 * Created by Harrison on 3/17/2018.
 */

app.controller('AuthController', ['$rootScope', '$scope', 'AuthService',
    function ($rootScope, $scope, AuthService) {

        $scope.user = {};

        $scope.register = function () {
            AuthService.registerUser($scope.user, function (response) {
                $scope.login();
            }, function (response) {
                console.log("an error occurred while trying to register the user");
            });
        };

        $scope.login = function () {
            AuthService.login($scope.user.username, $scope.user.password, function (response) {
                window.sessionStorage.setItem('authorization', response.data);
                AuthService.setHttpAuthorizationHeader(response.data);
            }, function (response) {

            });
        };
    }
]);

app.service('AuthService', ['$http', 'APIService', 'authInfo', 'baseUrl', function ($http, APIService, authInfo, baseUrl) {

    this.login = function (username, password, successHandler, errorHandler) {
        APIService.post(authInfo.clientId + ':' + authInfo.clientSecret + '@' + baseUrl +
            '/oauth/token?username=' + username + '&password=' + password + '&grant_type=' + authInfo.grantType,
            successHandler, errorHandler);
    };

    this.registerUser = function (userDetails, successHandler, errorHandler) {
        APIService.post('/api/user/register', userDetails, successHandler, errorHandler);
    };

    this.setHttpAuthorizationHeader = function (data) {
        $http.defaults.headers.common['Authorization'] = 'Bearer ' + data;
    };

}]);