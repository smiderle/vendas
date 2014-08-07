package br.com.vendas.support;

/**
 * Enum com os c�digos http
 * http://en.wikipedia.org/wiki/List_of_HTTP_status_codes 
 * @author LADAIR
 *
 */
public enum HTTPStatusCode {	
	
	 SUCESS_200("200", "OK"),
	 SERVER_ERROR_500("500","Internal Server Error"),
	 SERVER_ERROR_503("503","Service Unavailable");
	
	private String code;
	private String description;
	
	private HTTPStatusCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {	
		return getCode();
	}
}
