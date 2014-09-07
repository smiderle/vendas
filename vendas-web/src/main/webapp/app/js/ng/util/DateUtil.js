'use strict';

vendasApp.factory('DateUtil',function(){

	return {		

		/**
		 * Formata uma data no formato dd/MM/yyyy 
		 */
		format : function(date, separator){
			
			var formated='';
			
			if(date && date instanceof Date){
				var dd = date.getDate(),
				MM = date.getMonth()+1,
				yyyy = date.getFullYear();
				
				//Insere um zero na frente do numero, caso seja menor que 10
				dd = dd < 10 ? '0'+dd : dd;
				MM = MM < 10 ? '0'+MM : MM;
				
				separator = separator || '/';
				if(date instanceof Date){
					formated = dd+separator+ MM + separator +yyyy;
				}
			}
			
			return formated;			
		}	
	};
});