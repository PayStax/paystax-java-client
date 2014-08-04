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

import com.paystax.client.exception.PayStaxForbiddenException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * Provides PayStax API access.
 *
 * @author Erik R. Jensen
 */
public class PayStaxClient implements Serializable, LinkedResource {

	private static final long serialVersionUID = 9181337571458052234L;
	protected HttpClient httpClient;
	protected String url;
	protected UUID accountId;
	protected String companyName;
	protected Map<String, String> links;

	public PayStaxClient(String url, String username, String password) {
		this.url = url;
		this.httpClient = new HttpURLConnectionClient(username, password);
	}

	public PayStaxClient(String url, HttpClient httpClient) {
		this.url = url;
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	protected void init() {
		if (accountId == null) {
			try {
				httpClient.get(url, this);
			} catch (IOException x) {
				throw new IllegalStateException("Failed to initialized PayStaxClient: " + x.getMessage(), x);
			}
		}
	}

	public UUID getAccountId() {
		init();
		return accountId;
	}

	public String getCompanyName() {
		init();
		return companyName;
	}

	public Map<String, String> getLinks() {
		init();
		return links;
	}

	public PayStaxAccount getAccount() throws IOException {
		init();
		return httpClient.get(links.get("account"), new PayStaxAccount(this));
	}

	/**
	 * This method will issue a reset to PayStax which will delete all data except for the account itself and
	 * the authenticated user which issues the reset call. This feature is disabled in the PayStax production
	 * environment, but is enabled in the test environment to help facilitate integration testing.
	 *
	 * @throws IOException if an error occurs
	 */
	public void reset() throws IOException {
		init();
		if (!links.containsKey("reset")) {
			throw new PayStaxForbiddenException();
		}
		httpClient.execute(links.get("reset"));
	}

	public PayStaxUser newUser() {
		init();
		return new PayStaxUser(this);
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxUser> search(PayStaxUserSearch search) throws IOException {
		init();
		PayStaxPage<PayStaxUser> users = httpClient.get(
				new LinkBuilder(links.get("users")).addQueryParameters(search).toString(),
				PayStaxPage.class, PayStaxUser.class);
		users.setClient(this);
		users.setClazz(PayStaxUser.class);
		return users;
	}

	public PayStaxPage<PayStaxUser> users() throws IOException {
		return search(new PayStaxUserSearch());
	}

	public PayStaxUser getUser(UUID userId) throws IOException {
		init();
		PayStaxUser user = httpClient.get(
				new LinkBuilder(links.get("user")).expand(userId).toString(),
				PayStaxUser.class);
		user.setClient(this);
		return user;
	}

	public PayStaxCustomer newCustomer() {
		init();
		return new PayStaxCustomer(this);
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxCustomer> search(PayStaxCustomerSearch search) throws IOException {
		init();
		PayStaxPage<PayStaxCustomer> customers = httpClient.get(
				new LinkBuilder(links.get("customers")).addQueryParameters(search).toString(),
				PayStaxPage.class, PayStaxCustomer.class);
		customers.setClient(this);
		customers.setClazz(PayStaxCustomer.class);
		return customers;
	}

	public PayStaxPage<PayStaxCustomer> customers() throws IOException {
		return search(new PayStaxCustomerSearch());
	}

	public PayStaxCustomer getCustomer(UUID customerId) throws IOException {
		init();
		PayStaxCustomer customer = httpClient.get(
				new LinkBuilder(links.get("customer")).expand(customerId).toString(),
				PayStaxCustomer.class);
		customer.setClient(this);
		return customer;
	}

	public PayStaxCard newCard() {
		init();
		return new PayStaxCard(this);
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxCard> search(PayStaxCardSearch search) throws IOException {
		init();
		PayStaxPage<PayStaxCard> cards = httpClient.get(
				new LinkBuilder(links.get("cards")).addQueryParameters(search).toString(),
				PayStaxPage.class, PayStaxCard.class);
		cards.setClient(this);
		cards.setClazz(PayStaxCard.class);
		return cards;
	}

	public PayStaxPage<PayStaxCard> cards() throws IOException {
		return search(new PayStaxCardSearch());
	}

	public PayStaxCard getCard(UUID customerId, UUID cardId) throws IOException {
		init();
		PayStaxCard card = httpClient.get(
				new LinkBuilder(links.get("card")).expand(customerId, cardId).toString(),
				PayStaxCard.class);
		card.setClient(this);
		return card;
	}

	@Override
	public String toString() {
		return "PayStaxClient{" +
				"url='" + url + '\'' +
				", accountId=" + accountId +
				", companyName='" + companyName + '\'' +
				", links=" + links +
				'}';
	}

}