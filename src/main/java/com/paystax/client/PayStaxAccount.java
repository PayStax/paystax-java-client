package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;

/**
 * Holds PayStax account details.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxAccount implements Serializable {

	private static final long serialVersionUID = 5889487704196401219L;

	@JsonIgnore
	protected PayStaxClient client;
	protected UUID id;
	protected String site;
	protected String companyName;

	public PayStaxAccount() {}

	public PayStaxAccount(PayStaxClient client) {
		this.client = client;
	}

	public void setClient(PayStaxClient client) {
		this.client = client;
	}

	public PayStaxClient getClient() {
		return client;
	}

	public UUID getId() {
		return id;
	}

	public PayStaxAccount setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getSite() {
		return site;
	}

	public PayStaxAccount setSite(String site) {
		this.site = site;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public PayStaxAccount setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	@Override
	public String toString() {
		return "PayStaxAccount{" +
				"id=" + id +
				", site='" + site + '\'' +
				", companyName='" + companyName + '\'' +
				'}';
	}
}
