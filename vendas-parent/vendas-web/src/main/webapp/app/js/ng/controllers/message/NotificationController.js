/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';
		
	vendasApp.controller('NotificationController',['$scope','NotificationService', 'ContextService',
	                                               function MessageController($scope, NotificationService, ContextService) {
		
		var userLogged = ContextService.getUserLogged();
		
		
		
		/**
		 * Notificações
		 */
		
		$scope.notifications = [];
		
		$scope.load = function(type) {
			$scope.showLoad = true;
			
			var cNotification = NotificationService.getNotifications(userLogged.userID , type);
			
			cNotification.then( function( toReturn ){				
				if(toReturn.code === '200') {					
					toReturn.value.forEach(function(notification) {
						$scope.notifications.push(notification);
					});					
				}
				$scope.showLoad = false;
			});
			
		};
		
		$scope.formatDate = function(date){
			return moment(date).fromNow();
		};
		
	}]);
})();