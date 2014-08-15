'use strict';

vendasApp.factory('OrganizationService',
		function(Restangular){

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
			var user = Restangular.all('branchOffice').all('save').post(branchOffice);
			return user;
        },
        
        /**
		 * Salva ou atualiza uma nova filial.
		 * @param fields
		 * @returns
		 */
		saveOrUpadate : function(branchOffice){
			var user = Restangular.all('user').all('saveOrUpdate').post(branchOffice);
			return user;
        },
	};
});