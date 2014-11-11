'use strict';

vendasApp.factory('FormsPaymentService', function() {
	var payments =[ {
		description : 'Dinheiro',
		id : 1
	}, {
		description : 'Cheque',
		id : 2
	}, {
		description : 'Boleto',
		id : 3
	}, {
		description : 'Cartão',
		id : 4
	}, {
		description : 'Duplicata',
		id : 5
	}, {
		description : 'Promissória',
		id : 6
	}, ];

	return {

		/**
		 * Retorna as formas de pagamento. Por enquanto ta fixo. Posteriormente,
		 * avaliar a necessidade de persistir isso em banco.
		 */
		getFormsPayment : function() {
			return payments;
		},

		getByID : function(id){
			var i = 0;
			for(;i < payments.length; i++){
				if(payments[i].id === id){
					return payments[i];
				}				
			}


		}
	};
});
