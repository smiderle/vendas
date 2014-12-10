/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){	
	'use strict';

	vendasApp.factory('ReportService', ['ContextService', 'Restangular' ,function(ContextService, Restangular){

		return {

			getCurrentCountSalesByBranch : function( organizationID, branchID ){

				var parameters = {
						'organizationID' : organizationID,
						'branchID' : branchID
				};

				return Restangular.all("v1").all("report").all("getCurrentCountSalesByBranch").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},			
		};
	}]);
})();