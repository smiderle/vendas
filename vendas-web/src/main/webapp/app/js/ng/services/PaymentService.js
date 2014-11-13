'use strict';

vendasApp.factory('PaymentService',function(Restangular){
	
	var paymentDetails;
	
	return {
		/**
		 * Retorna todas as percelas em aberto.
		 * @returns
		 */
		getAllPendingByOrganizationID: function(organizationID,branchID, offset, limit){

			var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset,
					'limit' : limit
			};

			return Restangular.all("v1").all("orderPayment").all("getAllPendingByOrganizationID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},
		
		getPendingByFilter: function(organizationID,branchID,filter, offset, limit){

			var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'filter' : filter,
					'offset': offset,
					'limit' : limit
			};

			return Restangular.all("v1").all("orderPayment").all("getPendingByFilter").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},
		
		/**
		 * Retornar as parcelas de determinado pedido
		 */
		getByID : function(orderID){

			var parameters = {
					'orderID' : orderID
			};

			return Restangular.all("v1").all("orderPayment").all("getByID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;		
				p.code = result.code;
				return p;
			});
		},
		
		getByComplexFilter: function(organizationID, branchID, customerID, issuanceDateGte, issuanceDateLte, expirationDateGte, expirationDateLte,  statusPayment,formPayment, offset, limit){
			
			var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'customerID': customerID,
					'issuanceDateGte': issuanceDateGte,
					'issuanceDateLte': issuanceDateLte,
					'expirationDateGte': expirationDateGte,
					'expirationDateLte': expirationDateLte,
					'statusPayment': statusPayment,
					'formPayment': formPayment,
					'offset': offset,
					'limit' : limit
			};

			return Restangular.all("v1").all("orderPayment").all("getByComplexFilter").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;	
				p.code = result.code;
				return p;
			});
		},
		
		//getByComplexFilter Integer organizationID, Integer branchID, String customerID, Date issuanceDateGte, Date issuanceDateLte, Date expirationDateGte, Date expirationDateLte,  String statusPayment, Integer offset, Integer limit
		
		setPaid : function(orderPaymentID){
			return  Restangular.all("v1").all('orderPayment').all('setPaid').post(orderPaymentID);
		},
				
		getPayment : function(){
			return paymentDetails;
		},
		
		setPayment : function( payment ){
			paymentDetails = payment;
		}
		
		
	};
});