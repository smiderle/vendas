'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.factory('OrderService',
		function(Restangular){ 
	return {
		
		/**
		 * Salva o pedido
		 */
		save : function(orderWrapper){
			return  Restangular.all("v1").all('order').all('save').post(orderWrapper);
		}
	};
});