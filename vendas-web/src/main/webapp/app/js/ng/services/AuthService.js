'use strict';

vendasApp.factory('AuthService', function(ContextService){
	
	return {
		
		/**
		 * Verifica se o usuario logado é administrador
		 */
		isAdmin: function(){
			var user = ContextService.getUserLogged();    	
	    	if(user.userRoles){
	    		for(var i = 0 ; i < user.userRoles.length; i++){
	    			if(user.userRoles[i].role === 'ROLE_ADMIN'){
	    				return true;
	    			}
	    		}
	    	}
	    	return false;
			
		}
	};
});