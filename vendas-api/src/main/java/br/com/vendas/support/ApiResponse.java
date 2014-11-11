package br.com.vendas.support;

import java.io.Serializable;

public class ApiResponse implements Serializable {

	private static final long serialVersionUID = -5584906004219908759L;
	public static String STATUS_SUCCESS = "SUCCESS";
	public static String STATUS_FAILURE = "FAILURE";

	private String status;
	private String code;
	private String message;

	private Object payload;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getPayload() {
		return payload;
	}

	public String getStatus() {
		return status;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setPayload(final Object payload) {
		this.payload = payload;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	
	
}