package br.com.vendas.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	/**
	 * Retorna a data inicial do mes, ou seja
	 * se hoje for 04/12/2014 23:41 ira retornar 01/12/2014 00:00
	 */
	public static Calendar initMonth( Date date ){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime( date );
		calendar.set( Calendar.HOUR_OF_DAY, calendar.getActualMinimum( Calendar.HOUR_OF_DAY ) );
		calendar.set( Calendar.MINUTE, calendar.getActualMinimum( Calendar.MINUTE ) );
		calendar.set( Calendar.SECOND, calendar.getActualMinimum( Calendar.SECOND ) );
		calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMinimum( Calendar.DAY_OF_MONTH ));		
		return calendar;
	}
	
	/**
	 * Retorna a data final do mes, ou seja
	 * se hoje for 04/12/2014 23:41 ira retornar 01/12/2014 00:00
	 */
	public static Calendar finalMonth( Date date ){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime( date );
		calendar.set( Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY) );
		calendar.set( Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE) );
		calendar.set( Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND) );
		calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar;
	}

	public static String getFormatDateTime(Date date){

		return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);

	}

	public static String getFormatDate(Date date){

		return new SimpleDateFormat(DATE_FORMAT).format(date);

	}

	public static String getCurrentDateUUID(){

		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	}

}