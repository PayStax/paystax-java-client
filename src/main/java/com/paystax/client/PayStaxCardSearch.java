package com.paystax.client;

import java.util.Date;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
public class PayStaxCardSearch extends PayStaxSearch<PayStaxCardSearch> {

	private static final long serialVersionUID = 6866724642159771144L;

	UUID customerId;

	String accountNumber;

	String lastFourDigits;

	CardType cardType;

	Date expirationDateBefore;

	Date expirationDateAfter;

	Boolean expired;

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public Date getExpirationDateBefore() {
		return expirationDateBefore;
	}

	public void setExpirationDateBefore(Date expirationDateBefore) {
		this.expirationDateBefore = expirationDateBefore;
	}

	public Date getExpirationDateAfter() {
		return expirationDateAfter;
	}

	public void setExpirationDateAfter(Date expirationDateAfter) {
		this.expirationDateAfter = expirationDateAfter;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
}
