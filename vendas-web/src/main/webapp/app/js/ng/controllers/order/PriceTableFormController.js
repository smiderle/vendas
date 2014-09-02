'use strict';

vendasApp.controller('PriceTableFormController',
		['$scope','ContextService','UtilityService','PriceTableService', 
		 function PriceTableFormController($scope, ContextService, UtilityService, PriceTableService) {
			
			$scope.priceTable;
			
			$scope.typesIncrease =[
					{ label: 'Acréscimo', value: true},
					{ label: 'Desconto', value: false}
			];	
			

			$scope.initPriceTableForm = function(){
				$scope.priceTable = PriceTableService.getPriceTableEdition();
				var isEdition = $scope.priceTable && $scope.priceTable.id;
				
				if(!isEdition){
					$scope.priceTable = {
							increase : true
					};
				}
			};
			
			/**
			 * Salva ou atauliza a tabela de preço
			 */
			$scope.save = function(){
				if($('#pricetable-form').valid()){
					$scope.priceTable.organizationID = ContextService.getOrganizationID();
					$scope.priceTable.branchID = 1; //ALTERAR
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
