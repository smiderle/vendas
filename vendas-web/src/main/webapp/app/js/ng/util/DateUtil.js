'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.factory('DateUtil',function(){

	return {		

		/**
		 * Formata uma data no formato dd/MM/yyyy. 
		 * Espera uma date e o separador. Caso seja passada uma string no lugar da data,
		 * é instanciado um date com a string passada por parametro 
		 */
		format : function(date, separator, showMinutes){
			
			var formated='';
			
			if(!(date instanceof Date)){
				date = new Date(date.replace('-', '/').replace('-', '/'));
			}
			
			if(date && date instanceof Date){
				var dd = date.getDate(),
				MM = date.getMonth()+1,
				yyyy = date.getFullYear(),
				HH = date.getHours(),
				mm = date.getMinutes();
				
				//Insere um zero na frente do numero, caso seja menor que 10
				dd = dd < 10 ? '0'+dd : dd;
				MM = MM < 10 ? '0'+MM : MM;
				
				separator = separator || '/';
				if(date instanceof Date){
					formated = dd+separator+ MM + separator +yyyy;
				}
				
				if(showMinutes){
					if(HH < 10){ HH = '0'+HH;};
					if(mm < 10){ mm = '0'+mm;};
					formated  = formated + ' '+ HH + ':'+ mm; 
				}
			}
			
			return formated;			
		},
		
		/**
		 * Retorna os millisegundos da data no formato dd/MM/YYYY
		 * Fonte: https://stackoverflow.com/questions/7151543/convert-dd-mm-yyyy-string-to-date/7151626#7151626
		 */
		getTime : function(ddMYYYY){
			return new Date(ddMYYYY.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3")).getTime();			
		},
		
		/**
		 * Valida se a data é no formato dd/MM/YYYY e válida
		 */
		isValidDate : function(input){
			 var reg = /(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d/;
			  if (input.match(reg)) {
			    return true;
			  } else {
				  return false;
			  }
		}
	};
});