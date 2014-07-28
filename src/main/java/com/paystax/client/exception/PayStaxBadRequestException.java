package com.paystax.client.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik R. Jensen
 */
public class PayStaxBadRequestException extends PayStaxException {

	private static final long serialVersionUID = -7372223617979515476L;

	protected List<Error> errors = new ArrayList<Error>();

	public PayStaxBadRequestException() {}

	public PayStaxBadRequestException(String message) {
		super(message);
	}

	public PayStaxBadRequestException(String message, List<Error> errors) {
		super(message);
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
