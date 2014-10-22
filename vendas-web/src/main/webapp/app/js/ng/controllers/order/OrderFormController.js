'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('OrderFormController',
		['$scope','$rootScope','OrderService','UtilityService','ContextService','PriceTableService','CustomerService','CalcUtil','FormsPaymentService','InstallmentService',
		 function BudgetFormController($scope,$rootScope,OrderService, UtilityService, ContextService, PriceTableService, CustomerService, CalcUtil, FormsPaymentService, InstallmentService) {
			
			$scope.branch = ContextService.getBranchLogged();
			
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
			 * Parcelamento selecionado
			 */
			$scope.installmenetSelected = {};
			
			/**
			 * Forma de pagamento selecionado
			 */
			$scope.formPaymentSelected;
			
						
			/**
			 * Tabelas de preço
			 */
			$scope.tables = [];
             
            /**
             * Valor Total do pedido
             */
            $scope.valueTotal = 0.0;
            
            /**
             * Valor Total das Parcelas.
             */
            $scope.valueTotalInstallment = 0.0;
			
			/**
			 * Quando for editar um item do pedido é armazendo o item antes da
			 * edição nessa variavel, para que se o usuário cancelar a operação,
			 * seja possivel retornar os dados originais
			 */ 
			 
			$scope.orderItemEdition = undefined;
			
			
			/**
			 * Utilizados pela directiva validator
			 */			
			$scope.maximunDiscount = $scope.branch.maximumDiscount;
						

			$scope.init = function(){
				
				configWizard();
				
				$scope.order.issuanceTime = new Date();

				$scope.errorMessage = 'Alguns campos estão incorretos. Por favor, verifique antes de continuar.';

				/**
				 * Inicializa os parametros que irão ser setados na url do autocomplete
				 */
				$scope.dataFormatFn = function(str) {
					return {
						filter: str,
						organizationID: ContextService.getOrganizationID(),
						branchID: $scope.branch.branchOfficeID,
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
						branchID: $scope.branch.branchOfficeID,
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
				
				listFormsPayment(
						function(){
							if(!isEdition && $scope.formsPayment.length > 0)
								if($scope.order.customer && $scope.order.customer.formPayment){
									$scope.order.formPayment =  $scope.order.customer.formPayment;
								} else {
									$scope.formPaymentSelected = $scope.formsPayment[0];
									$scope.order.formPayment =  $scope.formsPayment[0].id;
								}																
							});
				
				listInstallments(
						function(){
							if(!isEdition && $scope.installments.length > 0)
								if($scope.order.customer && $scope.order.customer.installment){
									$scope.installment =  $scope.order.customer.installment;
								} else {
									$scope.installmenetSelected = $scope.installments[0];
									$scope.installment =  $scope.installments[0].id;
								}
								
								  
							});

			};	
			
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
				} else {
					$scope.discountPercentage = undefined;
				}
			});
			
			$scope.onBlurDiscount = function(){
				/*if(!isNaN($scope.orderItem.discount)){
					
					calcPercentageDiscount($scope.orderItem.discount);
					
					var table = $scope.orderItem.priceTable;
					var valPriceTable = CalcUtil.applyPercentage($scope.productSelected.salePrice,table.percentage, !table.increase);
					
					var valFinal = valPriceTable - $scope.orderItem.discount;
					$scope.orderItem.salePrice = parseFloat(valFinal).toFixed(2)/1;
				}*/
			};
			
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
						$scope.orderItem.discount = undefined;
					}
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
					$scope.orderItem.discount = undefined;
					//Recalcula o desconto em percentual com a nova tabela de preço selecionada
					calcPercentageDiscount($scope.orderItem.discount);
				}
			};
			
			/**
			 * Quando mudar o combo do parcelamento
			 */
			$scope.onChangeInstallment = function(){
				var installment = $scope.installments;
				
				var i = 0;
				for(;i < installment.length; i++){
					if(installment[i].id === $scope.installment){
						$scope.installmenetSelected = installment[i];
						generateInstallment(installment[i]);
						break;
					}
				}				
			};
			
			/**
			 * Quando mudar o combo de forma de parcelamento
			 */
			$scope.onChangeFormPayment = function(){
				
				var formsPayment = $scope.formsPayment;
					
				for(var i = 0 ; i < formsPayment.length ; i++){
					if(formsPayment[i].id === $scope.order.formPayment){
						$scope.formPaymentSelected = formsPayment[i];
						break;
					}
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
						//orderItem.discount = 0.0;
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
					$scope.orderItem.discount = undefined;
					$scope.orderItem.quantity = 1;
					$scope.orderItem.product = obj.originalObject;
				}				
			};
			
			/**
			 * Cliente selecionado
			 */
			
			$scope.customerSelectedCallback = function(obj){
				if(obj){
					$scope.order.customer = obj.originalObject;
					 
					if($scope.order.customer && $scope.order.customer.installment){
						$scope.installment =  $scope.order.customer.installment;
					}					
					
					if($scope.customerSelected && $scope.customerSelected.formPayment){
						$scope.order.formPayment =  $scope.order.customer.formPayment;
					}
					
				}				
			};
			
			
			/**
			 * Remove o item do pedido
			 */
			$scope.deleteOrderItem = function(orderItemIndex){
				$scope.ordersItens.splice( orderItemIndex, 1 );
                calcValueTotal();
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
			

			
			function generateInstallment(installment){
								
				var dias = installment.installmentsDays.split(' ').map(Number);
				
				CalcUtil.geraParcelas($scope.valueTotal, dias,installment.tax,function(parcelasGeradas){
					$scope.parcelas = parcelasGeradas;
					
					var valueTotalInstallment = 0.0;
					parcelasGeradas.forEach(function(parcela){
						valueTotalInstallment += parcela.valor;						
					});
					
					$scope.valueTotalInstallment = valueTotalInstallment;
				});
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
						$scope.branch.maximumDiscount);
				
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
			
			$scope.getFormPaymentSelected = function(){
				var length = $scope.formsPayment;
				for(var i=0 ; i < length; i++ ){
					if(payment.id === $scope.formsPayment[i]){
						return payment;
						break;
					};
				}
				
			};
			 			
			/**
			 * Carrega as formas de pagamento
			 */
			function listFormsPayment(callback){
				$scope.formsPayment =  FormsPaymentService.getFormsPayment();
				callback();
			};
			
			/**
			 * Carrega os parcelamentos
			 */
			function listInstallments(callback) {
				var aInstallments = InstallmentService.getAllByBranch(ContextService.getOrganizationID(), $scope.branch.branchOfficeID);
				aInstallments.then(function(toReturn){
					$scope.installments = toReturn.value;
					callback();
				});
			};
			

			/**
			 * Carrega as tabelas de preço 
			 */			
			function listTables(callback) {
				var aTables = PriceTableService.getAllByBranch(ContextService.getOrganizationID(), $scope.branch.branchOfficeID);
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
			
			/**
			 * Faz algumas configurações no wizard do pedido, como por exemplo, 
			 * registrar ouvintes nas trocas de paginas.
			 */
			function configWizard(){
				$('#myWizard').on('change', function(e, data) {
	                console.log('change');
	                console.log(data.step);	         
	                
	                
	                if(data.step === 2 && data.direction==='next') {
	                	$scope.onChangeInstallment();
	                    // return e.preventDefault();
	                }
	                
	                if(data.step === 3 && data.direction==='next') {
	                	
	                	$scope.parcelas.forEach(function(parcela){
	                		console.log(parcela);
	                	});
	                }
	                
	                
	                
	            });
	 
	            $('#myWizard').on('changed', function(e, data) {	    
	            	console.log(data);
	                console.log('changed');
	            });
	 
	            $('#myWizard').on('finished', function(e, data) {
	            	$.smallBox({
						title : "Congratulations! Your form was submitted",
						content : "<i class='fa fa-clock-o'></i><i>1 seconds ago...</i>",
						color : "#5F895F",
						iconSmall : "fa fa-check bounce animated",
						timeout : 4000
					});
	            });
	 
	            $('.btn-prev').on('click', function() {
	                console.log('prev');
	            });
	 
	            $('.btn-next').on('click', function() {
	                console.log('next');
	            });
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