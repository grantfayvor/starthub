/**
 * Created by Harrison on 3/17/2018.
 */

app.controller('AuthController', ['$rootScope', '$scope', '$state', 'AuthService',
    function ($rootScope, $scope, $state, AuthService) {
        
        $rootScope.$state = $state;
        $scope.user = {};

        $scope.register = function () {
            AuthService.registerUser($scope.user, function (response) {
                if(Boolean(response.data) === true || response.data === true) $scope.login();
                else console.log("an error occurred while trying to register the user");
            }, function (response) {
                console.log("an error occurred while trying to register the user");
            });
        };

        $scope.login = function () {
            console.log($scope.user.username + ' === ' + $scope.user.password);
            AuthService.login($scope.user.username, $scope.user.password, function (response) {
                window.sessionStorage.setItem('authorization', response.data.access_token);
                AuthService.setHttpAuthorizationHeader(response.data.access_token);
                $state.go('home.dashboard');
            }, function (response) {
                console.log("an error occurred while trying to login to the system");
                console.log(response);
            });
        };
    }
]);

app.service('AuthService', ['$http', 'APIService', 'authInfo', 'baseUrl', 'userUrl', 
    function ($http, APIService, authInfo, baseUrl, userUrl) {

    this.login = function (username, password, successHandler, errorHandler) {
        APIService.post('http://' + authInfo.clientId + ':' + authInfo.clientSecret + '@' + baseUrl +
            '/oauth/token?username=' + username + '&password=' + password + '&grant_type=' + authInfo.grantType, {},
            successHandler, errorHandler);
    };

    this.registerUser = function (userDetails, successHandler, errorHandler) {
        APIService.post(userUrl + '/register', userDetails, successHandler, errorHandler);
    };

    this.setHttpAuthorizationHeader = function (data) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + data;
    };

}]);