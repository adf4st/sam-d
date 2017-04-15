'use strict';
var angular = require('angular');

var privateView = angular.module('myApp');

privateView.config(function($stateProvider) {
  $stateProvider.
      state("private", {
          url: "/private",
          templateUrl: "components/private/private.html",
          controller: "PrivateCtrl",
          data: {
              requiresLogin: true
          }
      })
});

privateView.controller('PrivateCtrl', ['$scope', 'UserService', 'AlertsService',  function($scope, UserService, AlertsService) {

    UserService.getAllUsers().then(
        function (success) {
            $scope.users = success.data;
        },
        function (error) {
            AlertsService.addAlert({
                text : "Error getting users. " + error.message,
                title : "Error: ",
                type : "alert-danger"
            })
        }
    );

}]);