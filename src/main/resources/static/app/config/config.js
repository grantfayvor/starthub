var app = angular.module('starthub', ['ui.router', 'ngCookies', 'summernote']);

app.config(['$httpProvider', '$interpolateProvider', '$locationProvider', '$stateProvider', '$urlRouterProvider', /* 'socketProvider', */
function ($httpProvider, $interpolateProvider, $locationProvider, $stateProvider, $urlRouterProvider/*, socketProvider*/) {

        $httpProvider.defaults.headers.common.Accept = "application/json";
        $httpProvider.defaults.headers.common['Content-Type'] = "application/json";
        // $httpProvider.defaults.useXDomain = true;

        $locationProvider.html5Mode(true);

        $urlRouterProvider.otherwise('/');
        $urlRouterProvider.when('/', 'dashboard');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: '/app/home.html',
                controller: 'MainController'
            })
            .state('home.dashboard', {
                url: 'dashboard',
                templateUrl: '/app/dashboard.html',
                controller: 'MainController'
            })
            .state('home.new-idea', {
                url: 'new-idea',
                templateUrl: '/app/modules/idea/new-idea.html',
                controller: 'IdeaController'
            })
            .state('home.my-ideas', {
                url: 'my-ideas',
                templateUrl: '/app/modules/idea/view-ideas.html',
                controller: 'IdeaController'
            })
            .state('home.drafts', {
                url: 'drafts',
                templateUrl: '/app/modules/idea/drafts.html',
                controller: 'IdeaController'
            })
            .state('home.feed', {
                url: 'feed',
                templateUrl: '/app/modules/feed/view-feed.html',
                controller: 'FeedController'
                // onExit: socketProvider.disconnectSocket()
            })
            .state('home.view-users', {
                url: 'view-users',
                templateUrl: '/app/modules/user/view-users.html',
                controller: 'UserController'
            })
            .state('login', {
                url: '/login',
                templateUrl: '/app/modules/auth/login.html',
                controller: 'AuthController'
            })
            .state('register', {
                url: '/register',
                templateUrl: '/app/modules/auth/register.html',
                controller: 'AuthController'
            });

    }
]);
