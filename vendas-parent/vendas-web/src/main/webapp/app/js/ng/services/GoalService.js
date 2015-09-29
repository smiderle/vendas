/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){	
	'use strict';

	vendasApp.factory('GoalService', ['ContextService', 'Restangular' ,function(ContextService, Restangular){
		
		return {

			getAllByBranch : function( organizationID, branchID ){

				var parameters = {
						'organizationID' : organizationID, 
						'branchID' : branchID
				};

				return Restangular.all("private").all("v1").all("goal").all("getGoalRegistered").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
			save : function( targets, userID ){
				return  Restangular.all("private").all("v1").all('goal').all('save').post( targets ,{},{'userID' : userID } );
			},
		};
	}]);
})();