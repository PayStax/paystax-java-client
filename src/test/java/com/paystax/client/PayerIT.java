package com.paystax.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.slf4j.profiler.Profiler;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Erik R. Jensen
 */
@Slf4j
public class PayerIT {

	private static PayStaxClient client;

	private static Profiler profiler;

	@BeforeClass
	public static void init() throws IOException {
		client = IntegrationTestHelper.getClient();
		profiler = new Profiler("Payer Integration Tests");
	}

	@AfterClass
	public static void cleanup() {
		profiler.stop().print();
	}

	@Test
	public void happyPathTest() throws IOException {
		profiler.start("New Payer");
		PayStaxPayer payer = client.newPayer()
				.setFirstName("James")
				.setLastName("Kirk")
				.setEmailAddress("captain@enterprise.com")
				.setFullName("James T Kirk")
				.setMerchantReference("12345")
				.save();

		profiler.start("Update Payer");
		payer.setFirstName("Jim")
				.save();

		profiler.start("Get Payer");
		payer = client.getPayer(payer.getId());
		profiler.stop();

		assertThat(payer.getFirstName(), equalTo("Jim"));
		assertThat(payer.getLastName(), equalTo("Kirk"));
		assertThat(payer.getEmailAddress(), equalTo("captain@enterprise.com"));
		assertThat(payer.getFullName(), equalTo("James T Kirk"));
		assertThat(payer.getMerchantReference(), equalTo("12345"));

		profiler.start("Search Payer");
		PayStaxPage<PayStaxPayer> page = client.payerSearch()
				.addPayerId(payer.getId())
				.search();
		profiler.stop();
		assertThat(page.getPage().getCount(), equalTo(1L));
		payer = page.getContent().get(0);
		assertThat(payer.getFirstName(), equalTo("Jim"));
		assertThat(payer.getLastName(), equalTo("Kirk"));
		assertThat(payer.getEmailAddress(), equalTo("captain@enterprise.com"));
		assertThat(payer.getFullName(), equalTo("James T Kirk"));
		assertThat(payer.getMerchantReference(), equalTo("12345"));

		profiler.start("Delete Payer");
		client.deletePayer(payer.getId());
	}
}
