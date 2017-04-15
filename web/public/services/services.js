/**
 * Created by alexfabian on 2/22/15.
 */

var services = angular.module('myApp');

services.factory('ConfigService', ['$http', function ($http) {

    var config;

    function initializeConfig() {
        var request = {
            method: 'GET',
            url: '/env'
        };

        $http(request).then(
            function (success) {
                config = success.data;
            },
            function (error) {
                var errorText = "loading configuration from server: " + error.statusText;
                console.log(errorText);
            }
        );
    }

    function _config() {
        if(!config){
            initializeConfig();
        }
        return config;
    }

    return {
        initializeConfig : initializeConfig,
        _config : _config
    }
}
]);

services.factory('Tools', ['$http', 'ConfigService', '$sce', function($http, ConfigService, $sce){
    var baseUrl = ConfigService._config().API_BASE_URL;

    return {
        getTools      : getTools,
        createTool : createTool,
        updateTool : updateTool
    };

    function getTools(){
        var request = {
            method: 'GET',
            url: baseUrl + '/tools'
        };

        return $http(request);
    }

    function createTool(tool){
        var request = {
            method: 'POST',
            url: baseUrl + '/tools',
            data: tool
        };

        return $http(request);
    }

    function updateTool(tool){
        var request = {
            method: 'PUT',
            url: $sce.trustAsUrl(tool._links.self.href),
            data: tool
        };

        return $http(request)
    }

}]);

services.factory('AlertsService', [function () {

    var alerts = [];

    function addAlert(alert){
        alerts.push(alert);
    }

    function removeAlert(alert) {
        var index = alerts.indexOf(alert);
        alerts.splice(index, 1);
    }

    return {
        alerts : alerts,
        addAlert : addAlert,
        removeAlert : removeAlert
    }
}]);

services.factory('UserService', ['$http','jwtHelper', 'ConfigService', '$sce', 'AlertsService', '$rootScope', '$state', function ($http,jwtHelper, ConfigService, $sce, AlertsService, $rootScope, $state) {
    var baseUrl = ConfigService._config().API_BASE_URL;

    function getUserDetails() {
        var request = {
            method: 'GET',
            url : baseUrl + "/users/currentUser"
        };

        return $http(request)
    }

    function updateUser(user) {
        var request = {
            method: 'PUT',
            url : baseUrl + "/users",
            data: user
        };

        return $http(request)
    }

    function getUser() {
        var token = localStorage.getItem("id_token");

        var tokenPayload = jwtHelper.decodeToken(token);

        return {
            username: tokenPayload.sub,
            firstName: tokenPayload["net.alexfabian.firstName"],
            lastName: tokenPayload["net.alexfabian.lastName"]
        };
    }

    function getAllUsers() {
        var request = {
            method: 'GET',
            url : baseUrl + "/users"
        };

        return $http(request);
    }

    function createUser(user) {
        var request = {
            method: 'POST',
            url: baseUrl + "/register" ,
            data: user
        };

        return $http(request)
    }

    function login(username, password) {
        var authObject = {};
        authObject.username = username;
        authObject.password = password;

        var request = {
            method: 'POST',
            url: baseUrl + "/auth/login",  //TODO: extract into config!
            data: authObject
        };

        $http(request).then(
            function (success) {
                localStorage.setItem("id_token", success.data.token);
                localStorage.setItem("id_token_refresh", success.data.refreshToken);
                initUser();
                AlertsService.addAlert({
                    title : "Welcome: " + $rootScope.user.firstName + " " + $rootScope.user.lastName + "!",
                    text : "",
                    type : "alert-success"
                });
                $state.go('account');
            },
            function (error) {
                AlertsService.addAlert({
                    text : "logging in: " + error.statusText,
                    title : "Error  ",
                    type : "alert-danger"
                });
            }
        )
    }

    function getExpirationDate(){
        return jwtHelper.getTokenExpirationDate(expToken);
    }

    function initUser() {
        var token = localStorage.getItem("id_token");

        if(!!token){
            $rootScope.isAuthenticated = true;
            $rootScope.user = getUser();
        }
    }

    return {
        getUser : getUser,
        createUser : createUser,
        login : login,
        getUserDetails : getUserDetails,
        updateUser : updateUser,
        initUser : initUser,
        getAllUsers : getAllUsers
    }

}]);
