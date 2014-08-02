package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Holds PayStax account details.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxAccount implements LinkedResource, Serializable {

	private static final long serialVersionUID = 5889487704196401219L;

	@JsonIgnore
	protected PayStaxClient client;
	protected UUID id;
	protected String site;
	protected String companyName;
	protected Map<String, String> links = new HashMap<String, String>();

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

	@JsonIgnore
	public Map<String, String> getLinks() {
		return links;
	}

	@JsonProperty
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "PayStaxAccount{" +
				"id=" + id +
				", site='" + site + '\'' +
				", companyName='" + companyName + '\'' +
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
		PayStaxAccount that = (PayStaxAccount) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
