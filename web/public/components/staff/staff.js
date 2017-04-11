'use strict';
var angular = require('angular');

var staffView = angular.module('myApp');

staffView.config(function($stateProvider) {
  $stateProvider.
      state("staff", {
          url: "/staff",
          templateUrl: "components/staff/staff.html",
          controller: "StaffCtrl"
      })
});

staffView.controller('StaffCtrl', ['$scope', function($scope) {



}]);