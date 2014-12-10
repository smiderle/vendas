/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function() {

	'use strict';

	vendasApp.controller('ProductGroupFormController',
			['$scope','ContextService','UtilityService','ProductGroupService', 
			 function ProductGroupFormController($scope, ContextService, UtilityService, ProductGroupService) {
				
				var userLogged = ContextService.getUserLogged();

				/**
				 * Grupo
				 */
				$scope.productGroup;

				/**
				 * Chamado no init do form
				 */
				$scope.initGroupForm = function(){
					$scope.productGroup = ProductGroupService.getProductGroupEdition();
					var isEdition = $scope.productGroup && $scope.productGroup.id;

					if(!isEdition){
						
					}
				};

				/**
				 * Salva ou atualiza o grupo de produos
				 */
				$scope.save = function(){
					if($('#productGroup-form').valid()){
						$scope.productGroup.organizationID = ContextService.getOrganizationID();
						$scope.productGroup.branchID = ContextService.getBranchLogged().branchOfficeID;
						var aProductGroup = ProductGroupService.save( $scope.productGroup , userLogged.userID );
						aProductGroup.then(function(toReturn){
							if(toReturn.code == '200'){
								UtilityService.showAlertSucess('Sucesso.', 'Categoria salva com sucesso!!');
							} else {
								UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
							}					
						});
					}

				};
			}]);
})();
