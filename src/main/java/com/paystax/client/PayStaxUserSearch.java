package com.paystax.client;

import java.util.UUID;

/**
 * Holds search criteria used to find users.
 *
 * @author Erik R. Jensen
 */
public class PayStaxUserSearch extends PayStaxSearch<PayStaxUserSearch> {

	private static final long serialVersionUID = -8177256304814901137L;
	protected UUID id;
	protected String username;
	protected String usernameStartsWith;
	protected String firstNameStartsWith;
	protected String firstNameContains;
	protected String lastNameContains;
	protected String lastNameStartsWith;

	public UUID getId() {
		return id;
	}

	public PayStaxUserSearch setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public PayStaxUserSearch setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getUsernameStartsWith() {
		return usernameStartsWith;
	}

	public PayStaxUserSearch setUsernameStartsWith(String usernameStartsWith) {
		this.usernameStartsWith = usernameStartsWith;
		return this;
	}

	public String getFirstNameStartsWith() {
		return firstNameStartsWith;
	}

	public PayStaxUserSearch setFirstNameStartsWith(String firstNameStartsWith) {
		this.firstNameStartsWith = firstNameStartsWith;
		return this;
	}

	public String getFirstNameContains() {
		return firstNameContains;
	}

	public PayStaxUserSearch setFirstNameContains(String firstNameContains) {
		this.firstNameContains = firstNameContains;
		return this;
	}

	public String getLastNameContains() {
		return lastNameContains;
	}

	public PayStaxUserSearch setLastNameContains(String lastNameContains) {
		this.lastNameContains = lastNameContains;
		return this;
	}

	public String getLastNameStartsWith() {
		return lastNameStartsWith;
	}

	public PayStaxUserSearch setLastNameStartsWith(String lastNameStartsWith) {
		this.lastNameStartsWith = lastNameStartsWith;
		return this;
	}

}
