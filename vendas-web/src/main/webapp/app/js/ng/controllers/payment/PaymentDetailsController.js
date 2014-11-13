'use strict';

vendasApp.controller('PaymentDetailsController',['$scope','PaymentService' ,'ContextService','UtilityService','FormsPaymentService', 'DateUtil',
        function PaymentDetailsController($scope, PaymentService, ContextService,UtilityService,FormsPaymentService, DateUtil) {
	
	/**
	 * A parcela que foi selecionada na listagem de titulos
	 */
	$scope.payment = {}; 
	
	/**
	 * A parcela que esta selecionado para listar  nos campos. Quando for selecionado uma outra parcela la na aba de "demais parcelas"
	 * ira ser setado nessa variavel
	 */
	$scope.paymentSelected = {};
	
	$scope.init = function(){
		$scope.payment = PaymentService.getPayment();
		
		if($scope.payment){
			$scope.paymentSelected =  $scope.payment;
			var cPayments = PaymentService.getByID($scope.payment.id);
			
			cPayments.then(function(toReturn){
				if(toReturn.code === '200') {
					/*
					 * Como somente a lista de parcelas e o parcelamento foi carregado a mais nessa request, 
					 * então é atribuido só alista ao objeto que ja esta carregado
					 */
					$scope.payment.order.ordersPayments = toReturn.value.order.ordersPayments;
					$scope.payment.order.installment = toReturn.value.order.installment;
					
					$scope.formPayment = FormsPaymentService.getByID($scope.payment.order.formPayment);
				}
				
			});
		}
	};
	
	/**
	 * Mostra os detalhes da parcela no formulario 
	 */
	$scope.showInstallment = function(index){
		$scope.paymentSelected = $scope.payment.order.ordersPayments[index];
		$scope.paymentSelected.order = $scope.payment.order;
		//$scope.paymentSelected.order.ordersPayments = $scope.payment.order.ordersPayments;
		$scope.paymentSelected.installment = $scope.payment.order.installment;
		
		//Seleciona a primeira tab
		$('.nav-tabs li:eq(0) a').tab('show');		
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
	
}]);
