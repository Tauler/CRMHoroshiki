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
	
	this.resendConfirmSms = function(userId) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/resendConfirmSms',
            data: 'userId='+userId,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.isLoginExists = function(login, isBlank) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/isLogin',
            data: 'login='+login+'&isBlank='+isBlank,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.getCurrentUser = function() {return $http.get(backendServerAddr+'/user/getCurrentUser'); }
	
	this.editAddress = function(id, address, intercom, storey, access, apartment, comment) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAddress',
            data: 'id='+id+'&address='+address+'&intercom='+intercom+'&storey='+storey+'&access='+access+'&apartment='+apartment+'&comment='+comment,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.deleteAddress = function(id) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/deleteAddress',
            data: 'id='+id,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.editSex = function(sex) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAccount',
            data: 'sex='+sex,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.editBirthday = function(birthday) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAccount',
            data: 'birthday='+birthday,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.editOrderConfirm = function(orderConfirm) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAccount',
            data: 'orderConfirm='+orderConfirm,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.editNotifications = function(notifications) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAccount',
            data: 'notifications='+notifications,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
	this.editOrderConfirmType = function(orderConfirmType) {
        return $http({
            method: 'POST',
            url: backendServerAddr+'/user/editAccount',
            data: 'orderConfirmType='+orderConfirmType,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        });
    }
	
}]);