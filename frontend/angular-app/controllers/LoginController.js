var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('LoginViewController', ['$scope', '$location', 'AccountService',
    function ($scope, $location, AccountService) {

        var backUrl = $location.$$search.backUrl;

        if(backUrl == undefined || backUrl == true || backUrl == ""){
            backUrl = "/";
        }
		
		$scope.phone = {};
        $scope.phone.maskTemplate = phoneMaskTemplate;
		
		$scope.changePwd = function(){
			$scope.showPasswordError = false;
		};

        //Вход
        $scope.submitLoginForm = function(){
            AccountService.loginAccount($scope.login, $scope.password).success(function(result){
                if(result.success == true){
                    redirect(backUrl);
                }else{
                    $scope.showPasswordError = true;
                }
            }).error(function(result, status){
                httpErrors($location.url(), status);
            });
        }
    }
]);

loginControllers.controller('RegistrationViewController', ['$scope', '$location', 'AccountService',
    function ($scope, $location, AccountService) {

		$scope.phone = {};
        $scope.phone.maskTemplate = phoneMaskTemplate;
		
		$scope.page = {};
		$scope.page.showRegistration = true;
		$scope.page.showConfirm = false;
		// $scope.page.showRegistration = false;
		// $scope.page.showConfirm = true;
		
		$scope.availablePaymentMethods = ['CARD', 'CASH', 'DELIVERY_CARD'];
		
		$scope.user = {};
		
		$scope.user.login = '';
		$scope.user.password = '';
		$scope.user.name = '';
		$scope.user.mail = '';
		$scope.user.address = '';
		$scope.user.intercom = '';
		$scope.user.storey = '';
		$scope.user.access = '';
		$scope.user.apartment = '';
		$scope.user.comment = '';
		$scope.user.paymentMethod = $scope.availablePaymentMethods[0];
		$scope.user.code = '';
		$scope.user.id = 0;
		
		$scope.submitRegisterForm = function(){
			AccountService.registerAccount($scope.user.id, $scope.user.login, $scope.user.password, $scope.user.name, $scope.user.mail, $scope.user.address, $scope.user.intercom, $scope.user.storey, $scope.user.access, $scope.user.apartment, $scope.user.comment, $scope.user.paymentMethod).success(function(result){
				if(result.success == true){
                    $scope.showConfirmPage();
					$scope.user.id = result.data;
                }else{
                    
                }
            }).error(function(result, status){
                httpErrors($location.url(), status);
            });
		}
		
		$scope.error = {};
		
		$scope.error.login = false;
		$scope.error.loginExists = false;
		$scope.error.password = false;
		$scope.error.name = false;
		$scope.error.mail = false;
		$scope.error.address = false;
		$scope.error.intercom = false;
		$scope.error.storey = false;
		$scope.error.access = false;
		$scope.error.apartment = false;
		$scope.error.comment = false;
		$scope.error.paymentMethod = false;
		$scope.error.code = false;

		$scope.validateName = function(){
			$scope.error.name = false;
			if($scope.user.name == null || $scope.user.name == undefined || $scope.user.name.length == 0){
				$scope.error.name = true;
			}
		}
		
		$scope.validatePhone = function(){
			$scope.error.login = false;
			$scope.error.loginExists = false;
			if(!userPhoneRegexp.test($scope.user.login)){
				$scope.error.login = true;
			}else{
				AccountService.isLoginExists($scope.user.login, true).success(function(result){
					if(result.success == true){
						if(result.data == true){
							$scope.error.loginExists = true;
						}
					}else{
						displayErrorMessage($scope.translation[result.reason]);
					}
				}).error(function(result, status){
					httpErrors($location.url(), status);
				});
			}
		}
		
		$scope.validateMail = function(){
			$scope.error.mail = false;
			if(!mailRegexp.test($scope.user.mail)){
				$scope.error.mail = true;
			}
        }
		
		$scope.checkIsEmptyMail = function(){
			if($scope.user.mail.length == 0){
				$scope.error.mail = false;
			}
		}
		
		$scope.validatePassword = function(){
			$scope.error.password = false;
			if(!passwordRegexp.test($scope.user.password)){
				$scope.error.password = true;
			}
        }
		
		$scope.validateAddress = function(){
			$scope.error.address = false;
			if($scope.user.address == null || $scope.user.address == undefined || $scope.user.address.length == 0){
				$scope.error.address = true;
			}
		}
		
		$scope.validatePaymentMethod = function(){
			$scope.error.paymentMethod = false;
			if($scope.availablePaymentMethods.indexOf($scope.user.paymentMethod) == -1){
				$scope.error.paymentMethod = true;
			}
		}
		
		$scope.firstBlockCompleted = function(){
			if($scope.user.login == undefined){ // ui.mask workaround
				return $scope.user.name.length > 0 && $scope.user.password.length > 0;
			}else{
				return $scope.user.name.length > 0 && $scope.user.login.length > 0 && $scope.user.password.length > 0;
			}
		}
		
		$scope.secondBlockCompleted = function(){
			return $scope.firstBlockCompleted() && $scope.user.address.length > 0;
		}
		
		$scope.thirdBlockCompleted = function(){
			return $scope.secondBlockCompleted() && $scope.user.paymentMethod.length > 0;
		}
		
		$scope.firstBlockHaveErrors = function(){
			return $scope.error.name == true || $scope.error.login == true || $scope.error.loginExists == true || $scope.error.mail == true || $scope.error.password == true;
		}
		
		$scope.secondBlockHaveErrors = function(){
			return $scope.error.address == true;
		}
		
		$scope.thirdBlockHaveErrors = function(){
			return $scope.error.paymentMethod == true;
		}
		
		$scope.formIsValid = function(){
			return $scope.thirdBlockCompleted() && !$scope.firstBlockHaveErrors() && !$scope.secondBlockHaveErrors() && !$scope.thirdBlockHaveErrors();
		}
		
		$scope.showComment = false;
		$scope.showCommentField = function(){
			if($scope.showComment){
				$scope.showComment = false;
			}else{
				$scope.showComment = true;
			}
		}
		
		$scope.showConfirmPage = function(){
			$scope.page.showRegistration = false;
			$scope.page.showConfirm = true;
			
			$location.path('/account/confirm', false);
		}
		
		$scope.showRegistrationPage = function(){
			$scope.page.showRegistration = true;
			$scope.page.showConfirm = false;
			
			$scope.user.login = '';
			$location.path('/account/registration', false);
			
			setTimeout(function(){
				$("#login").focus();
			}, 1200);
		}
		
		$scope.validateConfirmCode = function(){
			$scope.error.code = false;
			if(!numberRegexp.test($scope.user.code)){
				$scope.error.code = true;
			}else{
				if($scope.user.code.length == 4){
					AccountService.registerConfirm($scope.user.id, $scope.user.code).success(function(result){
						if(result.success == true){
							$location.path('/account/login');
						}else{
							$scope.error.code = true;
						}
					}).error(function(result, status){
						httpErrors($location.url(), status);
					});
				}
			}
		}
		
		$scope.resendConfirmCode = function(){
			AccountService.resendConfirmSms($scope.user.id).success(function(result){
				if(result.success == true){
                    
                }else{
                    displayErrorMessage($scope.translation[result.reason]);
                }
            }).error(function(result, status){
                httpErrors($location.url(), status);
            });
		}
    }
]);

loginControllers.controller('ConfirmViewController', ['$scope', '$location',
    function ($scope, $location) {
		$location.path('/account/registration');
	}
]);

loginControllers.controller('ProfileViewController', ['$scope', '$rootScope', '$location', 'AccountService',
    function ($scope, $rootScope, $location, AccountService) {

		$scope.currentUser = {};
		$scope.urlAvatar = "";

		$scope.myCallback = function (valueFromDirective) {
			$scope.$apply(function () {
				$scope.urlAvatar = valueFromDirective.data;
			});
		};

		$rootScope.$watch('currentUserLoaded', function(){
			if($rootScope.currentUserLoaded == true){
				$scope.currentUser = $rootScope.currentUser;
			}
		});
		
		$scope.reloadCurrentUser = function(){
			AccountService.getCurrentUser().success(function (result) {
				if (result.success == true) {
					$scope.currentUser = result.data;
				} else {
					displayErrorMessage($scope.translation[result.reason]);
				}
			}).error(function (result, status) {
				httpErrors($location.url(), status);
			});
		}
		
		// Модальник добавления и изменения адреса
		$scope.addAddressModal = function(address){
			if(address != null){
				$scope.newAddress = address;
				
				$scope.newAddress.isNew = false;
				$scope.newAddress.showComment = false;
				
				if($scope.newAddress.comment.length > 0){
					$scope.newAddress.showComment = true;
				}
			}else{
				$scope.newAddress = {};
				
				$scope.newAddress.id = 0;
				$scope.newAddress.address = "";
				$scope.newAddress.intercom = "";
				$scope.newAddress.storey = "";
				$scope.newAddress.access = "";
				$scope.newAddress.apartment = "";
				$scope.newAddress.comment = "";
				
				$scope.newAddress.isNew = true;
				$scope.newAddress.showComment = false;
			}
			
			$scope.newAddress.error = {};
			$scope.newAddress.error.address = false;
		}
		
		$scope.addAddressModalShowComment = function(){
			if($scope.newAddress.showComment){
				$scope.newAddress.showComment = false;
			}else{
				$scope.newAddress.showComment = true;
			}
		}
		
		$scope.validateAddress = function(){
			$scope.newAddress.error.address = false;
			if($scope.newAddress.address == null || $scope.newAddress.address == undefined || $scope.newAddress.address.length == 0){
				$scope.newAddress.error.address = true;
			}
		}
		
		$scope.addAddressModalSubmit = function(){

			if(!$scope.newAddress.error.address){
				AccountService.editAddress($scope.newAddress.id, $scope.newAddress.address, $scope.newAddress.intercom, $scope.newAddress.storey, $scope.newAddress.access, $scope.newAddress.apartment, $scope.newAddress.comment).success(function(result){
					if(result.success == true){
						$scope.reloadCurrentUser();
					}else{
						displayErrorMessage($scope.translation[result.reason]);
					}
				}).error(function(result, status){
					httpErrors($location.url(), status);
				});
			}
		}
	}
]);