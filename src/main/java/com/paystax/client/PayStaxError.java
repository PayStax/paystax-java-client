package com.paystax.client;

/**
 * @author Erik R. Jensen
 */
public class PayStaxError {

	private String message;
	private String field;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
