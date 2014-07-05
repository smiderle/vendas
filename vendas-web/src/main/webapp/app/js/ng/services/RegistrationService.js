'use strict';

vendasApp.factory('RegistrationService',function(Restangular, VendasWebRestangular){

	return {

		/**
		 * Registra um novo usuario para o sistema.
		 * @param fields
		 * @returns
		 */
		registeringNewUser : function(fields){
			var parameters = {
					'organizationName' : fields.organizationName, 
					'userName' : fields.userName, 
					'email' : fields.email, 
					'password' : fields.password
					};
			
			var user = Restangular.all('public').all('generateNewUser').post('parameters',parameters);
			return user;
        },
        /**
         *
         * Faz a autenticação do usuario.
         */
        redirectNewUser : function(username, password){
			var parameters = {
					'j_password' : password, 
					'j_username' : username					
					};
			
			var user = VendasWebRestangular.all('j_spring_security_check').post('parameters',parameters);
			return user;
        }        
	};	
});
