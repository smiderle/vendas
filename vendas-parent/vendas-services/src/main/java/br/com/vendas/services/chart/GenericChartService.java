package br.com.vendas.services.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class GenericChartService {
	

	protected List<Object[]> completarDatasQueFaltam( List<Object[]> dias, int initDay, int finalDay ) {

		List<Object[]> diasRetorno = new ArrayList<>(31);

		Integer diaAtual = 1;

		if( dias.size() > 0 ) {

			for ( Object[] dia : dias ) {
				//Por questão de arredondamento, é convertido o double para um biginteget
				dia[1] = new BigDecimal( String.valueOf(dia[1]) ) ;
				
				if( dia[0] == diaAtual ) {
					diasRetorno.add( dia );
					diaAtual ++;
				} else {
					Integer diaInt = (Integer) dia[0];
					//Cria do ultimo dia que foi criado até o dia anterior ao próximo existente. Depois adiciona o próximo na lista também.					
					gerarDeAte( diasRetorno, diaAtual, diaInt -1 );
					diasRetorno.add( dia );
					diaAtual  = diaInt +1;
				}
			}

			

		}
		
		/**
		 * Se extir mais dias até o final do mês, é completado a lista com valores diários zerado
		 */
		int actualMaximum = new GregorianCalendar().getActualMaximum( Calendar.DAY_OF_MONTH);

		if( diaAtual < actualMaximum );{
			gerarDeAte( diasRetorno, diaAtual , actualMaximum );
		}

		return diasRetorno;

	}

	/**
	 * Adiciona arrays do dia initDay até finalDay
	 * @param dias
	 * @param initDay
	 * @param finalDay
	 */
	protected void gerarDeAte(List<Object[]> dias, int initDay, int finalDay ) {
		for (int i = initDay ; i <= finalDay; i++) {
			dias.add( new Object[]{ i, new BigDecimal(0) } );
		}		
	}

}
