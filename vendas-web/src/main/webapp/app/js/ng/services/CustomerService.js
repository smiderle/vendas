'use strict';

vendasApp.factory('CustomerService',function(Restangular,UtilityService, LocalStorageService){
	var _customer;
	//Cliente que sera editado
	var customerEdition;
	return {
		
		
		/**
		 * Registra um novo cliente para o sistema.
		 * @param fields
		 * @returns
		 */
		save : function(customer){
			var newCustomer = Restangular.all('v1').all('customer').all('saveCustomer').post(customer);
			return newCustomer;
        },


 

        /**
         * Retorna todos os clientes de determinada empresa.
         * @returns
         */
        getAllByOrganization: function(organizationID,branchID,offset){
        	
        	var parameters = {
					'organizationID' : organizationID, 
					'branchID': branchID,
					'offset' : offset					
					};
        	
        	
        	return Restangular.all('v1').all("customer").all("getCustomersByOrganizationID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
        },
        
        /**
		 * Retorna todos os produtos de determinada filial.
		 * @returns
		 */
		getAllByFilter : function(filter,organizationID,branchID, offset){
			
			var parameters = {
					'filter': filter,
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset
			};


			return Restangular.all("v1").all("customer").all("getAllByFilter").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;
				return p;
			});
		},
		
		/**
		 * Retorna o limite de crédito do cliente
		 */
		getAvaliableCreditLimit : function(customerID){
			
			var parameters = {
					'customerID': customerID
			};

			return Restangular.all("v1").all("customer").all("getAvaliableCreditLimit").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;
				return p;
			});
		},
		
		/**
		 * Verifica se existe algum pagamento vencido
		 */
		hasExpiratePayment : function(customerID){
			
			var parameters = {
					'customerID': customerID
			};

			return Restangular.all("v1").all("customer").all("hasExpiratePayment").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.code = result.code;
				p.message = result.message;
				p.rowCount = result.rowCount;
				return p;
			});
		},
        
        
        setCustomerEdition: function(customer){
        	customerEdition = customer;
        },
        
        getCustomerEdition: function(){
        	return customerEdition;
        },        
	};	
});
