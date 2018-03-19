/**
 * Created by Harrison on 3/19/2018.
 */

app.run(['$http', '$rootScope', '$cookies', '$state', function ($http, $rootScope, $cookies, $state) {

    var token = window.sessionStorage.getItem('authorization');

    if (token) {
        $http.get('/oauth/check_token?token=' + token)
            .then(function (response) {
                console.log(response.data);
                $rootScope.isAuthorized = typeof response.data != 'undefined' || response.data != null;
            }, function (response) {
                $rootScope.authorized = false;
            });
    }

    $rootScope.$on('$stateChangeStart', function (event, toState) {
        if (!angular.isFunction(toState.data.rule)) {
            event.preventDefault();
            return;
        }

        if (!$rootScope.isAuthorized) {
            event.preventDefault();
            $state.go('login');
        }

        event.preventDefault();
        $state.go(toState);
    });
}]);