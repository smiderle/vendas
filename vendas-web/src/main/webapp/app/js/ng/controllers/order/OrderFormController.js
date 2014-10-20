'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('OrderFormController',
		['$scope','$rootScope','OrderService','UtilityService','ContextService','PriceTableService','CustomerService','CalcUtil',
		 function BudgetFormController($scope,$rootScope,OrderService, UtilityService, ContextService, PriceTableService, CustomerService, CalcUtil) {
			

			$scope.errorMessage = 'Testando';
			
			/**
			 * Pedido
			 */
			$scope.order = {};
			
			/**
			 * Produto selecionado
			 */
			$scope.productSelected = {};
						
			/**
			 * pedidoItens
			 */
			$scope.ordersItens = [];
			
			/**
			 * pedidoItem 
			 */
			$scope.orderItem = {};
			
			/**
			 * Cliente selecionado
			 */
			$scope.customerSelected = {};
						
			/**
			 * Tabelas de preço
			 */
			$scope.tables = [];
             
            /**
             * Valor Total do pedido
             */
            $scope.valueTotal = 0.0;
			
			/**
			 * Quando for editar um item do pedido é armazendo o item antes da
			 * edição nessa variavel, para que se o usuário cancelar a operação,
			 * seja possivel retornar os dados originais
			 */ 
			 
			$scope.orderItemEdition = undefined;
			
			
			/**
			 * Utilizados pela directiva validator
			 */			
			$scope.maximunDiscount = ContextService.getBranchLogged().maximumDiscount;
			
			/**
			 * Quando o desconto (em reais) é alterado, é calculado o percentual e alterado desconto (em precentual)
			 */
			$scope.$watch('orderItem.discount', function(newValue, oldValue){
				if(!isNaN(newValue)){
					
					calcPercentageDiscount(newValue);
					
					var table = $scope.orderItem.priceTable;
					var valPriceTable = CalcUtil.applyPercentage($scope.productSelected.salePrice,table.percentage, !table.increase);
					
					var valFinal = valPriceTable - $scope.orderItem.discount;
					$scope.orderItem.salePrice = parseFloat(valFinal).toFixed(2)/1;
				}
			});
			
			/**
			 * Quando o campo orderItemSalePrice, preço de venda, perder o focu
			 *
			 */
			$scope.onBlurItemSalePrice = function(){
				if(!isNaN($scope.orderItem.salePrice)){
					calcPercentageDiscount($scope.orderItem.discount);		
					
					var table = $scope.orderItem.priceTable;
					var valPriceTable = CalcUtil.applyPercentage($scope.productSelected.salePrice,table.percentage, !table.increase);
					
					if(valPriceTable > $scope.orderItem.salePrice){
						var valFinal = valPriceTable - $scope.orderItem.salePrice ;
						$scope.orderItem.discount = parseFloat(valFinal).toFixed(2)/1;
					} else {
						$scope.orderItem.discount = 0/1;
					}
				}
			};
					
			
			/**
			 * Produto selecionado
			 */
			$scope.productSelectedCallback = function(obj){
				if(obj){
					$scope.productSelected = obj.originalObject;
					
					applyPriceTable(obj.originalObject.salePrice);
					
					var table = $scope.orderItem.priceTable;
					var valProductPriceTable = CalcUtil.applyPercentage(obj.originalObject.salePrice,table.percentage, !table.increase);
					$scope.orderItem.salePrice = valProductPriceTable; 
					// foi colocado o 0.0/1, porque se deixar somente 0.0, o input do tipo number não esta apresentando o valor 
					$scope.orderItem.discount = 0.0;
					$scope.orderItem.quantity = 1;
					$scope.orderItem.product = obj.originalObject;
				}				
			};
			
			/**
			 * Quando mudar a tabela de preço
			 */
			$scope.onChangePriceTable = function(){
				var table = $scope.orderItem.priceTable;
				if($scope.productSelected && $scope.productSelected.salePrice){
					var valFinal = 0;
					valFinal = CalcUtil.applyPercentage($scope.productSelected.salePrice,table.percentage, !table.increase);
					$scope.orderItem.salePrice= valFinal;
					$scope.orderItem.discount = 0.0;
					//Recalcula o desconto em percentual com a nova tabela de preço selecionada
					calcPercentageDiscount($scope.orderItem.discount);
				}
			};
			
			
			/**
			 * Adiciona o produto no "carrinho"
			 */
			$scope.addProductToCart = function(orderItem){
							
				var errorMessages = isValidOrderItem();
				if(errorMessages == undefined){

					$('#alertOrderInvalid').hide();
					
					if(orderItem.discount == undefined){
						orderItem.discount = 0.0;
					}

					//console.log($scope.orderForm.orderItemDiscountPercentage.$error.maximumdiscountvalidator);
					
					//Se essa variavel estiver vazia, significa que não é uma edição
					if($scope.orderItemEdition == undefined || $scope.orderItemEdition  == null){
						$scope.ordersItens.push(orderItem);
					} else {
						$scope.orderItemEdition = undefined;
					}

					clearProductSelected();
	                calcValueTotal();
				} else {
					$scope.errorMessage = errorMessages;
					$('#alertOrderInvalid').show();
				}

			};
			
			$scope.cancelEdition = function(){
				
				var orderItem = $scope.orderItemEdition;
				
				//Atribui os valores originais, de antes da edição.
				$scope.orderItem.discount = orderItem.discount;
				$scope.orderItem.quantity = orderItem.quantity;
				$scope.orderItem.salePrice = orderItem.salePrice;
				$scope.orderItem.priceTable = orderItem.priceTable;
				$scope.orderItem.observation = orderItem.observation;
				
				clearProductSelected();
				
			};
			
			/**
			 * Edita o produto do pedido
			 */
			$scope.editProduct = function(orderItemIndex){
				
				var orderItem = $scope.ordersItens[orderItemIndex];
								
				$scope.orderItemEdition = {
						discount : orderItem.discount,
						quantity: orderItem.quantity,
						salePrice: orderItem.salePrice,
						priceTable: orderItem.priceTable,
						observation: orderItem.observation
						
				};
				
 
				$scope.productSelected = orderItem.product;
				
				
				//Editado
				$scope.orderItem = orderItem;
				applyPriceTable(orderItem.product.salePrice);
				notifyEdition();
				
				calcPercentageDiscount($scope.orderItem.discount);
				
				$rootScope.$broadcast('angucomplete-alt:setValue', { elementId:  'autoCompleteProduct', value: orderItem.product.description } );
			};
			
			
			/**
			 * Remove o item do pedido
			 */
			$scope.deleteOrderItem = function(orderItemIndex){
				$scope.ordersItens.splice( orderItemIndex, 1 );
                calcValueTotal();
			};


			$scope.init = function(){
				
				$scope.errorMessage = 'Alguns campos estão incorretos. Por favor, verifique antes de continuar.';

				/**
				 * Inicializa os parametros que irão ser setados na url do autocomplete
				 */
				$scope.dataFormatFn = function(str) {
					return {
						filter: str,
						organizationID: ContextService.getOrganizationID(),
						branchID: ContextService.getBranchLogged().branchOfficeID,
						offset: 0,
						limit: 20
					}; 
				};

				/**
				 * Inicializa os parametros que irão ser setados na url do autocomplete
				 */
				$scope.dataFormatProduct = function(str) {
					return {
						filter: str,
						organizationID: ContextService.getOrganizationID(),
						branchID: ContextService.getBranchLogged().branchOfficeID,
						offset: 0,
						limit: 20
					}; 
				};

				var isEdition = false;

				listTables(
						function(){
							if(!isEdition && $scope.tables.length > 0){
								$scope.orderItem.priceTable =  $scope.tables[0];
								applyPriceTable();
							}
						});

			};		
			
			$scope.parseFloat = function(val) {
			    return isNaN(parseFloat(val)) ? 0: parseFloat(val);
			};
			
			/**
			 * Formata o valor com 2 casas decimais e virgula.
			 */
			$scope.formatFloat = function(val){

				if(isNaN(val) || val <= 0){
					return '0,00';
				} else {
					return numberFormat(val, 2, ',', '.'); 
				}			
			};
			
			/**
			 * É calculado qual é o percentual equivalente ao descontoEmReais, em relação ao preço do produto, 
			 * já aplicado a tebela de preço
			 */
			function calcPercentageDiscount(descontoEmReais) {

				var table =  $scope.orderItem.priceTable;
				
				var productPriceTable = CalcUtil.applyPercentage($scope.productSelected.salePrice,table.percentage, !table.increase);
				
				var percentageValue = CalcUtil.getPercentageByValue(productPriceTable ,parseFloat(descontoEmReais));
				
				$scope.discountPercentage = parseFloat(percentageValue).toFixed(2)/1;
				
				//Variavel para mostrar para o usuario o valor máximo de desconto
				
				//$scope.formatFloat(
				
				var maxDesc = CalcUtil.getValueByPercentage(
						productPriceTable,
						ContextService.getBranchLogged().maximumDiscount);
				
				$scope.maximunDiscountProduct = $scope.formatFloat(maxDesc);
				
				/*
				$scope.maximunDiscountProduct =  CalcUtil.applyPercentage(
						productPriceTable,
						(100 - ContextService.getBranchLogged().maximumDiscount), true);*/
			
			}
			
			/**
			 * Calcula a soma total do pedido
			 */
			function calcValueTotal(){
				var orderItens = $scope.ordersItens;
                var total = 0.0;
				if(orderItens && orderItens.length > 0){
					orderItens.forEach(function(orderItem){
                        total += orderItem.salePrice * orderItem.quantity;
                    });
				}
				$scope.valueTotal = total;
			}
			 			

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
			
			/**
			 * Aplica o percentual da tabela de preço na descrição do combo das tabelas de preço
			 */
			function applyPriceTable(salePrice){
								
				$scope.tables.forEach(function(table){					

					var valFinal = CalcUtil.applyPercentage(salePrice,table.percentage, !table.increase);	
					if(salePrice){
						
						table.label = ('R$ '+$scope.formatFloat(valFinal)+' - ' + table.description);
					} else {
						table.label = table.description;
					}
					
					/*
					var customTable = {
							description: ('R$ '+valFinal+' - ' + table.description),
							id: table.id ,
							increase: table.increase,
							percentage: table.percentage
					};

					$scope.customTables.push(customTable);*/
				});
			};
			
			function clearProductSelected(){
				var lastPriceTable = $scope.orderItem.priceTable;
				$scope.productSelected = {};
				//Seta a ultima tabela de preço utilizada
				$scope.orderItem = { priceTable : lastPriceTable};
				//Remove o produto da edição, caso exista
				$scope.orderItemEdition = undefined;
				//Percentual de desconto
				$scope.discountPercentage = 0;
				//Remove o perço do combo ta tabela de preço
				applyPriceTable();
				//Lança um evento pro angucomplete-alt limpar o scope				
				$rootScope.$broadcast('angucomplete-alt:clearSearch','autoCompleteProduct');
			};
			
			function notifyEdition(){
				var $post = $("#flsOrderItem");

				$post.addClass("fieldset-edit");
				
				setTimeout(function(){
					$post.addClass("fieldset-edit-transparent");
					setTimeout(function(){
						$post.removeClass("fieldset-edit");
						$post.removeClass("fieldset-edit-transparent");
					}, 200);
				}, 200);	
			};
			
			function isValidOrderItem() {
				var errorMessages = undefined;
				if($scope.orderForm.orderItemDiscountPercentage.$error.maximumdiscountvalidator){
					errorMessages = 'O campo \'Desconto\' está inválido.';					
				}
				return errorMessages;
				
			}

			
			/*function validateOrderItem(){
				var messageInvalid = undefined;
				if(isNaN($scope.orderItem.quantity)){
					messageInvalid = 'Informe uma quantidade válida.';
				} else if($scope.orderItem.quantity != 0){
					
				} else if($scope.orderItem.salePrice != 0){
					
				}				
				
				
			}*/
			
			
		}]);