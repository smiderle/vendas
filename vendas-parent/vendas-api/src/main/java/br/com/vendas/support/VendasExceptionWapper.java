package br.com.vendas.support;

import br.com.vendas.exception.VendasException;



public class VendasExceptionWapper{
	private String code;
	private String message;
	
	public VendasExceptionWapper(final Exception e) {
		this.message = e.getMessage();
	}

	public VendasExceptionWapper(final VendasException ex){
		this.code = ex.getCode();
		this.message = ex.getMessage();
	}

	public VendasExceptionWapper(final String code, final String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public void setCode(final String code) {
		this.code = code;
	}

	public void setMessage(final String message) {
		this.message = message;
	}


}
