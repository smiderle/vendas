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
			
		},
		
		/**
		 * Verifica se o usuário tem acesso a determinado menu
		 */
		hasAccess : function( menuID ){
			var user = ContextService.getUserLogged();
			var size = user.menusApplication.length;
			for( var i = 0; i < size ; i++ ){
				if( menuID === user.menusApplication[i].menuID ){
					return true;
				}
			}
			return false;
		}
	};
});