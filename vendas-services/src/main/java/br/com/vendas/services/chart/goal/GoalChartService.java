package br.com.vendas.services.chart.goal;

import java.util.Date;
import java.util.List;

import br.com.vendas.domain.chart.LineChart;
import br.com.vendas.services.support.ServiceResponse;

public interface GoalChartService {
	
	/**
	 * Retorna o valor total de vendas acumulado diário, do mes passado por parametro, ou seja, o valor sera acumulado ao valor do dia anterior
	 * @param dtInitial
	 * @param dtFinal
	 */
	LineChart getLineChartTotalValueAccumulatedDailyBetweenMonthAndUserID( Integer userID, Date date ) ;
	
	
	/**
	 * Retorna o valor total de vendas diário, do mes passado por parametro
	 * @param userID
	 * @param date
	 * @return
	 */
	LineChart getLineChartTotalValueDailyBetweenMonthAndUserID( Integer userID, Date date ) ;
	
	/**
	 * Retorna uma lista de coordenadas para o mes atual com valores acumulativos, ou seja, sera somado o dia anterior ao dia atual
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<LineChart>> currentMonth(Integer userID);
	
	/**
	 * Retorna uma lista de coordenadas para o mes anterior com valores acumulativos, ou seja, sera somado o dia anterior ao dia atual
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<LineChart>> previousMonth(Integer userID);
	


}
