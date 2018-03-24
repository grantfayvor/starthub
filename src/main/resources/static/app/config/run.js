/**
 * Created by Harrison on 3/19/2018.
 */

app.run(['$http', '$rootScope', '$cookies', '$state', '$timeout', function ($http, $rootScope, $cookies, $state, $timeout) {

    var token = window.sessionStorage.getItem('authorization');

    if (token) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + token;
        $http.get('/oauth/check_token?token=' + token)
            .then(function (response) {
                $rootScope.isAuthorized = typeof response.data != 'undefined' || response.data != null;
                if($rootScope.isAuthorized) $timeout(function () { $state.go('home'); });
                else $timeout(function () { 
                    delete $http.defaults.headers.common.Authorization;
                    window.sessionStorage.removeItem('authorization');
                    $state.go('login'); 
                });
            }, function (response) {
                $rootScope.isAuthorized = false;
                $timeout(function () { 
                    delete $http.defaults.headers.common.Authorization;
                    window.sessionStorage.removeItem('authorization');
                    $state.go('login'); 
                });
            });
    } else {
        $timeout(function () { $state.go('login'); });
    }

    // $rootScope.$on('$stateChangeStart', function (event, toState) {
    //     if (!angular.isFunction(toState.data.rule)) {
    //         event.preventDefault();
    //         return;
    //     }

    //     if (!$rootScope.isAuthorized) {
    //         event.preventDefault();
    //         $timeout(function () { $state.go('login'); });
    //     }

    //     event.preventDefault();
    //     $timeout(function () { $state.go(toState); });
    // });
}]);