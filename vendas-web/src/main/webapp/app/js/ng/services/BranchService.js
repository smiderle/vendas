'use strict';

vendasApp.factory('BranchService',
		function(Restangular){
	
	var branchEdit;

	return {

		/**
		 * Retorna todas as filiais cadastradas na empresa.
		 */
		getAllBranchesByOrganizationID: function(organizationID){
			var parameters = {
					'organizationID' : organizationID,
			};

			return Restangular.all("branchOffice").all("getBranchesByOrganizationID").getList(parameters).then(function(result){
				var p = {};
				p.value = result.value;
				p.rowCount = result.rowCount;			
				return p;
			});
		},		
		
		/**
		 * Salva uma nova filial.
		 * @param fields
		 * @returns
		 */
		save : function(branchOffice){
			var branch = Restangular.all('branchOffice').all('save').post(branchOffice);
			return branch;
        },
        
        /**
		 * Salva ou atualiza uma nova filial.
		 * @param fields
		 * @returns
		 */
        update : function(branchOffice){
			var branch = Restangular.all('branchOffice').all('update').post(branchOffice);
			return branch;
        },
        
        /**
         *	Seta uma filial para edição 
         */
        setBranchEdition : function(branch){
        	branchEdit = branch;
        },
        
        /**
         * Recupera o usuario para a edição
         */
        getBranchEdition : function(){
        	return branchEdit;
        },
	};
});