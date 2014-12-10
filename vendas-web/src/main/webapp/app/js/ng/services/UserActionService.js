/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';

	vendasApp.factory('UserActionService',function(Restangular) {

		return {

			getUserAction : function(userID, offset, limit){
				var parameters = {
						'userID' : userID, 
						'limit' : limit,
						'offset': offset
				};
				
				return Restangular.all("v1").all("userAction").all("getAllByUserID").getList(parameters).then(function(result){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});				
				
			},
		};
	});
})();