/**
 * Created by alexfabian on 2/4/17.
 */

angular.module('myApp')

.directive('errorDisplay', function () {
   return {
       restrict: 'E',
       templateUrl: 'directives/alertsDisplay/alertsDisplay.html',
       controller: 'AlertsDisplayController'
   }
})

.controller('AlertsDisplayController', ['$scope', 'AlertsService', function ($scope, AlertsService) {

    $scope.alerts = AlertsService.alerts;

    $scope.closeAlert = function(alert){
        AlertsService.removeAlert(alert);
    };

}]);