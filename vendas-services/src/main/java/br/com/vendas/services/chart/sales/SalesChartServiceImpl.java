package br.com.vendas.services.chart.sales;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.core.util.DateUtil;
import br.com.vendas.dao.order.OrderDAO;
import br.com.vendas.domain.chart.LineChart;
import br.com.vendas.services.chart.GenericChartService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class SalesChartServiceImpl extends GenericChartService implements SalesChartService {
	
	@Inject
	private OrderDAO orderDAO;


	@Override
	public ServiceResponse<List<LineChart>> currentMonth(Integer userID) {
		List<LineChart> toReturn = new ArrayList<>();
		
		LineChart metaAtingida = getLineChartTotalValueDailyBetweenMonthAndUserID( userID, new Date() );		
		toReturn.add( metaAtingida );
		return ServiceResponseFactory.create( toReturn );
	}
	
	@Override
	public ServiceResponse<List<LineChart>> currentMonthByBranch(Integer branchID) {
		List<LineChart> toReturn = new ArrayList<>();
		
		LineChart metaAtingida = getLineChartTotalValueDailyBetweenMonthAndBranchID( branchID, new Date() );		
		toReturn.add( metaAtingida );
		return ServiceResponseFactory.create( toReturn );
	}

	@Override
	public ServiceResponse<List<LineChart>> previousMonth(Integer userID) {	
		List<LineChart> toReturn = new ArrayList<>();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime( new Date() );
		calendar.add( Calendar.MONTH, -1 );
		
		LineChart vendas = getLineChartTotalValueDailyBetweenMonthAndUserID( userID, calendar.getTime() );		
		toReturn.add( vendas );

		return ServiceResponseFactory.create( toReturn );
	}
	
	@Override
	public ServiceResponse<List<LineChart>> previousMonthByBranch(Integer branchID) {
		List<LineChart> toReturn = new ArrayList<>();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime( new Date() );
		calendar.add( Calendar.MONTH, -1 );
		
		LineChart vendas = getLineChartTotalValueDailyBetweenMonthAndBranchID( branchID, calendar.getTime() );		
		toReturn.add( vendas );

		return ServiceResponseFactory.create( toReturn );
	}



	public LineChart getLineChartTotalValueDailyBetweenMonthAndUserID( Integer userID, Date date ) {
		LineChart lineChart = new LineChart();
		lineChart.setKey("Total de vendas diária");

		List<Object[]> completarDatasQueFaltam = getTotalValueDailyBetweenMonthAndUserID(userID, date);

		lineChart.setValues( completarDatasQueFaltam );
		return lineChart;
	}
	
	public LineChart getLineChartTotalValueDailyBetweenMonthAndBranchID( Integer branchID, Date date ) {
		LineChart lineChart = new LineChart();
		lineChart.setKey("Total de vendas diária");

		List<Object[]> completarDatasQueFaltam = getTotalValueDailyBetweenMonthAndBranchID(branchID, date);

		lineChart.setValues( completarDatasQueFaltam );
		return lineChart;
	}


	private List<Object[]> getTotalValueDailyBetweenMonthAndUserID( Integer userID, Date date ) {

		Calendar dtInitial = DateUtil.initMonth( date );
		Calendar dtFinal = DateUtil.finalMonth( date );

		List<Object[]> valorTotalDiarioEntreDatasEUsuario = orderDAO.getTotalValueDailyBetweenDateAndUserID(userID, dtInitial.getTime() , dtFinal.getTime() );

		return completarDatasQueFaltam(valorTotalDiarioEntreDatasEUsuario, 1, new GregorianCalendar().getActualMaximum( Calendar.DAY_OF_MONTH ));

	}
	
	private List<Object[]> getTotalValueDailyBetweenMonthAndBranchID( Integer branchID, Date date ) {

		Calendar dtInitial = DateUtil.initMonth( date );
		Calendar dtFinal = DateUtil.finalMonth( date );

		List<Object[]> valorTotalDiarioEntreDatasEUsuario = orderDAO.getTotalValueDailyBetweenDateAndBranchID(branchID, dtInitial.getTime() , dtFinal.getTime() );

		return completarDatasQueFaltam(valorTotalDiarioEntreDatasEUsuario, 1, new GregorianCalendar().getActualMaximum( Calendar.DAY_OF_MONTH ));

	}

	
}
