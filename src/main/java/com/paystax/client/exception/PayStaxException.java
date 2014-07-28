package com.paystax.client.exception;

import java.io.IOException;

/**
 * @author Erik R. Jensen
 */
public class PayStaxException extends IOException {

	private static final long serialVersionUID = 2779235624601303153L;

	public PayStaxException() {}

	public PayStaxException(String message) {
		super(message);
	}

	public PayStaxException(String message, Throwable t) {
		super(message, t);
	}

	public PayStaxException(Throwable t) {
		super(t);
	}


}
