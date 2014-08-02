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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Unit tests for LinkBuilder.
 *
 * @author Erik R. Jensen
 */
public class LinkBuilderTest {

	@Test
	public void testQueryParameters() {
		String url = new LinkBuilder("https://test.paystax.cc/customers{?firstName,lastName}")
				.addQueryParameter("firstName", "test1")
				.addQueryParameter("lastName", "test2").toString();
		assertThat(url, equalTo("https://test.paystax.cc/customers?firstName=test1&lastName=test2"));
	}

	@Test
	public void testExpand() {
		String url = new LinkBuilder("https://test.paystax.cc/customers/{customerId}/cards/{cardId}")
				.expand("test1", "test2").toString();
		assertThat(url, equalTo("https://test.paystax.cc/customers/test1/cards/test2"));
	}

	@Test
	public void testAddQueryParameters() {
		PayStaxUserSearch search = new PayStaxUserSearch()
				.setUsername("test")
				.setFirstNameContains("test")
				.setFirstNameStartsWith("test")
				.setLastNameContains("test")
				.setLastNameStartsWith("test")
				.setUsernameStartsWith("test")
				.addSort(new PayStaxSortBy("username", PayStaxSortDirection.ASC))
				.addSort(new PayStaxSortBy("firstName", PayStaxSortDirection.ASC));
		String url = new LinkBuilder("http://master.paystax.cc:8080/customers" +
				"{?id,identifier1,identifier1Contains,identifier1StartsWith,identifier2,identifier2Contains," +
				"identifier2StartsWith,firstNameContains,firstNameStartsWith,lastNameContains," +
				"lastNameStartsWith,fullNameContains,fullNameStartsWith,emailAddress,emailAddressContains," +
				"emailAddressStartsWith}")
				.addQueryParameters(search).toString();
		// TODO I'm not sure if we can count on the order always remaining the same we should probably refactor the unit test
		assertThat(url, equalTo("http://master.paystax.cc:8080/customers?username=test&usernameStartsWith=test" +
				"&firstNameStartsWith=test&firstNameContains=test&lastNameContains=test" +
				"&lastNameStartsWith=test&page=0&size=25&sort=username%2Casc&sort=firstName%2Casc"));
	}
}
