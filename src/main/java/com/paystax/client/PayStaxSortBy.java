package com.paystax.client;

/**
 * @author Erik R. Jensen
 */
public class PayStaxSortBy {

	private String field;
	private PayStaxSortDirection direction;

	public PayStaxSortBy() {}

	public PayStaxSortBy(String field, PayStaxSortDirection direction) {
		this.field = field;
		this.direction = direction;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(field);
		if (direction != null) {
			sb.append(",").append(direction.toString().toLowerCase());
		}
		return sb.toString();
	}
}
