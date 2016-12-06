var accountServices = angular.module('accountServices', []);

accountServices.service('AccountService', ['$http', function($http) {
	this.loginAccount = function(username, password) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/login',
            data: 'j_username='+username+'&j_password='+password,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }

    this.logoutAccount = function() {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/logout',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }

    this.registerAccount = function(userId, login, password, name, mail, address, intercom, storey, access, apartment, comment, paymentMethod) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/registration',
            data: '&userId='+userId+'&login='+login+'&password='+password+'&name='+name+'&mail='+mail+'&address='+address+'&intercom='+intercom+'&storey='+storey+'&access='+access+'&apartment='+apartment+'&comment='+comment+'&paymentMethod='+paymentMethod,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.registerConfirm = function(userId, code) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/registration/confirm',
            data: 'userId='+userId+'&code='+code,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.resendConfirmSms = function(userId, code) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/resendConfirmSms',
            data: 'userId='+userId,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.getCurrentUser = function() {return $http.get(backendServerAddr+'/user/getCurrentUser'); }
}]);