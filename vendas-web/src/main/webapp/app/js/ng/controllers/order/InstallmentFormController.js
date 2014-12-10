'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('InstallmentFormController',
		['$scope','InstallmentService', 'DateUtil','CalcUtil','UtilityService','ContextService',
		 function InstallmentFormController($scope, InstallmentService, DateUtil,CalcUtil, UtilityService, ContextService) {
			
			var userLogged = ContextService.getUserLogged();
			/**
			 * Valor exemplo para as parcelas
			 */
			$scope.valorExemplo = 100.55;
			
			$scope.isValidInstallment = true;
			
			$scope.parcelasExemplo = [];
			
			/**
			 * Salva o parcelamento
			 */
			$scope.save = function(installment) {
				if($('#installment-form').valid() && $scope.isValidInstallment){
					installment.organizationID = ContextService.getOrganizationID();
					installment.branchID = ContextService.getBranchLogged().branchOfficeID;
					var aInstallment =  InstallmentService.save(installment, userLogged.userID);
					aInstallment.then(function(toReturn){
						if(toReturn.code == '200'){
							UtilityService.showAlertSucess('Sucesso.', 'Parcelamento salvo com sucesso!!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}					
					});
				}

			};
			
			/**
			 * inicialização do form
			 */
			$scope.initInstallmentForm = function() {
				$scope.installment = InstallmentService.getInstallmentEdition();	
				var isEdition = $scope.installment && $scope.installment.id;
				if(!isEdition){
					
					$scope.installment = {
						active: true	
					};
				}
				
			};
			
			/**
			 * Cria um ouvinte para ficar monitorando o objeto installmentsDays, cada vez que ele for alterado os parcelamentos,
			 * é recalculado os parcelamentos de exemplo			 
			 */
			$scope.$watch('installment.installmentsDays', function(newValue, oldValue){
				if($scope.installment){
					var taxa = $scope.installment.tax || 0;
					calculaParcelas(newValue, taxa);
				}				
			});	
			
			/**
			 * Cria um ouvinte para ficar monitorando o objeto tax, cada vez que ele for alterado o percentual de acréscimo,
			 * é recalculado os parcelamentos de exemplo			 
			 */
			$scope.$watch('installment.tax', function(newValue, oldValue){
				if($scope.installment){
					var parcelas = $scope.installment.installmentsDays;
					calculaParcelas(parcelas, newValue);
				}
			});
			
			function calculaParcelas(parcelas, taxa){

				if(parcelas){					
					var days = parcelas.replace('\"', '').split(/[ ]+/);
					validaParcelas();
				}
				
				function validaParcelas(){
					var diasValidos = [],
					valorAnterior = -1,
					isValid = true;					
					var size = days.length;
					for (var i = 0; i < size; i++) {
						var dayInt = parseInt(days[i]);
						//dayInt < 100000 para não estrapolar a data
						if(Number.isInteger(dayInt) && dayInt > valorAnterior && dayInt < 100000){
							diasValidos.push(dayInt);
							valorAnterior = dayInt;
						} else {
							//Valores inválidos
							isValid = false;
							break;
						}
					};
					
					if(isValid){						
						$scope.isValidInstallment = true;
						CalcUtil.geraParcelas($scope.valorExemplo,diasValidos,taxa,function(parcelasGeradas){
							$scope.parcelasExemplo = parcelasGeradas;
						} );
					} else {
						$scope.isValidInstallment = false;
						$scope.parcelasExemplo = [];
					}
					
				}
			
			}
			
		}]);