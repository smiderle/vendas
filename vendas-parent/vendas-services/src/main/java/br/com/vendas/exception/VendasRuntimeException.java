package br.com.vendas.exception;

public class VendasRuntimeException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8978049999538739309L;

	public VendasRuntimeException(String message) {
		super(message);
	}
	
	public VendasRuntimeException(Throwable throwable) {
		super(throwable);
	}
	
	public VendasRuntimeException(Throwable throwable,String message) {
		super(message, throwable);
	}
	
	public VendasRuntimeException() {
		super();
	}
	
	

}
