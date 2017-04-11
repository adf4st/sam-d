'use strict';
var angular = require('angular');

var modal = angular.module('myApp');

modal.controller('ToolModalController', function ($scope, $uibModalInstance, tool, isNewTool, Tools) {

    $scope.tool = tool;

    $scope.isNewTool = isNewTool;

    $scope.update = function (updatedTool) {
        if($scope.toolForm.$valid){
            Tools.updateTool(updatedTool).then(
                function (success) {
                    $uibModalInstance.close(updatedTool);
                },
                function () {
                    $scope.errorMessage = "Error updating tool."
                }
            );
        }
    };

    $scope.create = function (newTool){
        if($scope.toolForm.$valid) {
            Tools.createTool(newTool).then(
                function (success) {
                    $uibModalInstance.close(newTool);
                },
                function () {
                    $scope.errorMessage = "Error creating tool."
                }
            );
        }
    };

    $scope.delete = function (toolToDelete) {
        Tools.deleteTool(toolToDelete).then(
            function (success) {
                $uibModalInstance.close(toolToDelete);
            },
            function () {
                $scope.errorMessage = "Error deleting tool."
            }
        );
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});