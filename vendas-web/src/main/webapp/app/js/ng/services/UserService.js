'use strict';

vendasApp.factory('UserService',function(Restangular,UtilityService, VendasWebRestangular, LocalStorageService){
	var _user;
	//Usuario que sera editado
	var userEdition;
	return {
		
		
		/**
		 * Registra um novo usuario para o sistema.
		 * Primeiro parametro é o usuário que esta sendo salvo, segundo parametro é o código do usuário que esta salvando
		 * @param fields
		 * @returns
		 */
		saveUser : function(user, userID){
			var user = Restangular.all('v1').all('user').all('saveUser').post(user,{},{'userID' : userID});
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
			
			var user = Restangular.all('v1').all('public').all('generateNewUser').post('parameters',parameters);
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
		findUserByEmail : function(email){
			
			return Restangular.all('v1').all("user").all("getUserByEmail").getList({"email":email}).then(function(result){
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
        	
        	
        	return Restangular.all('v1').all("user").all("getUsersByOrganizationID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
        }, 
        
        getOtherUsersByOrganizationID: function(organizationID,userID, offset){
        	
        	var parameters = {
					'organizationID' : organizationID, 
					'offset' : offset,
					'userID': userID
					};
        	
        	
        	return Restangular.all('v1').all("user").all("getOtherUsersByOrganizationID").getList(parameters).then(function(result){
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
	    	
	    	
	    	return Restangular.all('v1').all("user").all("getUsersByUserIDOrNameOrEmail").getList(parameters).then(function(result){
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
        	Restangular.all('v1').all('user').all('addUserAccess').post(userID);
        },
        
        /**
         * Retorna true,se o vendedor passado por parametro possuir as permissões de administrador
         */
        isAdmin : function( userRoles ){
	    	if(userRoles){
	    		for(var i = 0 ; i < userRoles.length; i++){
	    			if(userRoles[i].role === 'ROLE_ADMIN'){
	    				return true;
	    			}
	    		}
	    	}
	    	return false;
		},
		
		/**
         * Retorna true,se o vendedor passado por parametro possuir as permissões de vendedor
         */
		isSeller : function(userRoles){
	    	if(userRoles){
	    		for(var i = 0 ; i < userRoles.length; i++){
	    			if(userRoles[i].role === 'ROLE_USER'){
	    				return true;
	    			}
	    		}
	    	}
	    	return false;
		}
	};	
});
