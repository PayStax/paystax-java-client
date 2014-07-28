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

		PayStaxUser user2 = client.newUser()
				.setUsername("unittest2")
				.setPassword("Password1!")
				.setEmailAddress("unittest2@paystax.com")
				.setFirstName("Unit")
				.setLastName("Test")
				.save();


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

		PayStaxCustomer customer2 = client.newCustomer()
				.setIdentifier1("customer2")
				.setIdentifier2("customer2")
				.setFirstName("Unit")
				.setLastName("Test")
				.setFullName("Unit Test")
				.setEmailAddress("unittest2@paystax.com")
				.save();

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

	}

}
