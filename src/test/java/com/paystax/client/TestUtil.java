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

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Helper methods to reduce code bloat in unit tests.
 *
 * @author Erik R. Jensen
 */
public class TestUtil {

	public static final String URL = "https://test.paystax.cc:8080";
	public static final String USERNAME = "test";
	public static final String PASSWORD = "Password1!";

	public static PayStaxClient client() {
		return new PayStaxClient(URL, USERNAME, PASSWORD);
	}

//	public static PayStaxUser newUser(PayStaxClient client) throws IOException {
//		PayStaxUser user = client.newUser()
//				.setUsername("unittest")
//				.setPassword("Password1!")
//				.setFirstName("Unit")
//				.setLastName("Test")
//				.setEmailAddress("unittest@paystax.com")
//				.addPermission("VIEW_FULL_ACCOUNT_NUMBER")
//				.addRole("ROLE_ACCOUNT_MANAGER")
//				.save();
//		assertNotNull(user.getCreatedDate());
//		assertNotNull(user.getLastModifiedDate());
//		return user;
//	}
//
//	public static PayStaxCustomer newPayer(PayStaxClient client) throws IOException {
//		PayStaxCustomer customer = client.newPayer()
//				.setMerchantReference1("unittestid1")
//				.setMerchantReference2("unittestid2")
//				.setFirstName("Unit")
//				.setLastName("Test")
//				.setFullName("Unit Test")
//				.setEmailAddress("unittest@paystax.com")
//				.save();
//		assertNotNull(customer.getCreatedDate());
//		assertNotNull(customer.getLastModifiedDate());
//		return customer;
//	}
//
//	public static PayStaxCard newVisaCard(PayStaxClient client, UUID customerId) throws IOException {
//		PayStaxCard card = client.newCard()
//				.setCustomerId(customerId)
//				.setCardType(CardType.VISA)
//				.setAccountNumber("4111111111111111")
//				.setExpirationDate("08/14")
//				.setCardholderName("John Doe")
//				.setPriority(1)
//				.setAddress(new PayStaxAddress().setAddressLine1("123 Test St")
//						.setAddressLine2("Suite 101")
//						.setCity("Pleasant Grove")
//						.setState("UT")
//						.setPostalCode("84062")
//						.setCountry("USA"))
//				.save();
//		assertNotNull(card.getCreatedDate());
//		assertNotNull(card.getLastModifiedDate());
//		return card;
//	}
//
//	public static PayStaxCard newMasterCard(PayStaxClient client, UUID customerId) throws IOException {
//		PayStaxCard card = client.newCard()
//				.setCustomerId(customerId)
//				.setCardType(CardType.MASTERCARD)
//				.setAccountNumber("5555555555554444")
//				.setExpirationDate("09/14")
//				.setCardholderName("Jane Doe")
//				.setPriority(2)
//				.setAddress(new PayStaxAddress().setAddressLine1("123 Test St")
//						.setAddressLine2("Suite 101")
//						.setCity("Pleasant Grove")
//						.setState("UT")
//						.setPostalCode("84062")
//						.setCountry("USA"))
//				.save();
//		assertNotNull(card.getCreatedDate());
//		assertNotNull(card.getLastModifiedDate());
//		return card;
//	}
}
