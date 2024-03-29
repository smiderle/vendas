package br.com.vendas.api.rest.v1.chart;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.chart.LineChart;
import br.com.vendas.services.chart.goal.GoalChartService;
import br.com.vendas.services.chart.sales.SalesChartService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;

@RequestMapping(value="/private/v1/chart")
@Controller
public class ChartRest {
	
	@Inject
	private GoalChartService goalChartService;
	
	@Inject
	private SalesChartService salesChartService; 
	
	
	/**
	 * Meta do mes atual
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartGoalCurrentMonth", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartTargetsCurentMonth(Integer organizationID, Integer branchID,  Integer userID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = goalChartService.currentMonth( organizationID, branchID, userID );
	
		return ResponseBuilder.build( currentMonth );
		
	}
	

	/**
	 * Metas do mes anterior
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartGoalPreviousMonth", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartTargetsPreviouesMonth(Integer organizationID, Integer branchID,  Integer userID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = goalChartService.previousMonth(organizationID, branchID,  userID );
		
		return ResponseBuilder.build( currentMonth );
		
	}
	
	/**
	 * Vendas do mes anterior
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartSalesPreviousMonth", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartSalesPreviouesMonth(Integer organizationID, Integer branchID, Integer userID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = salesChartService.previousMonth(organizationID, branchID, userID );
		
		return ResponseBuilder.build( currentMonth );
		
	}
	
	/**
	 * Vendas do mes anterior
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartSalesCurrentMonth", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartSalesCurrentMonth(Integer organizationID, Integer branchID, Integer userID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = salesChartService.currentMonth( organizationID, branchID, userID );
		
		return ResponseBuilder.build( currentMonth );
		
	}
	
	
	/**
	 * Vendas do mes anterior de determinada filial
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartSalesCurrentMonthByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartSalesCurrentMonthByBranch( Integer organizationID, Integer branchID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = salesChartService.currentMonthByBranch( organizationID, branchID );
		
		return ResponseBuilder.build( currentMonth );
		
	}
	
	/**
	 * Vendas do mes anterior de determinada filial
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="getChartSalesPreviousMonthByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getChartSalesPreviouesMonthByBranch( Integer organizationID, Integer branchID ) {
		
		ServiceResponse<List<LineChart>> currentMonth = salesChartService.previousMonthByBranch( organizationID, branchID );
		
		return ResponseBuilder.build( currentMonth );
		
	}

}
