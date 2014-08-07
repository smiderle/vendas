'use strict';

vendasApp.factory('ContextService',function($rootScope, Restangular,UtilityService, VendasWebRestangular, LocalStorageService, Constants){

    
    function getUser (){
    	if($rootScope.user){
    		return $rootScope.user;
    	} else {
    		var _user = LocalStorageService.getFromLocalStorage(Constants.LOCAL_STORAGE_USER_LOGGED_KEY);
    		$rootScope.user = _user;
    		return $rootScope.user;
    	}
    };
    
    function getOrganizationID(){
    	var _user = getUser();    	
    	return _user.organizationID;
    };
    
     function getBranchOfficeID(){
    	var _user = getUser();
    	return _user.branchOfficeID ;
    };

	
	
	return {
		getOrganizationID : getOrganizationID
		
	};	
});
