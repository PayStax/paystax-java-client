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

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Erik R. Jensen
 */
public class PayStaxClientTest {

	@Test
	public void test() throws IOException {
		PayStaxClient client = new PayStaxClient("http://test.paystax.cc:8080", "test", "Password1!");
		assertNotNull(client.getAccountId());
		assertThat(client.getCompanyName(), equalTo("Test, LLC"));

		PayStaxAccount account = client.getAccount();
		assertNotNull(account.getId());
		assertThat(account.getSite(), equalTo("test"));
		assertThat(account.getCompanyName(), equalTo("Test, LLC"));

		PayStaxUser user1 = client.newUser()
				.setUsername("unittest1")
				.setPassword("Password1!")
				.setEmailAddress("unittest1@paystax.com")
				.setFirstName("Unit")
				.setLastName("Test")
				.save();
		assertNotNull(user1.getId());

		PayStaxUser user2 = client.newUser()
				.setUsername("unittest2")
				.setPassword("Password1!")
				.setEmailAddress("unittest2@paystax.com")
				.setFirstName("Unit")
				.setLastName("Test")
				.save();
		assertNotNull(user2.getId());


		PayStaxPage<PayStaxUser> users = client.search(new PayStaxUserSearch()
				.setSize(1)
				.setUsernameStartsWith("unittest")
				.addSort(new PayStaxSortBy("username", PayStaxSortDirection.ASC)));

		assertThat(users.getContent().get(0).getUsername(), equalTo("unittest1"));
		assertThat(users.getPage().getNumber(), equalTo(0));

		users = users.next();
		assertThat(users.getContent().get(0).getUsername(), equalTo("unittest2"));
		assertThat(users.getPage().getNumber(), equalTo(1));

		users = users.prev();
		assertThat(users.getContent().get(0).getUsername(), equalTo("unittest1"));
		assertThat(users.getPage().getNumber(), equalTo(0));

		PayStaxCustomer customer1 = client.newCustomer()
				.setIdentifier1("customer1")
				.setIdentifier2("customer1")
				.setFirstName("Unit")
				.setLastName("Test")
				.setFullName("Unit Test")
				.setEmailAddress("unittest1@paystax.com")
				.save();
		assertNotNull(customer1.getId());

		PayStaxCustomer customer2 = client.newCustomer()
				.setIdentifier1("customer2")
				.setIdentifier2("customer2")
				.setFirstName("Unit")
				.setLastName("Test")
				.setFullName("Unit Test")
				.setEmailAddress("unittest2@paystax.com")
				.save();
		assertNotNull(customer2.getId());

		PayStaxPage<PayStaxCustomer> customers = client.search(new PayStaxCustomerSearch()
				.setSize(1)
				.setFirstNameStartsWith("Unit")
				.addSort(new PayStaxSortBy("identifier1", PayStaxSortDirection.ASC)));

		assertThat(customers.getContent().get(0).getIdentifier1(), equalTo("customer1"));
		assertThat(users.getPage().getCount(), equalTo(2L));
		assertThat(users.getPage().getNumber(), equalTo(0));

		customers = customers.next();
		assertThat(customers.getContent().get(0).getIdentifier1(), equalTo("customer2"));
		assertThat(users.getPage().getNumber(), equalTo(0));

		customers = customers.prev();
		assertThat(customers.getContent().get(0).getIdentifier1(), equalTo("customer1"));
		assertThat(users.getPage().getCount(), equalTo(2L));
		assertThat(users.getPage().getNumber(), equalTo(0));

		PayStaxCard card1 = client.newCard()
				.setCardType(CardType.VISA)
				.setAccountNumber("4111111111111111")
				.setCardholderName("John Doe")
				.setCustomerId(customer1.getId())
				.setExpirationDate("01/2020")
				.setPriority(1)
				.setAddress(new PayStaxAddress()
						.setAddressLine1("123 Test St")
						.setAddressLine2("Suite 101")
						.setCity("Pleasant Grove")
						.setState("UT")
						.setPostalCode("84062")
						.setCountry("USA"))
				.save();
		assertNotNull(card1.getId());
		assertThat(card1.getMaskedAccountNumber(), equalTo("************1111"));

		PayStaxCard card2 = client.newCard()
				.setCardType(CardType.MASTERCARD)
				.setAccountNumber("5555555555554444")
				.setCardholderName("John Doe")
				.setCustomerId(customer1.getId())
				.setExpirationDate("01/2013")
				.setPriority(2)
				.setAddress(new PayStaxAddress()
						.setAddressLine1("123 Test St")
						.setAddressLine2("Suite 101")
						.setCity("Pleasant Grove")
						.setState("UT")
						.setPostalCode("84062")
						.setCountry("USA"))
				.save();
		assertNotNull(card2.getId());
		assertThat(card2.getMaskedAccountNumber(), equalTo("************4444"));

		PayStaxCard card = client.getCard(customer1.getId(), card1.getId());
		assertThat(card, equalTo(card1));

		PayStaxPage<PayStaxCard> cards = client.search(new PayStaxCardSearch()
				.setSize(1)
				.addSort(new PayStaxSortBy("expirationDate", PayStaxSortDirection.DESC)));

		assertThat(cards.getPage().getNumber(), equalTo(0));
		assertThat(cards.getPage().getPages(), equalTo(2));
		assertThat(cards.getPage().getCount(), equalTo(2L));
		assertThat(cards.getContent().get(0), equalTo(card1));

		cards = cards.next();

		assertThat(cards.getPage().getNumber(), equalTo(1));
		assertThat(cards.getPage().getPages(), equalTo(2));
		assertThat(cards.getPage().getCount(), equalTo(2L));
		assertThat(cards.getContent().get(0), equalTo(card2));

		cards = cards.prev();
		assertThat(cards.getContent().get(0), equalTo(card1));

	}

}
