package com.paystax.client.exception;

/**
 * @author Erik R. Jensen
 */
public class PayStaxNotFoundException extends PayStaxException {

	private static final long serialVersionUID = -5198771349467795833L;

	public PayStaxNotFoundException() {}

	public PayStaxNotFoundException(String message) {
		super(message);
	}

	public PayStaxNotFoundException(String message, Throwable t) {
		super(message, t);
	}

	public PayStaxNotFoundException(Throwable t) {
		super(t);
	}

}
