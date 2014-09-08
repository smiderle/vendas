'use strict';

vendasApp.factory('StateService',function(Restangular){

	return {
		findAll : function(){
			
			return Restangular.one("v1").one("states").one("getStates");
        }
	};	
});
