package com.paystax.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Holds user details.
 *
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxUser implements Serializable, LinkedResource {

	private static final long serialVersionUID = 6619624772261299022L;

	@JsonIgnore
	protected PayStaxClient client;
	protected String id;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	protected String emailAddress;
	protected List<String> roles = new ArrayList<String>();
	protected List<String> permissions = new ArrayList<String>();
	protected Map<String, String> links = new HashMap<String, String>();

	public PayStaxUser() {}

	public PayStaxUser(PayStaxClient client) {
		this.client = client;
	}

	public PayStaxClient getClient() {
		return client;
	}

	public void setClient(PayStaxClient client) {
		this.client = client;
	}

	public String getId() {
		return id;
	}

	public PayStaxUser setId(String id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public PayStaxUser setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public PayStaxUser setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public PayStaxUser setUsername(String username) {
		this.username = username;
		return this;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public PayStaxUser setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public PayStaxUser setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public List<String> getRoles() {
		return roles;
	}

	public PayStaxUser setRoles(List<String> roles) {
		this.roles = roles;
		return this;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public PayStaxUser setPermissions(List<String> permissions) {
		this.permissions = permissions;
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

	public PayStaxUser refresh() throws IOException {
		client.getHttpClient().get(links.get("self"), this);
		return this;
	}

	public PayStaxUser save() throws IOException {
		if (id == null) { // create
			client.getHttpClient().create(
					new LinkBuilder(client.getLinks().get("users")).toString(),
					this);
		} else { // update
			client.getHttpClient().update(
					new LinkBuilder(links.get("self")).toString(),
					this);
		}
		return this;
	}

	public void delete() throws IOException {
		client.getHttpClient().delete(
				new LinkBuilder(links.get("self")).toString());
	}

	@Override
	public String toString() {
		return "PayStaxUser{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", roles=" + roles +
				", permissions=" + permissions +
				", links=" + links +
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
		PayStaxUser that = (PayStaxUser) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
