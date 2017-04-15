/**
 * Created by alexfabian on 1/16/15.
 */
'use strict';
var angular = require('angular');

var contactView = angular.module('myApp');

contactView.config(function($stateProvider) {
    $stateProvider.
        state('tools', {
            url: "/tools",
            templateUrl: 'components/tools/tools.html',
            controller: 'ToolsController'
    });
});

contactView.controller('ToolsController', function($scope, Tools, AlertsService, NgTableParams, $window, $uibModal){

    $scope.isSettingsOpen = false;

    $scope.pageTitle = "";

    function refreshTools(){
        $scope.tableParams = new NgTableParams({}, {
            getData: function (params) {
                return Tools.getTools(params).then(
                    function(response){
                        params.total(response.data._embedded.tools.length);
                        return response.data._embedded.tools;
                    },
                    function (error) {
                        AlertsService.addAlert({
                            text : "Error creating user. " + error.message,
                            title : "Error: ",
                            type : "alert-danger"
                        });
                    }
                )
            }
        });
    }

    $scope.screenWidth = $window.innerWidth;


    $scope.columns = [
        { field: "name",        title: "Name",          sortable: "name ",       filter:{name    : "text"},   show: true },
        { field: "url",         title: "Url",           sortable: "url",         filter:{url     : "text"},   show: ($scope.screenWidth > 700) },
        { field: "port",        title: "Port",          sortable: "port"    ,    filter:{port    : "text"},   show: ($scope.screenWidth > 700) },
        { field: "version",     title: "Version",       sortable: "version",     filter:{version : "text"},   show: ($scope.screenWidth > 700) },
        { field: "notes",       title: "Notes",         sortable: "notes",       filter:{notes   : "text"},   show: ($scope.screenWidth > 700) },
        { field: "edit",        title: "Edit",          show: true }
    ];

    refreshTools();

    // Open Server URL in new tab
    $scope.open = function (tool) {
        $window.open(tool.url, '_blank');
    };

    // Open edit modal
    $scope.edit = function (selectedTool) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: './components/tools/toolModal.html',
            controller: 'ToolModalController',
            size: 'lg',
            resolve: {
                tool: function () {
                    return selectedTool;
                },
                isNewTool: function() {
                    return !selectedTool;
                }
            }
        });

        modalInstance.result.then(function (service) {
            $scope.selected = service;
            refreshTools();
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });

            console.log("opening modal window:" + selectedTool != null ? selectedTool.name : " new tool.");

    };
});