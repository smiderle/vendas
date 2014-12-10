/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	
	'use strict';

	vendasApp.controller('PriceTableListController',
			function PriceTableListController($scope,$location, $window,ContextService , PriceTableService, UtilityService) {
		
		var userLogged = ContextService.getUserLogged();
		
		
		/**
		 * Linhas que irão popular a datatable das Tabelas De Preço.
		 * Contém um array de tabelas, com array de colunas 
		 */
		$scope.rowsDataTable = [];
		
		$scope.tables;
		
		$scope.newPriceTable = function(){		
			//Remove qualquer tabela de preço que estava na edição
			PriceTableService.setPriceTableEdition({});
			
			$location.path('/pedido/cadastro-tabela-de-preco');
		};
		
		/**
		 * Atualiza a tabela de preço para excluida. Não ira mais ser listada
		 */
		$scope.deletePriceTable = function(){
			
			$.SmartMessageBox({
				title : "Exclusão",
				content : "Deseja realmente excluir a tabela de preço?",
				buttons : '[Não][Sim]'
			}, function(ButtonPressed) {
				if (ButtonPressed === "Sim") {
					var tableSelectedID = $( "input:checked" ).val();
					var tables = $scope.tables;
					var priceTableSelected;
					var i;
					for(i in tables){
						var priceTable = tables[i];			
						if(priceTable && priceTable.id == tableSelectedID){				
							priceTableSelected = priceTable;
							break;
						}
					}
					
					priceTableSelected.excluded = true;
					
					
					var aTable = $scope.busyLoader = PriceTableService.save(priceTableSelected, userLogged.userID);
					aTable.then(function(toReturn){
						if(toReturn.code == '200'){
							clearDatateble();
							$scope.listTables();
							UtilityService.showAlertSucess('Sucesso.', 'Tabela de preço excluida!!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}					
					});
					
				}
			});
		};
		
		$scope.editPriceTable = function(){	
			
			//Recupera o usuario selecionado no datatable.
			var tableSelectedID = $( "input:checked" ).val();
			var tables = $scope.tables;
			
			var i;
			for(i in tables){
				var priceTable = tables[i];			
				if(priceTable && priceTable.id == tableSelectedID){				
					PriceTableService.setPriceTableEdition(priceTable);				
					$location.path('/pedido/cadastro-tabela-de-preco');
					break;
				}
			}
		
		};
		
		
		
		$scope.listTables = function(){
			var aTables = $scope.busyLoader = PriceTableService.getAllByBranch(ContextService.getOrganizationID(), ContextService.getBranchLogged().branchOfficeID);
			aTables.then(function(toReturn){
				$scope.tables = toReturn.value;
				buildPriceTableDataTable(toReturn.value);
			});
		};
		
		
		/**
		 * Cria um array de usuarios, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
		 * sendo "ouvido" na directive CustomDataTable
		 */
		function buildPriceTableDataTable(tables){
			var tablesRows = [];
			
			tables.forEach(function(element, index){
				var table = element;
				if(table && table.id){
					//Adiciona um array com as colunas que irão ser apresentadas no dataTable
					tablesRows.push([
					              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+table.id+'"><i></i></label>',
					              index+1,				               
					              table.priceTableID, 
					              table.description,
					              parseFloat(table.percentage).toFixed(2).replace('.', ',') + ' %',
					              table.increase ? 'Sim' : 'Não',
					              table.active ? 'Sim' : 'Não',
					              ]);
				}
			});
			
			//Seta os usuarios 
			$scope.rowsDataTable = tablesRows;
		};
		
		
		/**
		 * Remove todos os elementos do datatable
		 */
		function clearDatateble(){
			var dataTable = $('#datatable_pricetables').dataTable();
			dataTable.fnClearTable(0);
			dataTable.fnDraw();
		}
		
	});

})();
