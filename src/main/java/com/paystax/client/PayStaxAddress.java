package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Holds address information.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxAddress implements Serializable {

	private static final long serialVersionUID = 5924873675404788818L;
	protected String addressLine1;
	protected String addressLine2;
	protected String addressLine3;
	protected String city;
	protected String state;
	protected String postalCode;
	protected String country;

	public String getAddressLine1() {
		return addressLine1;
	}

	public PayStaxAddress setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		return this;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public PayStaxAddress setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
		return this;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public PayStaxAddress setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
		return this;
	}

	public String getCity() {
		return city;
	}

	public PayStaxAddress setCity(String city) {
		this.city = city;
		return this;
	}

	public String getState() {
		return state;
	}

	public PayStaxAddress setState(String state) {
		this.state = state;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public PayStaxAddress setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public PayStaxAddress setCountry(String country) {
		this.country = country;
		return this;
	}

	@Override
	public String toString() {
		return "PayStaxAddress{" +
				"addressLine1='" + addressLine1 + '\'' +
				", addressLine2='" + addressLine2 + '\'' +
				", addressLine3='" + addressLine3 + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", postalCode='" + postalCode + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}
