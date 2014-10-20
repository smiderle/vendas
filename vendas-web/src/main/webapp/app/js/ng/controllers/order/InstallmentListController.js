'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('InstallmentListController',
		['$scope', '$location', 'InstallmentService','ContextService','UtilityService',
		 function InstallmentListController($scope,$location, InstallmentService, ContextService, UtilityService) {

			$scope.listInstallments = function(){
				var aInstallments = InstallmentService.getAllByBranch(ContextService.getOrganizationID(), ContextService.getBranchLogged().branchOfficeID);
				aInstallments.then(function(toReturn){
					$scope.installments = toReturn.value;
					buildPriceTableDataTable(toReturn.value);
				});
			};
			
			/**
			 * Redireciona para o formulario
			 */
			$scope.newInstallment = function(){		
				//Remove qualquer tabela de preço que estava na edição
				InstallmentService.setInstallmentEdition({});
				
				$location.path('/pedido/cadastro-parcelamento');
			};
			
			
			/**
			 * Atualiza o parcelamento para excluida. Não ira mais ser listada
			 */
			$scope.deleteInstallment = function(){
				
				$.SmartMessageBox({
					title : "Exclusão",
					content : "Deseja realmente excluir esse parcelamento?",
					buttons : '[Não][Sim]'
				}, function(ButtonPressed) {
					if (ButtonPressed === "Sim") {
						var installmentID = $( "input:checked" ).val(),
						installments = $scope.installments,
						installmentSelected,
						size = installments.length;
						
						for(var i = 0; i < size; i++){
							var installment = installments[i];			
							if(installment && installment.id == installmentID ){				
								installmentSelected = installment;
								break;
							}
						}
						
						installmentSelected.excluded = true;
						
						
						var aInstallment = InstallmentService.save(installmentSelected);
						aInstallment.then(function(toReturn){
							if(toReturn.code == '200'){
								clearDatateble();
								$scope.listInstallments();
								UtilityService.showAlertSucess('Sucesso.', 'Parcelamenti excluido!!');
							} else {
								UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
							}					
						});
						
					}
				});
			};
			
			
			/**
			 * Cria um array de parcelamentos, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
			 * sendo "ouvido" na directive CustomDataTable
			 */
			function buildPriceTableDataTable(installments){
				var installmentsRows = [];
				
				
				installments.forEach(function(element, index){
					var installment = element;
					if(installment && installment.id){
						//Adiciona um array com as colunas que irão ser apresentadas no dataTable
						installmentsRows.push([
						              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+installment.id+'"><i></i></label>',
						              index+1,				               
						              installment.installmentID, 
						              installment.description,
						              installment.installmentsDays+' dias',						              
						              parseFloat(installment.tax).toFixed(2) + ' %',
						              installment.active ? 'Sim' : 'Não',
						              ]);
					}
				});
				
				//Seta os usuarios 
				$scope.rowsDataTable = installmentsRows;
			};
			
			
			/**
			 * Edita o parcelamento selecionado 
			 */
			       
			$scope.editInstallment = function(){	
				
				//Recupera o usuario selecionado no datatable.
				var installmentSelectedID = $( "input:checked" ).val();
				var installments = $scope.installments;
				
				var size = installments.length;
				
				for (var i = 0; i < size; i++) {
					var installment = installments[i];			
					if(installment && installment.id == installmentSelectedID){				
						InstallmentService.setInstallmentEdition(installment);				
						$location.path('/pedido/cadastro-parcelamento');
						break;
					}
				}
			
			};
			
			/**
			 * Remove todos os elementos do datatable
			 */
			function clearDatateble(){
				var dataTable = $('#datatable_installment').dataTable();
				dataTable.fnClearTable(0);
				dataTable.fnDraw();
			}
			
			
		}]);