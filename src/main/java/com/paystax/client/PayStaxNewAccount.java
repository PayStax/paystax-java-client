/**
 *    Copyright 2015 PayStax, LLC
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
