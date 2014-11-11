'use strict';

vendasApp.factory('ContextService',function($rootScope, Restangular,UtilityService, VendasWebRestangular, LocalStorageService, Constants){

	/**
	 * Retorna o usuario logado salvo no objeto $rootScope.user, caso não o encontre, busca no localStorage
	 */
    function getUserLogged (){
    	if($rootScope.user){    		
    		return $rootScope.user;
    	} else {    		
    		var _user = LocalStorageService.getFromLocalStorageDecrypt(Constants.LOCAL_STORAGE_USER_LOGGED_KEY);
    		$rootScope.user = _user;
    		return $rootScope.user;
    	}
    };
    
    /**
     * Salva o usuario no local Storage criptografado
     */
    function setUserLogged(user){
    	LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_USER_LOGGED_KEY,user);
    };
    
    /**
     * Retorna o id da empresa do usuario
     */
    function getOrganizationID(){
    	var _user = getUserLogged();    	
    	return _user.organizationID;
    };
    
    /**
     * Retorna o id da filial logada
     */
    function getBranchOfficeID(){
    	var _user = getUserLogged();
    	return _user.branchOfficeID ;
    };
    
    /**
     * Retorna a filial logada
     */
    function getBranchLogged(){
    	if($rootScope.branchLogged){    		
    		return $rootScope.branchLogged;
    	} else {
    		var _branchLogged = LocalStorageService.getFromLocalStorageDecrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY);
    		$rootScope.branchLogged = _branchLogged;
    		return $rootScope.branchLogged;
    	}
    };
    
    /**
     * Salva a filial no local storage
     */
    function setBranchLogged(branchLogged){
    	$rootScope.branchLogged = branchLogged;
    	LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY,branchLogged);
    };
    

	
	return {
		getOrganizationID : getOrganizationID,
		getUserLogged : getUserLogged,
		setUserLogged: setUserLogged,
		getBranchLogged: getBranchLogged,
		setBranchLogged: setBranchLogged
	};	
});
