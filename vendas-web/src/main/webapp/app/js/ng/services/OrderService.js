/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	'use strict';

	vendasApp.factory('OrderService',
			function(Restangular){ 
		
		var orderEdit;
		return {
			
			/**
			 * Salva o pedido
			 */
			save : function(orderWrapper, userID){
				return  Restangular.all("v1").all('order').all('save').post(orderWrapper ,{},{'userID' : userID} );
			},
			
			/**
			 * Retorna todas os pedidos de determinada filial
			 */
			getAllByBranch: function( organizationID, branchID,offset,limit ) {

				var parameters = {
						'organizationID' : organizationID, 
						'branchID' : branchID,
						'offset': offset,
						'limit': limit
				};
				
				return Restangular.all("v1").all("order").all("getAllByBranch").getList(parameters).then(function(result){
					var p = {};
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			}, 
			
			getAllByUserAndBranch: function( organizationID, branchID,userID ,offset ,limit) {

				var parameters = {
						'organizationID' : organizationID, 
						'branchID' : branchID,
						'userID': userID,
						'offset': offset,
						'limit': limit
				};
				
				return Restangular.all("v1").all("order").all("getAllByUserAndBranch").getList(parameters).then(function(result){
					var p = {};
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			}, 	
			
			getByID: function( id) {

				var parameters = {
						'id' : id,				
				};
				
				return Restangular.all("v1").all("order").all("getByID").getList(parameters).then(function(result){
					var p = {};
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			}, 	
			
			getByFilter : function( filter, organizationID, branchID, offset) {

				var parameters = {
						'filter': filter,
						'organizationID' : organizationID, 
						'branchID' : branchID,
						'offset': offset
				};
				
				return Restangular.all("v1").all("order").all("getByFilter").getList(parameters).then(function(result){
					var p = {};
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			}, 	
			
			getByFilterAndUserID : function( filter, organizationID, branchID, userID, offset ) {

				var parameters = {
						'filter': filter,
						'organizationID' : organizationID, 
						'branchID' : branchID,
						'userID': userID,
						'offset': offset
				};
				
				return Restangular.all("v1").all("order").all("getByFilterAndUserID").getList(parameters).then(function(result){
					var p = {};
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			}, 	
			
			
			setOrderEdition : function(order){
				orderEdit = order;
			},

			getOrderEdition : function(){
				return orderEdit;
			} 
		};
	});
})();
