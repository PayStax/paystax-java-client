package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paystax.client.http.RestClient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
public class PayStaxNewAccount implements Serializable {

	private static final long serialVersionUID = -4881090996335877658L;

	@JsonIgnore
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient httpClient;
	protected String site;
	protected String companyName;
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;

	protected PayStaxNewAccount(RestClient httpClient) {
		this.httpClient = httpClient;
	}

	public PayStaxAccount save() throws IOException {
		return httpClient.create("/accounts", this, PayStaxAccount.class);
	}
}
