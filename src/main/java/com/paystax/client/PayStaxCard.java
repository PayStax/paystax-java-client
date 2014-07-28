package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMaskedAccountNumber() {
		return maskedAccountNumber;
	}

	public void setMaskedAccountNumber(String maskedAccountNumber) {
		this.maskedAccountNumber = maskedAccountNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public PayStaxAddress getAddress() {
		return address;
	}

	public void setAddress(PayStaxAddress address) {
		this.address = address;
	}

	public Map<String, String> getLinks() {
		return links;
	}

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
}
