package com.paystax.client.exception;

/**
 * @author Erik R. Jensen
 */
public class PayStaxAuthenticationFailedException extends PayStaxException {

	private static final long serialVersionUID = -3971843776362868402L;

	public PayStaxAuthenticationFailedException() {}

	public PayStaxAuthenticationFailedException(String message) {
		super(message);
	}
}
