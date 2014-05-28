function activationController($scope, $location) {
    var url = "" + $location.$$absUrl;
    $scope.displayLoginError = (url.indexOf("error=403") >= 0);
    $scope.displayActivateError = (url.indexOf("error=402") >= 0);

}