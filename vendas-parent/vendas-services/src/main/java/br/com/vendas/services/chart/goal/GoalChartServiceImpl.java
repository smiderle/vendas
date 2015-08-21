package br.com.vendas.services.chart.goal;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import br.com.vendas.core.util.MoneyUtil;
import br.com.vendas.dao.order.OrderDAO;
import br.com.vendas.dao.targets.GoalDAO;
import br.com.vendas.domain.chart.LineChart;
import br.com.vendas.domain.target.Goal;
import br.com.vendas.services.chart.GenericChartService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class GoalChartServiceImpl extends GenericChartService implements GoalChartService {

	
	@Inject
	private GoalDAO goalDAO;
	@Inject
	private OrderDAO orderDAO;
	
	
	/**
	 * Retorna a meta estabelecida para determinado mes 
	 * @param userID
	 * @return
	 */
	public LineChart getGoal(Integer organizationID, Integer branchID, Integer userID , Date date) {
		
		LineChart meta = new LineChart();
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime( date );
		
		int yearMonth = Integer.parseInt( new SimpleDateFormat("YYYYMM").format( date ) );
		Goal targets = goalDAO.findGoalByUserAndMonth(organizationID, branchID,userID, yearMonth);
		
		List<Object[]> valuesMeta = new ArrayList<>();
		valuesMeta.add( new Object[] { 1, new BigDecimal( 0 ) } );
		
		if(targets != null ){
			Double value = targets.getValue() != null ? targets.getValue() : 0.0;
			BigDecimal valueBig = new BigDecimal( String.valueOf( value ) );
			valuesMeta.add( new Object[] { calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) , valueBig });
		} else {
			valuesMeta.add( new Object[] { calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) , 0 });
		}
		
		
		meta.setKey("Meta");
		meta.setValues(valuesMeta);
		
		return meta;		
	}
	
	@Override
	public ServiceResponse<List<LineChart>> currentMonth(Integer organizationID, Integer branchID, Integer userID) {	
		List<LineChart> toReturn = new ArrayList<>();
		
		LineChart metaEstabelecida = getGoal( organizationID, branchID, userID, new Date() );
		LineChart metaAtingida = getLineChartTotalValueAccumulatedDailyBetweenMonthAndUserID(organizationID, branchID,  userID, new Date() );
		
		toReturn.add( metaAtingida );
		toReturn.add( metaEstabelecida );
		
		return ServiceResponseFactory.create( toReturn );
	}
	
	@Override
	public ServiceResponse<List<LineChart>> previousMonth(Integer organizationID, Integer branchID, Integer userID) {	
		List<LineChart> toReturn = new ArrayList<>();
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime( new Date() );
		calendar.add( Calendar.MONTH, -1 );
		
		
		LineChart metaEstabelecida = getGoal( organizationID, branchID, userID, calendar.getTime() );
		LineChart metaAtingida = getLineChartTotalValueAccumulatedDailyBetweenMonthAndUserID( organizationID, branchID,  userID, calendar.getTime() );
		
		toReturn.add( metaAtingida );
		toReturn.add( metaEstabelecida );
		
		return ServiceResponseFactory.create( toReturn );
	}
		
	
	@Override
	public LineChart getLineChartTotalValueAccumulatedDailyBetweenMonthAndUserID( Integer organizationID, Integer branchID, Integer userID, Date date ) {
		LineChart lineChart = new LineChart();
		lineChart.setKey("Atingido");
		
		List<Object[]> completarDatasQueFaltam = getTotalValueDailyBetweenMonthAndUserID(organizationID, branchID, userID, date);
		
		BigDecimal total = new BigDecimal(0);
		for (Object[] objects : completarDatasQueFaltam) {
			total = total.add( MoneyUtil.rounding( ( BigDecimal ) objects[1] ) ) ;
			objects[1] = total;
		}
		
		
		lineChart.setValues( completarDatasQueFaltam );
		return lineChart;
	
	}
	
	@Override
	public LineChart getLineChartTotalValueDailyBetweenMonthAndUserID(Integer organizationID, Integer branchID,  Integer userID, Date date ) {
		LineChart lineChart = new LineChart();
		lineChart.setKey("Atingido");
		
		List<Object[]> completarDatasQueFaltam = getTotalValueDailyBetweenMonthAndUserID(organizationID, branchID,  userID, date);
		
		lineChart.setValues( completarDatasQueFaltam );
		return lineChart;
	}
	
	
	public List<Object[]> getTotalValueDailyBetweenMonthAndUserID(Integer organizationID, Integer branchID, Integer userID, Date date ) {
				
		Calendar dtInitial = DateUtil.initMonth( date );
		Calendar dtFinal = DateUtil.finalMonth( date );

		List<Object[]> valorTotalDiarioEntreDatasEUsuario = orderDAO.getTotalValueDailyBetweenDateAndUserID(organizationID, branchID, userID, dtInitial.getTime() , dtFinal.getTime() );

		return completarDatasQueFaltam( valorTotalDiarioEntreDatasEUsuario, 1, new GregorianCalendar().getActualMaximum( Calendar.DAY_OF_MONTH ) );
		
	}
		
}
