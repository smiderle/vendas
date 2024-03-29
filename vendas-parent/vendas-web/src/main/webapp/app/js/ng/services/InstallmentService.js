'use strict';

vendasApp.factory('InstallmentService',function(Restangular){
	
	var installmentEdit; 
	return {
		
		save : function( installment, userID ){
			var installment_ = Restangular.all("private").all("v1").all('installment').all('save').post(installment,{},{'userID' : userID});
			return installment_;
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
        	
        	
        	return Restangular.all("private").all("v1").all("installment").all("findAllByBranche").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
        }, 
        
        
        setInstallmentEdition : function(installment){
        	installmentEdit = installment;
        },
        
        getInstallmentEdition : function(){
        	return installmentEdit;
        } 
		
	};
});