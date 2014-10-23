package br.com.vendas.exception;

public class ParseJsonException extends VendasException {
	
	private static final long serialVersionUID = 1L;

	public ParseJsonException() {
		super();
	}
	public ParseJsonException(final String message) {
		super(message);
	}
	public ParseJsonException(final String code, final String message) {
		super(code, message);
	}
	public ParseJsonException(final String code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	public ParseJsonException(final String code, final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}
	public ParseJsonException(final String message, final Throwable cause) {
		super(message, cause);
	}
	public ParseJsonException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public ParseJsonException(final Throwable cause) {
		super(cause);
	}

}
