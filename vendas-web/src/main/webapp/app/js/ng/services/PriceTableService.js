'use strict';

vendasApp.factory('PriceTableService',function(Restangular){
	
	var priceTableEdit; 
	return {
		
		save : function(priceTable){
			var priceTable_ = Restangular.all('priceTable').all('save').post(priceTable);
			return priceTable_;
        },
		
		
		  /**
         * Retorna todas as tabelas de preço de determinada filial.
         * @returns
         */
        getAllByBranch: function(organizationID,branchID){
        	
        	var parameters = {
					'organizationID' : organizationID, 
					'branchID' : branchID					
					};
        	
        	
        	return Restangular.all("priceTable").all("findAllByBranche").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
        }, 
        
        
        setPriceTableEdition : function(priceTable){
        	priceTableEdit = priceTable;
        },
        
        getPriceTableEdition : function(){
        	return priceTableEdit;
        } 
		
	};
});