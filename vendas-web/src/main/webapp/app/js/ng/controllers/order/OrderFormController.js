'use strict';

vendasApp.controller('OrderFormController',
		['$scope','$http','OrderService','UtilityService','ContextService','PriceTableService','CustomerService',
		 function BudgetFormController($scope,$http, OrderService, UtilityService, ContextService, PriceTableService, CustomerService) {
			
			/**
			 * Pedido
			 */
			$scope.order = {};
			
			//$scope.customers = [];

			$scope.init = function(){
				
				/**
				 * Inicializa os parametros que irão ser setados na url do autocomplete
				 */
				$scope.dataFormatFn = function(str) {
					return {
						filter: str,
						organizationID: ContextService.getOrganizationID(),
						branchID: ContextService.getBranchLogged().branchOfficeID,
						offset: 0,
						limit: 5
					}; 
				};
				
				var isEdition = false;
				
				listTables(
						function(){
							if(!isEdition && $scope.tables.length > 0)
								$scope.order.priceTable =  $scope.tables[0].id;							
						});

			};
			
			 			
			/**
			 * Auto complete do cliente
			 *//*
			$scope.onChangeCustomer = function(){
				
				
				
				 * Pega o texto digitado no imput. Não é pego o texto digitado pelo ng-model, 
				 * pois o ng-model seta o objeto selecionado
				 
				var txtCustomer = $('#txtCustomer').val();
				
				
				if(!txtCustomer || txtCustomer.length == 0){
					return;
				}
				
				var organizationID = ContextService.getOrganizationID();
				var branchID = ContextService.getBranchLogged().branchOfficeID;
				
				var cCustomer = CustomerService.getAllByFilter(txtCustomer,organizationID,branchID,0);
				cCustomer.then(function(toReturn){
					if(toReturn.code == '200'){
						var tmp = [];
						toReturn.value.forEach(function(customer){
							if(customer.id){
								tmp.push(customer);
							}
						});		
						
						$scope.customers = tmp;
					}
				});
			};*/
			
		


			/**
			 * Carrega as tabelas de preço 
			 */			
			function listTables(callback) {
				var aTables = PriceTableService.getAllByBranch(ContextService.getOrganizationID(), ContextService.getBranchLogged().branchOfficeID);
				aTables.then(function(toReturn){
					$scope.tables = toReturn.value;
					callback();
				});
			};
		}]);