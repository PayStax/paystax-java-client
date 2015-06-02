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

import com.paystax.client.http.URLConnectionRestClient;
import com.paystax.client.http.RestClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Main class used to access PayStax data.
 *
 * @author Erik R. Jensen
 */
public class PayStaxClient implements Serializable {

	private static final long serialVersionUID = 6730877433238660152L;

	protected RestClient restClient;

	public PayStaxClient(String url, String username, String password) {
		this.restClient = new URLConnectionRestClient(url, username, password);
	}

	public PayStaxClient(RestClient httpClient) {
		this.restClient = httpClient;
	}

	public RestClient getHttpClient() {
		return restClient;
	}

	public PayStaxAccount getAccount() throws IOException {
		return restClient.get("/account", PayStaxAccount.class);
	}

	public PayStaxAccount getAccount(UUID accountId) throws IOException {
		return restClient.get("/accounts/" + accountId, PayStaxAccount.class);
	}

	public PayStaxAccountSearch accountSearch() {
		return new PayStaxAccountSearch(restClient);
	}

	public PayStaxNewAccount newAccount() {
		return new PayStaxNewAccount(restClient);
	}

	public void deleteAccount(UUID accountId) throws IOException {
		restClient.delete("/accounts/" + accountId);
	}

	public PayStaxUserAccount getUserAccount() throws IOException {
		return restClient.get("/user", PayStaxUserAccount.class);
	}

	public PayStaxUserAccount getUserAccount(UUID userAccountId) throws IOException {
		return restClient.get("/users/" + userAccountId, PayStaxUserAccount.class);
	}

	public PayStaxUserAccountSearch userAccountSearch() {
		return new PayStaxUserAccountSearch(restClient);
	}

	public PayStaxUserAccount newUserAccount() {
		return new PayStaxUserAccount(restClient);
	}

	public void deleteUserAccount(UUID userAccountId) throws IOException {
		restClient.delete("/users/" + userAccountId);
	}

}