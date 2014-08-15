'use strict';

vendasApp.factory('UserBranchService',function(Restangular,UtilityService,Constants){	
	return {
		
        /**
         * Retorna todos as empresas do usuário.
         * @returns
         */
		getAllByUserID: function(userID){
        	
        	var parameters = {
					'userID': userID
					};
        	
        	
        	return Restangular.all("userBranch").all("getAllByUserID").getList(parameters).then(function(result){
        		        		
        		console.log(result);
				var p = {};
				p.value = [];
				
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;
				result.value.forEach(function(e,i){
					p.value.push(e);					
				});
				return p;
			});
        },
	};	
});
