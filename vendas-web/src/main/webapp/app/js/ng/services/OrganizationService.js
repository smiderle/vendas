'use strict';

vendasApp.factory('OrganizationService',
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
		save : function(branchOffice){
			var user = Restangular.all("private").all("v1").all('branchOffice').all('save').post(branchOffice);
			return user;
        },
        
        /**
		 * Salva ou atualiza uma nova filial.
		 * @param fields
		 * @returns
		 */
		saveOrUpadate : function(branchOffice){
			var user = Restangular.all("private").all("v1").all('user').all('saveOrUpdate').post(branchOffice);
			return user;
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