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
 * Holds customer information.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxCustomer implements Serializable {

	private static final long serialVersionUID = 6175764972549844777L;

	@JsonIgnore
	protected PayStaxClient client;

	protected UUID id;
	protected String identifier1;
	protected String identifier2;
	protected String firstName;
	protected String lastName;
	protected String fullName;
	protected String emailAddress;
	protected Map<String, String> links = new HashMap<String, String>();

	public PayStaxCustomer() {}

	public PayStaxCustomer(PayStaxClient client) {
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

	public PayStaxCustomer setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getIdentifier1() {
		return identifier1;
	}

	public PayStaxCustomer setIdentifier1(String identifier1) {
		this.identifier1 = identifier1;
		return this;
	}

	public String getIdentifier2() {
		return identifier2;
	}

	public PayStaxCustomer setIdentifier2(String identifier2) {
		this.identifier2 = identifier2;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public PayStaxCustomer setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public PayStaxCustomer setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public PayStaxCustomer setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public PayStaxCustomer setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public PayStaxCustomer refresh() throws IOException {
		client.getHttpClient().get(links.get("self"), this);
		return this;
	}

	public PayStaxCustomer save() throws IOException {
		if (id == null) { // create
			client.getHttpClient().create(
					new LinkBuilder(client.getLinks().get("customers")).toString(),
					this);
		} else { // update
			client.getHttpClient().update(
					new LinkBuilder(links.get("self")).toString(),
					this);
		}
		return this;
	}

	public void delete() throws IOException {
		client.getHttpClient().delete(links.get("self"));
	}

	@Override
	public String toString() {
		return "PayStaxCustomer{" +
				"id=" + id +
				", identifier1='" + identifier1 + '\'' +
				", identifier2='" + identifier2 + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", fullName='" + fullName + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				'}';
	}
}
