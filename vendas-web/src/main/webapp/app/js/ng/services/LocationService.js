'use strict';

vendasApp.factory('LocationService',function(Restangular){
	
	return {
		findAddressByPostalCode : function(postalCode){			
			return Restangular.all("v1").all("location").all("getAddressByPostalCode").getList({'postalCode' : postalCode}).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;			
				return p;
			});
		},
		
		findAllByDescription : function(description){			
			return Restangular.all("v1").all("location").all("getAllByDescription").getList({'description' : description, 'offset':0}).then(function(result){
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