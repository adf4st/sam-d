'use strict';
var angular = require('angular');

var staffView = angular.module('myApp');

staffView.config(function($stateProvider) {
  $stateProvider.
      state("login", {
          url: "/login",
          templateUrl: "components/login/login.html",
          controller: "LoginController"
      })
});

staffView.controller('LoginController', ['$scope', '$http', 'AlertsService', '$rootScope', 'UserService', function($scope, $http, AlertsService, $rootScope, UserService) {

    $scope.register = false;

    $scope.registerToggle = function () {
        $scope.register = !($scope.register);
    };

    $scope.loginOrRegister = function () {
        if($scope.register){
            createNewAccount();
        } else {
            login();
        }
    };

    var createNewAccount = function() {
        var userObject = {};
        userObject.username = $scope.username;
        userObject.password = $scope.password;
        userObject.firstName = $scope.firstName;
        userObject.lastName = $scope.lastName;

        UserService.createUser(userObject).then(
            function (success) {
                $scope.register = false;
                AlertsService.addAlert({
                    text : userObject.firstName + " " + userObject.lastName + ", your account has been created. You can now sign in.",
                    title : "SUCCESS! ",
                    type : "alert-success"
                });
            },
            function (error) {
                AlertsService.addAlert({
                    text : "Error creating user. " + error.message,
                    title : "Error: ",
                    type : "alert-danger"
                });
            }
        )
    };

    var login = function () {

        UserService.login($scope.username, $scope.password);
    }

}]);