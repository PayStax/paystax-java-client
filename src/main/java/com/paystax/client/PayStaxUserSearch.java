/**
 *    Copyright 2014 PayStax, LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
