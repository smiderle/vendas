/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	
	'use strict';

	vendasApp.controller('PaymentListController',['$scope','$location','PaymentService' ,'ContextService','UtilityService','FormsPaymentService', 'DateUtil',
	        function PaymentListController($scope,$location, PaymentService, ContextService,UtilityService,FormsPaymentService, DateUtil) {
		
		var noMorePayments = false,
			userLogged = ContextService.getUserLogged();
		
		$scope.payments = [];
		
		$scope.filterStatusInstallment = [
		            	                  { description: 'Paga', code: 'PAID'},
		            	                  { description: 'Pendente', code: 'PENDING'},
		            	                  { description: 'Atrasada', code: 'OVERDUE'}                
		            	                  ];
		
		
		
		$scope.init = function(){
			console.log('Moment');		
			console.log(moment().subtract(3, 'days').calendar())
			$scope.listPedingPayments();
			
			/**
			 * Formas de Pagamento, utilizados no filtro avançado
			 */
			$scope.formsPayment = FormsPaymentService.getFormsPayment();
		};
		
		$scope.listPedingPayments = function(){
			var organizationID = ContextService.getOrganizationID();
			var branchID = ContextService.getBranchLogged().branchOfficeID;
			var cPayments;
			
			if($scope.complexFilter){
				var cf = $scope.complexFilter;
				
				if(cf.issuanceDateGte && !DateUtil.isValidDate(cf.issuanceDateGte)){
					delete cf.issuanceDateGte;
				}
				
				if(cf.issuanceDateLte && !DateUtil.isValidDate(cf.issuanceDateLte)){
					delete cf.issuanceDateLte;
				}
				
				if(cf.expirationDateGte && !DateUtil.isValidDate(cf.expirationDateGte)){
					delete cf.expirationDateGte;
				}
				
				if(cf.expirationDateLte && !DateUtil.isValidDate(cf.expirationDateLte)){
					delete cf.expirationDateLte;
				}
							
				
				cPayments = $scope.busyLoader = PaymentService.getByComplexFilter( organizationID, branchID, cf.customerID, DateUtil.getTime(cf.issuanceDateGte), DateUtil.getTime(cf.issuanceDateLte), DateUtil.getTime(cf.expirationDateGte), DateUtil.getTime(cf.expirationDateLte), cf.status, cf.formPayment, $scope.payments.length);
			} else if($scope.filter){
				cPayments = $scope.busyLoader = PaymentService.getPendingByFilter( organizationID, branchID, $scope.filter, $scope.payments.length);					
			} else {
				cPayments = $scope.busyLoader = PaymentService.getAllPendingByOrganizationID(organizationID,branchID, $scope.payments.length);
				
			}
			
			cPayments.then(function(toReturn){
				
				if(toReturn.rowCount === 0){
					noMorePayments = true;
				}
				
				buildPaymentsDataTable(toReturn.value);
			});
		};
		
		/**
		 * Chamado no botão de pesquisa do filtro
		 */
		$scope.findPaymentsByFilter = function(){
			$scope.complexFilter = undefined;
			resetView();
			$scope.listPedingPayments();
		};
		
		/**
		 * Chamado no botão do filtro avançado
		 */
		$scope.findByComplexFilter = function(){
			resetView();
			$scope.listPedingPayments();
		};
		
		$scope.showPaymentDetails = function(){

			//Recupera o produto selecionado no datatable.
			var paymentSelectedID = $( "input:checked" ).val();
			var payments = $scope.payments;

			var i;
			for(i in payments){
				var payment = payments[i];
				if(payment && payment.id == paymentSelectedID ){
					PaymentService.setPayment(payment, userLogged.userID);
					$location.path('/financeiro/detalhes-contas-receber');
					break;
				}
			}
		};
		
		/**
		 * Pagar (efetuar baixa) a parcela
		 */	
		$scope.pay = function(){
			
			
			$.SmartMessageBox({
				title : "Efetuar Baixa",
				content : "Deseja realmente efetuar baixa da parcela selecionada?",
				buttons : '[Não][Sim]'
			}, function(ButtonPressed) {
				if (ButtonPressed === "Sim") {
					
					var paymentSelectedID = $( "input:checked" ).val();
					var aPay = PaymentService.setPaid(paymentSelectedID, userLogged.userID);
					
					
					aPay.then(function(toReturn){
						if(toReturn.code == '200'){
							resetView();
							$scope.listPedingPayments();
							UtilityService.showAlertSucess('Sucesso.', 'Baixa efetuada com sucesso!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}					
					});
					
				}
			});
		};
		
		/**
		 * Fica ouvindo o evento, que é disparado quando o usuario clicar na ultima pagina do datatable
		 */
		$scope.$on('vendasApp:isLastPage', function (event) {		
			event.stopPropagation();
			if(!noMorePayments){
				//listOrders($scope.orders.length);
				$scope.listPedingPayments();
			}
		});
		
		function buildPaymentsDataTable(payments){
			var paymentRows = [],
			paymentsSize = $scope.payments.length;
			
			payments.forEach(function(payment, index){
				
				if(payment && payment.id){
					
					$scope.payments.push(payment);
					
					var status = $scope.getPaymentStatus(payment),
					labelStatus = '';
					if(status === 'Paga'){
						labelStatus = '<span class="label label-success txt-color-white padding-5 pull-left">&nbsp;&nbsp;&nbsp;Paga&nbsp;&nbsp;&nbsp;&nbsp;</span>';
					} else if(status === 'Pendente'){
						labelStatus = '<span class="label label-warning txt-color-white padding-5 pull-left">Pendente</span>';
					} else if(status === 'Atrasada'){
						labelStatus = '<span class="label bg-color-redLight txt-color-white padding-5 pull-left">Atrasada</span>';
					}
					
					
					//Adiciona um array com as colunas que irão ser apresentadas no dataTable
					paymentRows.push([
					                '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+payment.id+'"><i></i></label>',						                						                
					                /*paymentsSize + index + 1,
					                payment.id, */
					                payment.order.id,
					                payment.order.customer.customerID +' - '+ payment.order.customer.name,
					                moment(payment.expirationDate).format('L'),
					                payment.documentNumber,
					                payment.observation,
					                'R$ '+ parseFloat(payment.installmentValue).toFixed(2).replace('.', ','),
					                payment.sequence,
					                labelStatus				                
					                
					                ]);
				}
			});

			//Seta os pagamentos
			$scope.rowsDataTable = paymentRows;
			
		};
		

		/**
		 * Verfica qual o status da parcela.
		 * 1 - Para parcela que já foi paga.
		 * 2 - Para parcela pendente.
		 * 3 - Para parcela vencida.
		 */
		$scope.getPaymentStatus = function(payment){
			if( payment.paymentDate){
				return 'Paga';
			} 
			
			//Se não tiver paga, verifica se a parcela já venceu
			var expirationDate = new Date(payment.expirationDate);
			var currentDate = new Date();
			expirationDate.setHours(0, 0, 0, 0);
			currentDate.setHours(0, 0, 0, 0);
			
			if(expirationDate < currentDate){		
				return 'Atrasada';
			}
			
			return 'Pendente';
		};
		
		/**
		 * Restaura todos os dados da tela
		 */
		function resetView(){
			$scope.payments = [];
			noMorePayments = false;
			
			clearDatateble();
			
			$('#btnPaymentDetails').attr("disabled","disabled");
			$('#btnPayInstallment').attr("disabled","disabled");		
		}
		
		/**
		 * Remove todos os elementos do datatable
		 */
		function clearDatateble(){
			var dataTable = $('#datatable_payment').dataTable();
			dataTable.fnClearTable(0);
			dataTable.fnDraw();
		}
		
	}]);

})();
