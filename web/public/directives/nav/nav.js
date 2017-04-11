/**
 * Created by alexfabian on 2/4/17.
 */

angular.module('myApp')

    .directive('myNavbar', function () {
        return {
            restrict: 'E',
            templateUrl: 'directives/nav/nav.html',
            controller: 'NavbarController'
        }
    })

    .controller('NavbarController', ['$scope', '$location', '$rootScope', '$state', 'UserService', 'AlertsService', function ($scope, $location, $rootScope, $state, UserService, AlertsService) {

        // re-pupulate rootScope.user on page refresh
        UserService.initUser();

        $scope.items = [
            'The first choice!',
            'And another choice for you.',
            'but wait! A third!'
        ];

        $scope.navbarItems = [
            {text: "Home", state: "home"},
            {text: "Staff", state: "staff"},
            {text: "Tools", state: "tools"}
        ];

        $scope.rightNavbarItems = [
            // {text: "Side Navigation", state: "staff"}
        ];

        $scope.dropdownItems = [
            {text: "Home", state: "home"},
            {text: "Staff", state: "staff"},
            {text: "Contact", state: "contact"}
        ];

        $scope.logout = function () {
            $rootScope.isAuthenticated = false;
            localStorage.removeItem("id_token");
            localStorage.removeItem("id_token_refresh");
            $state.go('login');
            AlertsService.addAlert({
                text : "You have been signed out",
                title : "Success: ",
                type : "alert-success"
            });
        };

    }]);