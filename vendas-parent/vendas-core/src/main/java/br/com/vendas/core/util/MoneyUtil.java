package br.com.vendas.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtil {
	
	public MoneyUtil() {}
	
	/**
	 * Converte um double para BigDecimal e arredonda o valor com 2 casas decimais para cima
	 * @param value
	 * @return
	 */
	public static BigDecimal rounding( Double value ){
		 return new BigDecimal( String.valueOf( value ) ).setScale( 2, RoundingMode.UP ); 
	}
	
	/**
	 * Arredonda o valor com 2 casas decimais para cima
	 * @param value
	 * @return
	 */
	public static BigDecimal rounding( BigDecimal value ){
		 return value.setScale( 2, RoundingMode.UP ); 
	}

}
