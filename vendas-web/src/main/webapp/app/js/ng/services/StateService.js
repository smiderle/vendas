'use strict';

vendasApp.factory('StateService',function(Restangular){

	return {
		findAll : function(){
			
			return Restangular.one("states").one("getStates");
        }
	};	
});
