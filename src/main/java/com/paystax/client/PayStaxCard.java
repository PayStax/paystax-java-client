package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Holds card details.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxCard implements Serializable {

	private static final long serialVersionUID = 6659484328587723253L;

	@JsonIgnore
	protected PayStaxClient client;

	protected UUID id;
	protected UUID customerId;
	protected int priority;
	protected CardType cardType;
	protected String accountNumber;
	protected String maskedAccountNumber;
	protected String expirationDate;
	protected String cardholderName;
	protected PayStaxAddress address;
	protected Map<String, String> links = new HashMap<String, String>();

	public PayStaxCard() {}

	public PayStaxCard(PayStaxClient client) {
		this.client = client;
	}

	public PayStaxClient getClient() {
		return client;
	}

	public void setClient(PayStaxClient client) {
		this.client = client;
	}

	public UUID getId() {
		return id;
	}

	public PayStaxCard setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public PayStaxCard setCustomerId(UUID customerId) {
		this.customerId = customerId;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public PayStaxCard setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public CardType getCardType() {
		return cardType;
	}

	public PayStaxCard setCardType(CardType cardType) {
		this.cardType = cardType;
		return this;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public PayStaxCard setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		return this;
	}

	@JsonIgnore
	public String getMaskedAccountNumber() {
		return maskedAccountNumber;
	}

	@JsonProperty
	public PayStaxCard setMaskedAccountNumber(String maskedAccountNumber) {
		this.maskedAccountNumber = maskedAccountNumber;
		return this;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public PayStaxCard setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
		return this;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public PayStaxCard setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
		return this;
	}

	public PayStaxAddress getAddress() {
		return address;
	}

	public PayStaxCard setAddress(PayStaxAddress address) {
		this.address = address;
		return this;
	}

	@JsonIgnore
	public Map<String, String> getLinks() {
		return links;
	}

	@JsonProperty
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	public PayStaxCard refresh() throws IOException {
		return client.getHttpClient().get(links.get("self"), this);
	}

	public PayStaxCard save() throws IOException {
		if (id == null) { // create
			client.getHttpClient().create(
					new LinkBuilder(client.getLinks().get("cards")).toString(),
					this);
		} else { // update
			client.getHttpClient().update(links.get("self"), this);
		}
		return this;
	}

	public void delete() throws IOException {
		client.getHttpClient().delete(links.get("self"));
	}

	@Override
	public String toString() {
		return "PayStaxCard{" +
				"id=" + id +
				", customerId=" + customerId +
				", priority=" + priority +
				", accountNumber='" + accountNumber + '\'' +
				", maskedAccountNumber='" + maskedAccountNumber + '\'' +
				", expirationDate='" + expirationDate + '\'' +
				", cardholderName='" + cardholderName + '\'' +
				", address=" + address +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PayStaxCard that = (PayStaxCard) o;
		return id.equals(that.id);
	}


	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
