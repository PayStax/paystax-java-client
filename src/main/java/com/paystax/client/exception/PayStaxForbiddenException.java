package com.paystax.client.exception;

/**
 * @author Erik R. Jensen
 */
public class PayStaxForbiddenException extends PayStaxException {

	private static final long serialVersionUID = -5723562295794965743L;

	public PayStaxForbiddenException() {}

	public PayStaxForbiddenException(String message) {
		super(message);
	}
}
