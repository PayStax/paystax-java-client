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
 * @author Erik R. Jensen
 */
public class PayStaxCustomerSearch extends PayStaxSearch<PayStaxCustomerSearch> {

	private static final long serialVersionUID = 1496118278694634588L;

	private UUID id;
	private String identifier1;
	private String identifier1Contains;
	private String identifier1StartsWith;
	private String identifier2;
	private String identifier2Contains;
	private String identifier2StartsWith;
	private String firstNameContains;
	private String firstNameStartsWith;
	private String lastNameContains;
	private String lastNameStartsWith;
	private String fullNameContains;
	private String fullNameStartsWith;
	private String emailAddress;
	private String emailAddressContains;
	private String emailAddressStartsWith;

	public UUID getId() {
		return id;
	}

	public PayStaxCustomerSearch setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getIdentifier1() {
		return identifier1;
	}

	public PayStaxCustomerSearch setIdentifier1(String identifier1) {
		this.identifier1 = identifier1;
		return this;
	}

	public String getIdentifier1Contains() {
		return identifier1Contains;
	}

	public PayStaxCustomerSearch setIdentifier1Contains(String identifier1Contains) {
		this.identifier1Contains = identifier1Contains;
		return this;
	}

	public String getIdentifier1StartsWith() {
		return identifier1StartsWith;
	}

	public PayStaxCustomerSearch setIdentifier1StartsWith(String identifier1StartsWith) {
		this.identifier1StartsWith = identifier1StartsWith;
		return this;
	}

	public String getIdentifier2() {
		return identifier2;
	}

	public PayStaxCustomerSearch setIdentifier2(String identifier2) {
		this.identifier2 = identifier2;
		return this;
	}

	public String getIdentifier2Contains() {
		return identifier2Contains;
	}

	public PayStaxCustomerSearch setIdentifier2Contains(String identifier2Contains) {
		this.identifier2Contains = identifier2Contains;
		return this;
	}

	public String getIdentifier2StartsWith() {
		return identifier2StartsWith;
	}

	public PayStaxCustomerSearch setIdentifier2StartsWith(String identifier2StartsWith) {
		this.identifier2StartsWith = identifier2StartsWith;
		return this;
	}

	public String getFirstNameContains() {
		return firstNameContains;
	}

	public PayStaxCustomerSearch setFirstNameContains(String firstNameContains) {
		this.firstNameContains = firstNameContains;
		return this;
	}

	public String getFirstNameStartsWith() {
		return firstNameStartsWith;
	}

	public PayStaxCustomerSearch setFirstNameStartsWith(String firstNameStartsWith) {
		this.firstNameStartsWith = firstNameStartsWith;
		return this;
	}

	public String getLastNameContains() {
		return lastNameContains;
	}

	public PayStaxCustomerSearch setLastNameContains(String lastNameContains) {
		this.lastNameContains = lastNameContains;
		return this;
	}

	public String getLastNameStartsWith() {
		return lastNameStartsWith;
	}

	public PayStaxCustomerSearch setLastNameStartsWith(String lastNameStartsWith) {
		this.lastNameStartsWith = lastNameStartsWith;
		return this;
	}

	public String getFullNameContains() {
		return fullNameContains;
	}

	public PayStaxCustomerSearch setFullNameContains(String fullNameContains) {
		this.fullNameContains = fullNameContains;
		return this;
	}

	public String getFullNameStartsWith() {
		return fullNameStartsWith;
	}

	public PayStaxCustomerSearch setFullNameStartsWith(String fullNameStartsWith) {
		this.fullNameStartsWith = fullNameStartsWith;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public PayStaxCustomerSearch setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public String getEmailAddressContains() {
		return emailAddressContains;
	}

	public PayStaxCustomerSearch setEmailAddressContains(String emailAddressContains) {
		this.emailAddressContains = emailAddressContains;
		return this;
	}

	public String getEmailAddressStartsWith() {
		return emailAddressStartsWith;
	}

	public PayStaxCustomerSearch setEmailAddressStartsWith(String emailAddressStartsWith) {
		this.emailAddressStartsWith = emailAddressStartsWith;
		return this;
	}
}
