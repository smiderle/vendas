package br.com.vendas.exception;

public class VendasException extends Exception {

	private static final long serialVersionUID = 1L;
	private String code;

	public VendasException() {
		super();
	}

	public VendasException(final String message) {
		super(message);
	}

	public VendasException(final String code, final String message) {
		super(message);
		this.code = code;
	}

	public VendasException(final String code, final String message, final Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public VendasException(final String code, final String message, final Throwable cause, final boolean enableSuppression,
	    final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = code;
	}

	public VendasException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public VendasException(final String message, final Throwable cause, final boolean enableSuppression,
	    final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VendasException(final Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		if (code != null && !code.isEmpty()) {
			return code + " - " + super.getMessage();
		} else {
			return super.getMessage();
		}
	}

	public String getTextMessage() {
		return super.getMessage();
	}

	public void setCode(final String code) {
		this.code = code;
	}

}
