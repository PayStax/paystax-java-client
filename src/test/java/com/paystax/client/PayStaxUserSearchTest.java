package com.paystax.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Erik R. Jensen
 */
public class PayStaxUserSearchTest {

	private static final Logger log = LoggerFactory.getLogger(PayStaxUserSearchTest.class);

	@Test
	public void testToQueryString() throws UnsupportedEncodingException {
//		PayStaxUserSearch search = new PayStaxUserSearch();
//		search.setUsername("test");
//		search.setFirstNameContains("test");
//		search.setFirstNameStartsWith("test");
//		search.setLastNameContains("test");
//		search.setLastNameStartsWith("test");
//		search.setUsernameStartsWith("test");
//		search.addSort(new PayStaxSortBy("username", PayStaxSortDirection.ASC));
//		search.addSort(new PayStaxSortBy("firstName", PayStaxSortDirection.ASC));
//		String query = search.toQueryString();
//		log.info("Query String: " + query);
//		// TODO I'm not sure if we can count on the order always remaining the same, probably should refactor the unit test
//		assertThat(search.toQueryString(), equalTo("?username=test&usernameStartsWith=test" +
//				"&firstNameStartsWith=test&firstNameContains=test&lastNameContains=test" +
//				"&lastNameStartsWith=test&page=0&size=25&sort=username%2Casc&sort=firstName%2Casc"));
	}
}
