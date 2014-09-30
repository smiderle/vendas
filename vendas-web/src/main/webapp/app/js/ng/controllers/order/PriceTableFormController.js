'use strict';

vendasApp.controller('PriceTableFormController',
		['$scope','ContextService','UtilityService','PriceTableService', 
		 function PriceTableFormController($scope, ContextService, UtilityService, PriceTableService) {

			/**
			 * Tabela de preço
			 */
			$scope.priceTable;

			/**
			 * Tipo de da tabela de preço. Acréscimo ou Desconto. Popula um combobox.
			 */
			$scope.typesIncrease =[
               { label: 'Acréscimo', value: true},
               { label: 'Desconto', value: false}
			];	


			/**
			 * Chamado no init do form
			 */
			$scope.initPriceTableForm = function(){
				$scope.priceTable = PriceTableService.getPriceTableEdition();
				var isEdition = $scope.priceTable && $scope.priceTable.id;

				if(!isEdition){
					$scope.priceTable = {
							increase : true,
							active: true
					};
				}
			};

			/**
			 * Salva ou atualiza a tabela de preço
			 */
			$scope.save = function(){
				if($('#pricetable-form').valid()){
					$scope.priceTable.organizationID = ContextService.getOrganizationID();
					$scope.priceTable.branchID = ContextService.getBranchLogged().branchOfficeID;
					var aTable = PriceTableService.save($scope.priceTable);
					aTable.then(function(toReturn){
						if(toReturn.code == '200'){
							UtilityService.showAlertSucess('Sucesso.', 'Tabela de Preço salva com sucesso!!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}					
					});
				}

			};
		}]);