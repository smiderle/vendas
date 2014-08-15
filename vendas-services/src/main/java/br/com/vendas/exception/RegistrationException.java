package br.com.vendas.exception;

public class RegistrationException extends ApplicationException{
	
	private static final long serialVersionUID = -757187531396947842L;
	
	public RegistrationException() {
		super();
	}
	public RegistrationException(final String message) {
		super(message);
	}
	public RegistrationException(final String code, final String message) {
		super(code, message);
	}
	public RegistrationException(final String code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	public RegistrationException(final String code, final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}
	public RegistrationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	public RegistrationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public RegistrationException(final Throwable cause) {
		super(cause);
	}

}
