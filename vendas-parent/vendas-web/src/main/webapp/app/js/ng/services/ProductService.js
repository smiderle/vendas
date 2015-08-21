'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.factory('ProductService',
		function(Restangular){

	var productEdit; 
	return {

		/**
		 * Salva ou atualiza o produto
		 */
		save : function(product, userID){
			return  Restangular.all("private").all("v1").all('product').all('save').post(product,{},{'userID' : userID});
		},

		/**
		 * Retorna todas os produtos de determinada filial.
		 * @returns
		 */
		getAllByBranch: function(organizationID,branchID, offset){

			var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset
			};


			return Restangular.all("private").all("v1").all("product").all("getAllByBranch").getList(parameters).then(function(result){
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
		getAllByFilter: function(filter,organizationID,branchID, offset){
			
			var parameters = {
					'filter': filter,
					'organizationID' : organizationID, 
					'branchID' : branchID,
					'offset': offset
			};


			return Restangular.all("private").all("v1").all("product").all("getAllByFilter").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},

		/**
		 * Salva ou atualiza o preço de promoção
		 */
		savePromotionPrice : function(product){
			return  Restangular.all("private").all("v1").all('productPromotion').all('save').post(product);
		},
		
		/**
		 * Salva ou atualiza o preço de promoção
		 */
		removePromotionPrice : function(product){
			return  Restangular.all("private").all("v1").all('productPromotion').all('remove').post(product);
		},
		
		
		
		/**
		 * Retorna todos os preços de promoção de determinado produto
		 */
		getAllPromotionPriceByProductID: function(productID){

			var parameters = {
					'productID': productID
			};


			return Restangular.all("private").all("v1").all("productPromotion").all("getAllByProductID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},
		

		setProductEdition : function(product){
			productEdit = product;
		},

		getProductEdition : function(){
			return productEdit;
		} 

	};
});