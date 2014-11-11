'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('OrderFormController',
		['$scope','$rootScope','$route','$location','OrderService','UtilityService','ContextService','PriceTableService','CustomerService','CalcUtil','FormsPaymentService','InstallmentService','socket',
		 function OrderFormController($scope,$rootScope,$route,$location,OrderService, UtilityService, ContextService, PriceTableService, CustomerService, CalcUtil, FormsPaymentService, InstallmentService, socket) {
			
			$scope.branch = ContextService.getBranchLogged();
			
			$scope.organizationID  = ContextService.getOrganizationID();
			
			
			$scope.errorMessage = 'Testando';
			
			/**
			 * Texto do botão de navegação. Quando for a ultima tab, o texto muda.
			 */
			//$scope.buttonNextText = 'Próximo';
			
			/**
			 * Utilizado para controlar as ações da ultima aba. commit true, 
			 * significa que o pedido foi enviado.
			 */
			$scope.commit = false;
			
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
			//$scope.customerSelected = {};
			
			/**
			 * Parcelamento selecionado
			 */
			$scope.installmenetSelected = {};
			
						
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
            //$scope.valueTotalInstallment = 0.0;
            			
			/**
			 * Quando for editar um item do pedido é armazendo o item antes da
			 * edição nessa variavel, para que se o usuário cancelar a operação,
			 * seja possivel retornar os dados originais
			 */ 
			 
			$scope.orderItemEdition = undefined;
			
			/**
			 * Limite de crédito disponivel
			 */
			$scope.avaliableCreditLimit;
			
			/**
			 * Utilizados pela directiva validator
			 */			
			$scope.maximunDiscount = $scope.branch.maximumDiscount;
			
			$scope.tokenAuthorization = undefined;
									

			$scope.init = function(){
				
				configWizard();
				
				$scope.order = {
						issuanceTime : new Date(),
						organizationID: ContextService.getOrganizationID(),
						branchID: $scope.branch.branchOfficeID,
						userID: ContextService.getUserLogged().userID,
						type: 1
				};
				
				//$scope.order.issuanceTime = new Date();

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
						limit: 10
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
						limit: 10
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
							if(!isEdition){
								$scope.order.formPayment =  $scope.formsPayment[0].id;
								$scope.onChangeFormPayment();
							}

						});
				
				listInstallments(
						function(){
							if(!isEdition) {
								$scope.order.installment = $scope.installments[0];								
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
				generateInstallment();
			};
			
			
			$scope.onChangeFormPayment = function(){
				$scope.formPaymentSelected = FormsPaymentService.getByID($scope.order.formPayment);
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
					
					//Se essa variavel estiver vazia, significa que não é uma edição
					if($scope.orderItemEdition == undefined || $scope.orderItemEdition  == null){
						
						orderItem.organizationID = ContextService.getOrganizationID();
						orderItem.branchID = $scope.branch.branchOfficeID;

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
			
			/**
			 * Cancela a edição, setando os valores default para o item do pedido.
			 */
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
			 * Irá validar se estiver configurado para bloquear ou solicitar liberação, ira mostrar o dialog, 
			 * caso esteja configurado para não fazer nada, ira executar o callback, que ira salvar o pedido. 
			 */
			$scope.finish = function() {
				orderValidate(save);
			};
			
			/**
			 * Salva o Pedido/Orçamento
			 */
			$scope.save = function(){
				$('#dialogCreditLimit').modal('hide');
				save();
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
						if($scope.order.customer.installment){
							$scope.order.installment =  $scope.order.customer.installment;
						}
					}					
					
					if($scope.order.customer && $scope.order.customer.formPayment){
						if($scope.order.customer.formPayment){
							$scope.order.formPayment =  $scope.order.customer.formPayment;
							$scope.onChangeFormPayment();
						}
					}
					
					/*
					 * Se a configuração de limite de crédito do cliente for igual a bloquear ou solicitar autorização, 
					 * ira buscar o limite de crédito disponivel do cliente.
					 */ 
					if($scope.branch.actionCreditLimit === 'B' || $scope.branch.actionCreditLimit === 'L') {
						var cAvaliable = CustomerService.getAvaliableCreditLimit($scope.order.customer.id);
						
						cAvaliable.then(function(data){
							if(data.code === '200'){
								$scope.avaliableCreditLimit = data.value;
							}
						});
					}
					
					/*
					 * Se a configuração de titulos vencidos do cliente for igual a bloquear ou solicitar autorização, 
					 * ira buscar se o cliente possui titulos vencidos.
					 */ 
					if($scope.branch.actionOverdue === 'B' || $scope.branch.actionOverdue === 'L') {
						var cAvaliable = CustomerService.hasExpiratePayment($scope.order.customer.id);
						
						cAvaliable.then(function(data){
							if(data.code === '200'){
								$scope.hasExpiratePayment = data.value;
							}
						});
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
			 * val - Valor a ser formatado
			 * negativeValue - Se true, é permitido formatar numeros negativos também. 
			 * Se false, caso seja passado um numero negativo, é retornado 0 
			 */
			$scope.formatFloat = function(val, negativeValue){

				if(isNaN(val) || val <= 0){
					if(negativeValue){
						return numberFormat(val, 2, ',', '.');
					} else {
						return '0,00';
					}
				} else {
					return numberFormat(val, 2, ',', '.'); 
				}			
			};
			

			
			function generateInstallment(){
				var installment = $scope.order.installment;
								
				var dias = installment.installmentsDays.split(' ').map(Number);
				
				CalcUtil.geraParcelas($scope.valueTotal, dias,installment.tax,function(parcelasGeradas){
					$scope.parcelas = parcelasGeradas;
										
					$scope.order.ordersPayments = $scope.parcelas;
					
					var valueTotalInstallment = 0.0;
					parcelasGeradas.forEach(function(parcela){
						valueTotalInstallment += parcela.installmentValue;						
					});
					
					$scope.order.netValue = valueTotalInstallment;
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
	                if(data.step === 1 && data.direction==='next') {
	                	
	                	if($scope.order.customer == undefined){	                		
	                		UtilityService.showAlertWarning(' Opss', 'Talvez seja melhor selecionar um cliente antes de continuar.');	                		
	                		return e.preventDefault();
	                	}
	                }

	                if(data.step === 2 && data.direction==='next') {	                	
	                	$scope.onChangeInstallment();
	                	if($scope.ordersItens.length === 0){
	                		UtilityService.showAlertWarning(' Opss', 'Talvez seja melhor adicionar pelo menos um produto antes de continuar.');	                		
	                		return e.preventDefault();
	                	}
	                }
	                
	                if(data.step === 3 && data.direction==='next') {
	                	$("#btnNext").html('Finalizar Venda <i class="fa fa-check"></i>');
	                }	  
	                
	                if(data.step === 4 && data.direction==='previous') {
	                	if($scope.commit){
		                	$scope.$apply(function () {
								$scope.$emit('vendasApp:goList');
								return e.preventDefault();
							});
	                	}
	                	
	                	$("#btnNext").html('Próximo <i class="fa fa-arrow-right"></i>');
	                	
	                	/**
	                	 * Chamado para limpar os campos do dialog, caso o usuário tenha já solicitado uma autorização, 
	                	 * e volte para os itens do pedido.
	                	 */
	                	$scope.$apply(function () {
							$scope.$emit('vendasApp:clearDialog');
						});
	                }	                
	                
	            });
	 
	            $('#myWizard').on('changed', function(e, data) {
	            	/*console.log(data);
	                console.log('changed');*/
	            });
	 
	            $('#myWizard').on('finished', function(e, data) {
	            	if(!$scope.commit){
	            		$scope.finish();
	            	} else {	            		
	            		$route.reload();
	            	}
	            	
	            	
	            });
	 
	            $('.btn-prev').on('click', function() {
	            });
	 
	            $('.btn-next').on('click', function() {
	            });
			}
			
			
			/**
			 * 
			 * Solicita a autoriação para vender  para cliente sem limite de crédito
			 */
			$scope.solicitAuthorization = function(){
				
				$scope.showJustificationText = false;
				$scope.showProgressBar = true;

				/*setTimeout(function (){
					$scope.$apply(function() {
						$scope.waitingAuthorization = false;
						$scope.acceptAuthorization = true;
					});
				}, 5000);*/
				
				$scope.tokenAuthorization = Math.floor( ( Math.random() * 100000000 ) + 1 );
				
				var authorizationType = '';
				
				if($scope.avaliableCreditLimit < $scope.order.netValue){
					authorizationType += 'Cliente sem limite de crédito suficiente. ';
				}
				
				if($scope.hasExpiratePayment){
					authorizationType += 'Cliente com parcelas vencidas';
				}
				
				
				
				
				
				socket.emit('authorization request',  
						{
							roomName: $scope.organizationID,
							token: $scope.tokenAuthorization,
							user: {
								userID : ContextService.getUserLogged().userID,
								name : ContextService.getUserLogged().name,
							},
							details: {
								authorizationInfo : $scope.authorizationInfo, 
								customer : $scope.order.customer,
								netValue : $scope.order.netValue,
								formPaymentSelected : $scope.formPaymentSelected,
								installment : $scope.order.installment,
								avaliableCreditLimit : $scope.avaliableCreditLimit,
								authorizationType: authorizationType
							}
						}
				 );
				
				socket.on('authorization response', function(authorizationResponse){
					$scope.$apply(function() {
						$scope.authorizationResponse = authorizationResponse;
						$scope.showJustificationText = false;
						$scope.showProgressBar = false;
						
						if(authorizationResponse.authorized){
							$scope.authorizationLabel = 'Venda autorizada pelo usuário '+ authorizationResponse.admin.name; 
						} else {
							$scope.authorizationLabel = 'Venda não foi autorizada pelo usuário '+authorizationResponse.admin.name;
						}
						 
					});
				});
			};
			
			/**
			 * Controles dos componentes do dialog de bloqueio/liberação
			 */
			$scope.showJustificationText = false;
			$scope.showBtnSolicitAuthorization = false;
			//$scope.showFinalizeButton;
			
			
			/**
			 * Valida se o pedido, se estiver tudo ok, executa o callback
			 */
			function orderValidate(callback){
				
				if($scope.hasExpiratePayment){
					//$scope.authorizationLabel = 'O cliente possui parcelas vencidas.';
				}
				
				
				if($scope.branch.actionCreditLimit === 'B' || $scope.branch.actionCreditLimit === 'L') {
					if($scope.branch.actionCreditLimit === 'L'){
				        $scope.$apply(function(){
				        	$scope.showJustificationText = true;
				        	$scope.showBtnSolicitAuthorization = true;
				        });						
					}
					
					if($scope.avaliableCreditLimit < $scope.order.netValue){
						$('#dialogCreditLimit').modal();
						return;
					}
				}
				
				if($scope.branch.actionOverdue === 'B' || $scope.branch.actionOverdue === 'L') {	
					if($scope.branch.actionOverdue === 'L'){

				        $scope.$apply(function(){
				        	$scope.showJustificationText = true;
				        });
					}
					
					if($scope.hasExpiratePayment){
						$('#dialogCreditLimit').modal();
						return;
					}
				}
				
				callback();
			}
			
			/**
			 * Salva o pedido
			 */
			function save() {
				var orderWrapper = {};
				
				orderWrapper.order = $scope.order;				
				orderWrapper.order.ordersItens =  $scope.ordersItens;				
				
				OrderService.save(orderWrapper).then(function(toReturn){
					if(toReturn.code == '200'){
						$scope.commit = true;
						$("#btnNext").html('Novo Pedido <i class="fa fa-plus"></i>');
						$("#btnOrders").html('<i class="fa fa-reorder"></i> Histórico de Pedidos ');
						
						$scope.order.id = toReturn.value.id;
						UtilityService.showAlertSucess('Sucesso.', 'Pedido salvo com sucesso!!');
					} else {
						UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
					}
				});
			}
			
			/**
			 * Foi utilizado dessa forma, porque se colocar o location dentro do on do jquery, o redirecionamento não funciona
			 */
			$scope.$on('vendasApp:goList', function (event) {
				event.stopPropagation();
				$location.path('/pedido/lista-pedido');
			});
			
			
			/**
			 * Foi utilizado dessa forma, porque não atualiza os scope dentro do jquery
			 */
			$scope.$on('vendasApp:clearDialog', function (event) {
				event.stopPropagation();
				$scope.showJustificationText = true;
				$scope.showProgressBar = false;
				$scope.authorizationResponse = undefined;
				$scope.authorizationLabel = undefined;
				
			});
		}]);