package br.com.vendas.services.support;

import java.util.List;

public class ServiceResponseFactory {
	public static <DOMAIN> ServiceResponse<DOMAIN> create(DOMAIN obj){
		ServiceResponse<DOMAIN> toReturn = new ServiceResponse<DOMAIN>();

		toReturn.setValue(obj);
		
		if(obj instanceof List<?>){
			toReturn.setRowCount(obj == null ? 0L : ((List<?>)obj).size());
		} else {
			toReturn.setRowCount(obj == null ? 0L : 1L);
		}
		return toReturn;
	}
}
