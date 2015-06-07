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

import com.paystax.client.gateway.PayStaxGatewayType;
import com.paystax.client.gateway.PayStaxPayvisionGateway;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.paystax.client.TestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Functional tests for PayStaxClient.
 *
 * @author Erik R. Jensen
 */
public class PayStaxClientTest {

	private PayStaxClient client;

	@Before
	public void testConstructor() {
		client = new PayStaxClient(URL, USERNAME, PASSWORD);
	}

//	@Test
//	public void testNewGateway() throws IOException {
//		PayStaxPayvisionGateway payvisionGateway = client.newGateway(PayStaxGatewayType.PAYVISION);
//	}



//	@Before
//	public void before() throws IOException {
//		log.debug("Initializing before executing test");
//		client = new PayStaxClient(URL, USERNAME, PASSWORD);
//		client.reset();
//	}
//
//	@Test
//	public void testPayStaxClientConstructor1() {
//		client = new PayStaxClient(URL, USERNAME, PASSWORD);
//		assertNotNull(client.getHttpClient());
//	}
//
//	@Test
//	public void testPayStaxClientConstructor2() {
//		HttpClient httpClient = new HttpURLConnectionClient(USERNAME, PASSWORD);
//		client = new PayStaxClient(URL, httpClient);
//		assertEquals(httpClient, client.getHttpClient());
//	}
//
//	@Test
//	public void getAccountId() {
//		assertNotNull(client.getAccountId());
//	}
//
//	@Test
//	public void testGetCompanyName() {
//		assertNotNull(client.getCompanyName());
//	}
//
//	@Test
//	public void testGetLinks() {
//		Map<String, String> links = client.getLinks();
//		assertTrue(links.containsKey("account"));
//	}
//
//	@Test
//	public void testGetAccount() throws IOException {
//		PayStaxAccount account = client.getAccount();
//		assertEquals(client.getAccountId(), account.getId());
//	}
//
//	@Test
//	public void testNewUser() throws IOException {
//		PayStaxUser user = client.newUser();
//		assertEquals(client, user.getClient());
//		assertTrue(user.getLinks().isEmpty());
//	}
//
//	@Test
//	public void testSearchUser() throws IOException {
//		PayStaxUserSearch search = new PayStaxUserSearch()
//				.setUsername(USERNAME);
//		PayStaxPage<PayStaxUser> users = client.search(search);
//		assertEquals(1L, users.getPage().getCount());
//		assertEquals(0, users.getPage().getNumber());
//		assertEquals(USERNAME, users.getContent().get(0).getUsername());
//	}
//
//	@Test
//	public void testSearchUserPaging() throws IOException {
//		PayStaxUser user = client.newUser()
//				.setUsername("zztest")
//				.setPassword("Password1!")
//				.setFirstName("Unit")
//				.setLastName("Test")
//				.setEmailAddress("test@example.com")
//				.save();
//
//		PayStaxUserSearch search = new PayStaxUserSearch()
//				.setSize(1)
//				.addSort(new PayStaxSortBy("username", PayStaxSortDirection.DESC));
//
//		PayStaxPage<PayStaxUser> users = client.search(search);
//		assertEquals(2L, users.getPage().getCount());
//		assertEquals(2, users.getPage().getPages());
//		assertEquals(0, users.getPage().getNumber());
//		assertEquals("zztest", users.getContent().get(0).getUsername());
//
//		users = users.next();
//		assertEquals(2L, users.getPage().getCount());
//		assertEquals(2, users.getPage().getPages());
//		assertEquals(1, users.getPage().getNumber());
//		assertEquals(USERNAME, users.getContent().get(0).getUsername());
//
//		users = users.prev();
//		assertEquals("zztest", users.getContent().get(0).getUsername());
//	}
//
//	@Test
//	public void testUsers() throws IOException {
//		PayStaxPage<PayStaxUser> users = client.users();
//		assertEquals(1L, users.getPage().getCount());
//		assertEquals(0, users.getPage().getNumber());
//		assertEquals(USERNAME, users.getContent().get(0).getUsername());
//	}
//
//	@Test
//	public void testGetUser() throws IOException {
//		PayStaxUser user = newUser(client);
//		PayStaxUser found = client.getUser(user.getId());
//		assertThat(found, equalTo(user));
//	}
//
//	@Test
//	public void testNewCustomer() throws IOException {
//		PayStaxCustomer customer = client.newCustomer();
//		assertEquals(client, customer.getClient());
//		assertTrue(customer.getLinks().isEmpty());
//	}
//
//	@Test
//	public void testSearchCustomers() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCustomerSearch search = new PayStaxCustomerSearch()
//				.setMerchantReference1("unittestid1");
//		PayStaxPage<PayStaxCustomer> customers = client.search(search);
//		assertEquals(1L, customers.getPage().getCount());
//		assertEquals(0, customers.getPage().getNumber());
//		assertEquals(customer, customers.getContent().get(0));
//	}
//
//	@Test
//	public void testCustomers() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxPage<PayStaxCustomer> customers = client.customers();
//		assertEquals(1L, customers.getPage().getCount());
//		assertEquals(0, customers.getPage().getNumber());
//		assertEquals(customer, customers.getContent().get(0));
//	}
//
//	@Test
//	public void testGetCustomer() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCustomer found = client.getCustomer(customer.getId());
//		assertEquals(customer, found);
//	}
//
//	@Test
//	public void testNewCard() throws IOException {
//		PayStaxCard card = client.newCard();
//		assertEquals(client, card.getClient());
//		assertTrue(card.getLinks().isEmpty());
//	}
//
//	@Test
//	public void testSearchCards() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCard card1 = newVisaCard(client, customer.getId());
//		PayStaxCard card2 = newMasterCard(client, customer.getId());
//		PayStaxCardSearch search = new PayStaxCardSearch()
//				.setCustomerId(customer.getId());
//		PayStaxPage<PayStaxCard> cards = client.search(search);
//		assertEquals(2L, cards.getPage().getCount());
//		assertEquals(card1, cards.getContent().get(0));
//		assertEquals(card2, cards.getContent().get(1));
//	}
//
//	@Test
//	public void testSearchCardsPaging() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCard card1 = newVisaCard(client, customer.getId());
//		PayStaxCard card2 = newMasterCard(client, customer.getId());
//		PayStaxCardSearch search = new PayStaxCardSearch()
//				.setCustomerId(customer.getId())
//				.setSize(1);
//		PayStaxPage<PayStaxCard> cards = client.search(search);
//		assertEquals(2L, cards.getPage().getCount());
//		assertEquals(card1, cards.getContent().get(0));
//		cards = cards.next();
//		assertEquals(card2, cards.getContent().get(0));
//		cards = cards.prev();
//		assertEquals(card1, cards.getContent().get(0));
//	}
//
//	@Test
//	public void testCards() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCard card1 = newVisaCard(client, customer.getId());
//		PayStaxCard card2 = newMasterCard(client, customer.getId());
//		PayStaxPage<PayStaxCard> cards = client.cards();
//		assertEquals(2L, cards.getPage().getCount());
//		assertEquals(card1, cards.getContent().get(0));
//		assertEquals(card2, cards.getContent().get(1));
//	}
//
//	@Test
//	public void testGetCard() throws IOException {
//		PayStaxCustomer customer = newCustomer(client);
//		PayStaxCard card = newVisaCard(client, customer.getId());
//		PayStaxCard found = client.getCard(customer.getId(), card.getId());
//		assertEquals(card, found);
//	}

}
