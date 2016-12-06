var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('LoginViewController', ['$scope', '$location', 'AccountService',
    function ($scope, $location, AccountService) {

        var backUrl = $location.$$search.backUrl;

        if(backUrl == undefined || backUrl == true || backUrl == "" || backUrl.indexOf("/account/login") >= 0 || backUrl.indexOf("/account/registration") >= 0 || backUrl.indexOf("/account/confirm") >= 0){
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
		$scope.error.code = '';

		$scope.validateName = function(){
			$scope.error.name = false;
			if($scope.user.name == null || $scope.user.name == undefined || $scope.user.name.length == 0){
				$scope.error.name = true;
			}
		}
		
		$scope.validatePhone = function(){
			$scope.error.login = false;
			if(!userPhoneRegexp.test($scope.user.login)){
				$scope.error.login = true;
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
			return $scope.error.name == true || $scope.error.login == true || $scope.error.mail == true || $scope.error.password == true;
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
			
			$("#login").focus();
			
			$location.path('/account/registration', false);
		}
		
		$scope.showCodeError = false;
		$scope.submitConfirmCode = function(){
			AccountService.registerConfirm($scope.user.id, $scope.user.code).success(function(result){
				if(result.success == true){
                    $location.path('/account/login');
                }else{
                    $scope.showCodeError = true;
                }
            }).error(function(result, status){
                httpErrors($location.url(), status);
            });
		}
		
		$scope.resendConfirmCode = function(){
			AccountService.resendConfirmSms($scope.user.id).success(function(result){
				if(result.success == true){
                    
                }else{
                    $scope.showCodeError = true;
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