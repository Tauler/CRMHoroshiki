var horoshikiLkApp = angular.module('horoshikiLkApp', [
    'ngRoute',
    'ngSanitize',
    'ngResource',
    'ngCookies',

    'layoutControllers',
    'errorControllers',
    'mainControllers',
	'loginControllers',

    'backendServices',
    'accountServices',
    'translationServices',

    'ngSimpleUpload',
	
	'ui.mask'
]);

horoshikiLkApp.config(['$locationProvider', '$httpProvider', '$routeProvider',
    function($locationProvider, $httpProvider, $routeProvider) {
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $httpProvider.defaults.xsrfHeaderName = "X-CSRF-TOKEN";
        $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!');

        $routeProvider
            .when('/', {
                templateUrl: '/angular-html/main.view.html',
                controller: 'MainViewController',
                pageId: 'mainViewPage',
                title: 'Главная страница'
            })
             .when('/error', {
                templateUrl: '/angular-html/error.view.html',
                controller: 'ErrorViewController',
				pageId: 'errorViewPage',
                title: 'Что-то пошло не так ...'
            })
			.when('/account/login', {
                templateUrl: '/angular-html/login.view.html',
                controller: 'LoginViewController',
				pageId: 'loginViewPage',
                title: 'Авторизация'
            })
			.when('/account/registration', {
                templateUrl: '/angular-html/registration.view.html',
                controller: 'RegistrationViewController',
				pageId: 'registerViewPage',
                title: 'Регистрация'
            })
			.when('/account/confirm', {
                templateUrl: '/angular-html/confirm.view.html',
                controller: 'ConfirmViewController',
				pageId: 'confirmViewPage',
                title: 'Регистрация'
            })
			.when('/account/profile', {
                templateUrl: '/angular-html/profile.view.html',
                controller: 'ProfileViewController',
				pageId: 'profileViewPage',
                title: 'Профиль пользователя'
            })
            .otherwise({
                redirectTo: '/'
            });
    }
]);

horoshikiLkApp.run(['$route', '$rootScope', '$location', function ($route, $rootScope, $location) {
    var original = $location.path;
    $location.path = function (path, reload) {
        if (reload === false) {
            var lastRoute = $route.current;
            var un = $rootScope.$on('$locationChangeSuccess', function () {
                $route.current = lastRoute;
                un();
            });
        }
        return original.apply($location, [path]);
    }
}]);
