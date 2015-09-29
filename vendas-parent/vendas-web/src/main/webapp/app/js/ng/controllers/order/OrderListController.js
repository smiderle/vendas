/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	
	'use strict';
	
	vendasApp.controller('OrderListController',
			['$scope', '$location', '$compile' ,'OrderService','ContextService','DateUtil','AuthService',
			 function OrderListController($scope,$location, $compile, OrderService, ContextService, DateUtil, AuthService) {
				
				var organizationID = ContextService.getOrganizationID(),
				    branchID = ContextService.getBranchLogged().branchOfficeID,
				    userLogged = ContextService.getUserLogged(),
				    noMoreOrders = false;
				
				/**
				 * Pedidos/Orçamentos
				 */
				$scope.orders = [];
				
				$scope.init = function(){
					//listOrders(0);
					$scope.listOrders();
				};
				
				$scope.newOrder = function() {
					OrderService.setOrderEdition({});
					$location.path('/pedido/cadastro-pedido');
				};
				
				$scope.showOrderDetails = function(){

					//Recupera o produto selecionado no datatable.
					var orderSelectedID = $( "input:checked" ).val();
					var orders = $scope.orders;

					var i;
					for(i in orders){
						var order = orders[i];
						if(order && order.id == orderSelectedID ){
							OrderService.setOrderEdition(order);
							$location.path('/pedido/detalhes-pedido');
							break;
						}
					}
				};
				
				$scope.editOrder = function(index){
					setOrderEdition(orders[index]);
				};
				
				/**
				 * Busca os pedidos. Caso algum texto esteja presente no filtro busca pelo filtro, caso contráio, 
				 * retorna todos os registros
				 */
				$scope.listOrders = function(){					
					var cOrders;
					
					if(AuthService.isAdmin()){
						if($scope.filter){
							cOrders = $scope.loadOrder = OrderService.getByFilter( $scope.filter, organizationID, branchID, $scope.orders.length);					
						} else {
							cOrders = $scope.loadOrder = OrderService.getAllByBranch(organizationID,branchID, $scope.orders.length);
						}
					} else {
						//getAllByUserAndBranch
						if($scope.filter){
							cOrders = $scope.loadOrder = OrderService.getByFilterAndUserID( $scope.filter, organizationID, branchID,userLogged.userID, $scope.orders.length);					
						} else {
							cOrders = $scope.loadOrder = OrderService.getAllByUserAndBranch(organizationID,branchID,userLogged.userID, $scope.orders.length);
						}
					}
					
					
					
					cOrders.then(function(toReturn){
						
						if(toReturn.rowCount === 0){
							noMoreOrders = true;
						}
						
						buildOrderTableDataTable(toReturn.value);
					});
				};
				
				/**
				 * Chamado no botão de pesquisa do filtro
				 */
				$scope.findOrdersByFilter = function(){
					
					resetView();
					$scope.listOrders();
				};
				
				/**
				 * Cria um array de orders, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
				 * sendo "ouvido" na directive CustomDataTable
				 */
				function buildOrderTableDataTable(orders){
					var orderRows = [],
					ordersSize = $scope.orders.length;
					
					
					orders.forEach(function(element, index){					
						var order = element;
						if(order && order.id){
							
							$scope.orders.push(order);
							
							//Adiciona um array com as colunas que irão ser apresentadas no dataTable
							orderRows.push([
							                '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+order.id+'"><i></i></label>',						                						                
							                ordersSize + index + 1,
							                order.id, 
							                order.customer.name ,
							                'R$ '+ parseFloat(order.netValue).toFixed(2).replace('.', ','),
							                moment(order.issuanceTime).format('DD/MM/YYYY'),
							                order.type == 1 ? '<span class="badge bg-color-green padding-5">Pedido</span>' : '<span class="badge bg-color-blue padding-5">Orçamento</span>'
							                
							                ]);
						}
					});

					//Seta os pedidos 
					$scope.rowsDataTable = orderRows;
					
				};
				
				/**
				 * Fica ouvindo o evento, que é disparado quando o usuario clicar na ultima pagina do datatable
				 */
				$scope.$on('vendasApp:isLastPage', function (event) {
					console.log('Camando o evento ultima pagina');
					event.stopPropagation();
					if(!noMoreOrders){
						//listOrders($scope.orders.length);
						$scope.listOrders();
					}
				});
				
				/**
				 * Restaura todos os dados da tela
				 */
				function resetView(){
					$scope.orders = [];
					noMoreOrders = false;
					
					clearDatateble();
					
					$('#btnOrderEdit').attr("disabled","disabled");
					$('#btnCustomerShowDetails').attr("disabled","disabled");
				}
				
				/**
				 * Remove todos os elementos do datatable
				 */
				function clearDatateble(){
					var dataTable = $('#datatable_order').dataTable();
					dataTable.fnClearTable(0);
					dataTable.fnDraw();
				}
				
			}]);
})();

