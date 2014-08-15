'use strict';

vendasApp.factory('UserService',function(Restangular,UtilityService, VendasWebRestangular, LocalStorageService, Constants){
	var _user;
	//Usuario que sera editado
	var userEdition;
	return {
		
		
		/**
		 * Registra um novo usuario para o sistema.
		 * @param fields
		 * @returns
		 */
		saveUser : function(user){
			var user = Restangular.all('user').all('saveUser').post(user);
			return user;
        },


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
        },        
	

		/**
		 * Registra um novo usuario para o sistema.
		 * @param fields
		 * @returns
		 */
		findUserByEmail : function(){	
			var email = UtilityService.getCookie(Constants.COOKIE_USER_EMAIL);
			return Restangular.all("user").all("getUserByEmail").getList({"email":email}).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;				
				return p;
			});
        }, 

        /**
         * Retorna todos os usuario de determinada empresa.
         * @returns
         */
        getAllByOrganization: function(organizationID,offset){
        	
        	var parameters = {
					'organizationID' : organizationID, 
					'offset' : offset					
					};
        	
        	
        	return Restangular.all("user").all("getUsersByOrganizationID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
        }, 
        
        /**
         * Busca os usuarios pelo filtro de pesquisa 
         */
        findUsersByFilter: function(organizationID,filter, offset){
	    	var parameters = {
					'organizationID' : organizationID, 
					'filter': filter,
					'offset' : offset					
					};
	    	
	    	
	    	return Restangular.all("user").all("getUsersByUserIDOrNameOrEmail").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;
				return p;
			});
		}, 
        
        setUserEdition: function(user){
        	userEdition = user;
        },
        
        getUserEdition: function(){
        	return userEdition;
        },
        
        addUserAccess: function(userID){
        	Restangular.all('user').all('addUserAccess').post(userID);
        }
	};	
});
