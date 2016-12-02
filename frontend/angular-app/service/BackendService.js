var backendServices = angular.module('backendServices', []);

backendServices.service('BackendService', ['$http', function($http) {
	this.checkIsAvailable = function(){
        return $http.get(backendServerAddr+'/isAvailableServer', {timeout: backendTimeout});
    }
}]);