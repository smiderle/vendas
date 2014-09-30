package br.com.vendas.core.util;

/**
 * Classe utilitaria para strings
 * @author Ladair C. Smiderle Junior 08/09/2014 - 22:23:24
 *
 */
public class StringUtils {
	
	/**
	 * Verifica se a string passada por parametro é um Integer
	 * @return é um iteiro ?
	 */
	public static boolean isInteger(String str){
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
		
	}

}
