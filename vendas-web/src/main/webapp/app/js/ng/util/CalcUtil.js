'use strict';

vendasApp.factory('CalcUtil',function(DateUtil){
	


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
			 * Data atual
			 */
			var today = new Date();
			
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
				valorParcela = parseFloat(valorParcela).toFixed(2);
			}			
			
			
			var valorUltimaParcela;
			/**
			 * Verifica se a divisão não é exata, então soma a diferença na ultima parcela
			 */
			if(valorParcela * numeroPrestacoes == valorTotal){
				valorUltimaParcela = valorParcela;
			} else {
				valorUltimaParcela = parseFloat(valorTotal - (valorParcela * (numeroPrestacoes-1))).toFixed(2) ;
			}
			
			
			
			
			/**
			 * Monta o array com as parcelas
			 */
			diasVencimento.forEach(function(diaParaVencer,i){
				today.setDate(today.getDate() + diaParaVencer);
				//Se for a ultima parcela e não possuir juros
				if(i+1 == numeroPrestacoes && taxa === 0){
					valorParcela = valorUltimaParcela;
				}
					
				
				var parcela = {
						valor : valorParcela,
						vencimento : DateUtil.format(today),
						sequencia: i+1
				};
				parcelasExemploTmp.push(parcela);
			});
			
			callback(parcelasExemploTmp);
		}
	}
});