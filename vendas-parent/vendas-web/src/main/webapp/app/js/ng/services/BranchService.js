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

			return Restangular.all("private").all("v1").all("branchOffice").all("getBranchesByOrganizationID").getList(parameters).then(function(result){
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
		save : function(branchOffice, userID){
			var branch = Restangular.all("private").all("v1").all('branchOffice').all('save').post(branchOffice, {},{'userID' : userID});
			return branch;
        },
        
        /**
		 * Salva ou atualiza uma nova filial.
		 * @param fields
		 * @returns
		 */
        update : function(branchOffice, userID){
			var branch = Restangular.all("private").all("v1").all('branchOffice').all('update').post(branchOffice,{},{'userID' : userID});
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