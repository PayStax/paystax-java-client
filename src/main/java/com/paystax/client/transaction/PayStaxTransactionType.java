package com.paystax.client.transaction;

/**
 * @author Erik R. Jensen
 */
public enum PayStaxTransactionType {
	CARD_AUTH,
	CARD_CAPTURE,
	CARD_AUTH_CAPTURE,
	CARD_VOID,
	CARD_REFUND,
	CARD_CREDIT,
	ACH
}
