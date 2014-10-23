'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.factory('CalcUtil',function(DateUtil, ContextService){
	
	return {		

		/**
		 * Executa o callback passando como parametro as parcelas geradas. 
		 * Parametros:
		 *  valorTotal: Valor total a ser dividido
		 *  diasVencimento: os dias após a compra que a parcela ira vencer Ex: [ 30, 60, 90 ]
		 *  
		 * Retorno:
		 * Retorna um array de parcelas Ex:
		 *  [{
		 * 	  valor : 25.34,
		 *	  vencimento : 25/10/2018,
		 *	  sequencia: 1
		 *  }]	
		 *  
		 */
		 geraParcelas: function(valorTotal, diasVencimento,taxa, callback){
			 var expo, valorParcela;
			 
			 
			 /*if (op==2 && !isNaN(ventrad)) {
				 valorTotal = valorTotal-ventrad
				}*/
			 
			 taxa = parseFloat(taxa)/100;
			 			 
			 
			 /**
			 * Numero total de parcelas
			 */
			var numeroPrestacoes = diasVencimento.length;
			 			 
			
			var parcelasExemploTmp = [];

			
			/**
			 * Calcula o valor de cada parcela. Se for informado alguma taxa de juros, é aplicado o juros em cada parcela
			 */
			if(taxa > 0){
				 expo = Math.pow((1+taxa), numeroPrestacoes);
				 //Parcelamento com entrada, ou seja, a primeira parcela é hoje.
				 var comEntrada = diasVencimento[0] === 0;
				 valorParcela = Math.round(100*valorTotal*((taxa*expo)/(expo-1)))/100;
				 /*if(comEntrada){
					 var valorRestante = valorTotal -(valorTotal / numeroPrestacoes);
					 valorParcela = Math.round(100*valorRestante*((taxa*expo)/(expo-1)))/100;
				 } else {
					 valorParcela = Math.round(100*valorTotal*((taxa*expo)/(expo-1)))/100;
				 }*/
				 
			} else {
				valorParcela = valorTotal / numeroPrestacoes;
				valorParcela = parseFloat(valorParcela).toFixed(2)/1;
			}			
			
			
			var valorUltimaParcela;
			/**
			 * Verifica se a divisão não é exata, então soma a diferença na ultima parcela
			 */
			if(valorParcela * numeroPrestacoes == valorTotal){
				valorUltimaParcela = valorParcela;
			} else {
				valorUltimaParcela = parseFloat(valorTotal - (valorParcela * (numeroPrestacoes-1))).toFixed(2)/1 ;
			}
			
			
			
			
			/**
			 * Monta o array com as parcelas
			 */
			diasVencimento.forEach(function(diaParaVencer,i){
				var date = new Date();
				date.setDate(new Date().getDate() + diaParaVencer);
				//Se for a ultima parcela e não possuir juros
				if(i+1 == numeroPrestacoes && taxa === 0){
					valorParcela = valorUltimaParcela;
				}
					
				
				var parcela = {
						installmentValue : valorParcela,
						expirationDate : date,
						sequence: i+1,
						organizationID: ContextService.getOrganizationID(),
						branchID: ContextService.getBranchLogged().branchOfficeID
				};
				
				parcelasExemploTmp.push(parcela);
			});
			
			callback(parcelasExemploTmp);
		},
		
		/**
		 * Retorna o valor, aplicando o percentual. O /1 , é para forçar um retorno long, pois o toFixed retorna uma string
		 * val - Valor 
		 * percentage - percentual
		 * discount - Se é um desconto, caso contrário é um acréscimo
		 * Exemplo: function(80, 10, true) - Vai retornar 72
		 *          function(80, 10, false) - Vai retornar 88
		 * 
		 */
		applyPercentage : function(val, percentage, discount){
			var variant = val * percentage / 100;
			if(discount){
				return parseFloat( val - variant ).toFixed(2) /1;
			} else {
				return parseFloat( val + variant ).toFixed(2) /1;				
			}			
		},
		
		/**
		 * Retorna o valor em reais que equivale determinado percentual.
		 * Exemplo: function(50, 10) - Retorna 5, ou seja, 10 porcento de 50 é 5. 
		 * 
		 */
		getValueByPercentage : function(val, percentage, discount){
			return val * percentage / 100;						
		},
		
		/**
		 * Retorna o percentual de um valor referente a outro
		 * originalValue - Valor cheio, sem o desconto
		 * changedValue - O valor com o desconto
		 */
		getPercentageByValue : function(originalValue, changedValue){
			//if(originalValue >= changedValue) {
				var percentage = changedValue * 100 / originalValue;
				return percentage;
			/*} else {
				var percentage = changedValue * 100 / originalValue;
				return percentage - 100;
			}*/			
		}
	};
});