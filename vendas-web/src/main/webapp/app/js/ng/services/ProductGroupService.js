'use strict';

vendasApp.factory('ProductGroupService',function(Restangular){

	var productGroupEdit; 
	return {

		/**
		 * Salva ou atualiza o grupo de produtos
		 */
		save : function(productGroup){
			var productGroup_ = Restangular.all("v1").all('productGroup').all('save').post(productGroup);
			return productGroup_;
		},


		/**
		 * Retorna todas os grupos de produtos de determinada filial.
		 * @returns
		 */
		getAllByBranch: function(organizationID,branchID, offset){

			var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset
			};


			return Restangular.all("v1").all("productGroup").all("getAllByBranch").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		}, 
		
		/**
		 * Retorna todas os grupos de produtos de determinada filial.
		 * @returns
		 */
		getAllByDescription: function(description,organizationID,branchID, offset){

			var parameters = {
					'description': description,
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset
			};


			return Restangular.all("v1").all("productGroup").all("getAllByDescription").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},


		setProductGroupEdition : function(productGroup){
			productGroupEdit = productGroup;
		},

		getProductGroupEdition : function(){
			return productGroupEdit;
		} 

	};
});