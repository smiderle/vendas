'use strict';

vendasApp.factory('StateService',function(Restangular){

	return {
		findAll : function(){
			
			return Restangular.one("private").one("v1").one("states").one("getStates");
        }
	};	
});
