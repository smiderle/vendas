'use strict';

vendasApp.factory('LocationService',function(Restangular){
	
	return {
		findAddressByPostalCode : function(postalCode){			
			return Restangular.all("private").all("v1").all("location").all("getAddressByPostalCode").getList({'postalCode' : postalCode}).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;			
				return p;
			});
		},
		
		/**
		 * Busca a cidade que inicia com a descrição passada por parametro
		 */
		findAllByDescription : function(description){			
			return Restangular.all("private").all("v1").all("location").all("getAllByDescription").getList({'description' : description, 'offset':0}).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;			
				return p;
			});
		}	
	};
	
});