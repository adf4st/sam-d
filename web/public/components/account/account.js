'use strict';
var angular = require('angular');

var homeView = angular.module('myApp');

homeView.config(function ($stateProvider) {
    $stateProvider.
        state('account', {
            url: "/account",
            templateUrl: "components/account/account.html",
            controller: "AccountCtrl",
            data: {
                requiresLogin: true
            }
        })
});

homeView.controller('AccountCtrl', ['$scope', 'AlertsService', 'UserService', '$state',
    function ($scope, AlertsService, UserService, $state) {

        $scope.updatePassword = false;
        $scope.editMode = false;

        UserService.getUserDetails(UserService.getUser()).then(
            function (success){
                $scope.currentUser = success.data;
            },
            function (error) {
                AlertsService.addAlert({
                    text : "Error getting user details. " + error.message,
                    title : "Error: ",
                    type : "alert-danger"
                });
            }
        );


        $scope.updateUser = function(){
            UserService.updateUser($scope.currentUser).then(
                function (success) {
                    $scope.currentUser = success.data;
                    AlertsService.addAlert({
                        text: "Updated user",
                        title : "Success",
                        type: "alert-success"
                    });
                    $scope.enableEditMode(false);
                },

                function (error) {
                    AlertsService.addAlert({
                        text: "Error updating user: " + error.message,
                        title : "Error",
                        type: "alert-danger"
                    });
                }
            )
        };

        $scope.enableEditMode = function (value) {
            if(value == false){
                $scope.updatePassword = false;
            }
            $scope.editMode = value;
        }

    }
]);


