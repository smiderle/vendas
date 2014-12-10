package br.com.vendas.services.chart.sales;

import java.util.List;

import br.com.vendas.domain.chart.LineChart;
import br.com.vendas.services.support.ServiceResponse;

public interface SalesChartService {

	
	/**
	 * Retorna uma lista de coordenadas para o mes atual
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<LineChart>> currentMonth(Integer userID);
	
	/**
	 * Retorna uma lista de coordenadas para o mes anterior
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<LineChart>> previousMonth(Integer userID);
	
	
	/**
	 * Retorna uma lista de coordernadas para o mes anterior de determinada filial
	 * @param branchID
	 * @return
	 */
	ServiceResponse<List<LineChart>> previousMonthByBranch(Integer branchID);
	

	/**
	 * Retorna uma lista de coordernadas para o mes atual de determinada filial
	 * @param branchID
	 * @return
	 */
	ServiceResponse<List<LineChart>> currentMonthByBranch(Integer branchID);
}
