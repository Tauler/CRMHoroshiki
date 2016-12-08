var layoutControllers = angular.module('layoutControllers', []);

layoutControllers.controller('HeaderController', ['$scope', '$rootScope',
    function ($scope, $rootScope) {

        $rootScope.$on('$routeChangeSuccess', function (e, current, pre) {
            $scope.title = current.title;
        });
    }
]);

layoutControllers.controller('LayoutController', ['$scope', '$rootScope', '$location', 'TranslationService', 'AccountService', 'BackendService',
    function ($scope, $rootScope, $location, TranslationService, AccountService, BackendService) {

        $rootScope.$on('$routeChangeSuccess', function (e, current, pre) {
            $scope.pageId = current.pageId;
        });

        //Подгружаем перевод на выбранный язык
        $scope.translate = function () {
            TranslationService.getTranslation($scope);
        };
        $scope.translate();
		
		//Текущий пользователь
		$scope.initCurrentUserModel = function(){
            $rootScope.currentUser = {};
            $rootScope.currentUserLoaded = false;
        }
        $scope.initCurrentUserModel();
		
		$scope.getCurrentUser = function(){
			AccountService.getCurrentUser().success(function (result) {
				if (result.success == true) {
					$rootScope.currentUser = result.data;
                    $rootScope.currentUserLoaded = true;
                    $rootScope.$broadcast('currentUserLoadedEvent');
				} else {
					displayErrorMessage($scope.translation[result.reason]);
				}
			}).error(function (result, status) {
				if(status == 403){
					if(publicUrls.indexOf($location.url()) == -1){
						$location.url("/account/login?backUrl="+$location.url());
					}
				}else{
					httpErrors($location.url(), status);
				}
			});
		}

        // Проверка доступности бэкэнда
        BackendService.checkIsAvailable().success(function(result){
            if(result.success == true){
                $scope.getCurrentUser();
			}else{
				redirectBackendError();
			}
        }).error(function(result, status){
            httpErrors($location.url(), status);
        });

        //Выход
        $scope.logoutButton = function () {
            AccountService.logoutAccount().success(function (result) {
				if (result.success == true) {
					redirect('/account/login');
				} else {
					displayErrorMessage($scope.translation[result.reason]);
				}
			}).error(function (result, status) {
				httpErrors($location.url(), status);
			});
        }
    }
]);
