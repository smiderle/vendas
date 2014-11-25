/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';
	
	vendasApp.factory('NotificationService', 
			['RestangularNode', 
			function(RestangularNode){
		
		return {
			/**
			 * Retorna as notificações
			 */
			getNotifications: function( to, type, offset ){
	        	return RestangularNode.all('v1').all("notification").all("to/"+to+"/type/"+ type + "/offset/"+offset).getList().then(function(result){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;
					return p;
				});
	        },
	        
	        /**
	         * Retorna a quantidade de notificações
	         */
	        getNotificationsUnreadCount: function( to ){
	        	return RestangularNode.all('v1').all("notification/notificationsUnreadCount").all("to/"+to).getList().then(function(result){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;
					return p;
				});
	        }
		};
	}]);
})();