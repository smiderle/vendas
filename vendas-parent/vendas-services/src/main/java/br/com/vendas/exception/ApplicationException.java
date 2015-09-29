package br.com.vendas.exception;

public class ApplicationException extends VendasException {
	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}
	public ApplicationException(final String message) {
		super(message);
	}
	public ApplicationException(final String code, final String message) {
		super(code, message);
	}
	public ApplicationException(final String code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	public ApplicationException(final String code, final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	public ApplicationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public ApplicationException(final Throwable cause) {
		super(cause);
	}



}
