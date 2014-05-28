function LocationController($scope, $location) {
    
	if($location.$$absUrl.lastIndexOf('/contacts') > 0){
        $scope.activeURL = 'contacts';
    } 
    
	else if($location.$$absUrl.lastIndexOf('/abonnements') > 0){
        $scope.activeURL = 'abonnements';
    } 
    
	else if($location.$$absUrl.lastIndexOf('/activation') > 0){
        $scope.activeURL = 'abonnements';
    } 
	else if($location.$$absUrl.lastIndexOf('/protected/profil/') > 0){
        $scope.activeURL = 'profil';
    }
	else if($location.$$absUrl.lastIndexOf('/admin/proxy') > 0){
        $scope.activeURL = 'abonnements2';
    }
	
	else if($location.$$absUrl.lastIndexOf('/codes') > 0){
        $scope.activeURL = 'abonnements';
    }
	else if($location.$$absUrl.lastIndexOf('/customers') > 0){
        $scope.activeURL = 'abonnements';
    }
	else if($location.$$absUrl.lastIndexOf('/proxy') > 0){
        $scope.activeURL = 'abonnements';
    }
	else if($location.$$absUrl.lastIndexOf('/users') > 0){
        $scope.activeURL = 'abonnements2';
    }
	else if($location.$$absUrl.lastIndexOf('/payments') > 0){
        $scope.activeURL = 'abonnements2';
    } 
	else if($location.$$absUrl.lastIndexOf('/configuration') > 0){
        $scope.activeURL = 'abonnements2';
    }
	else if($location.$$absUrl.lastIndexOf('/promocodes') > 0){
        $scope.activeURL = 'abonnements2';
    }
	else if($location.$$absUrl.lastIndexOf('/settings') > 0){
        $scope.activeURL = 'abonnements2';
    }
	else if($location.$$absUrl.lastIndexOf('/admin/profiles') > 0){
        $scope.activeURL = 'abonnements2';
    }
    else{
        $scope.activeURL = 'home';
    }
}
