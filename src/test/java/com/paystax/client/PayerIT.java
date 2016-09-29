/**
 *    Copyright 2013-2016 PayStax, LLC
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

import com.paystax.client.exception.PayStaxBadRequestException;
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
	public static void beforeClass() throws IOException {
		client = IntegrationTestHelper.getClient();
		profiler = new Profiler("Payer Integration Tests");
	}

	@AfterClass
	public static void afterClass() {
		profiler.stop().print();
	}

	@Test(expected = PayStaxBadRequestException.class)
	public void testDuplicateMerchantReference() throws IOException {
		PayStaxPayer payer = client.newPayer()
				.setFirstName("James")
				.setLastName("Kirk")
				.setEmailAddress("captain@enterprise.com")
				.setFullName("James T Kirk")
				.setMerchantReference("123456")
				.save();

		payer = client.newPayer()
				.setFirstName("James")
				.setLastName("Kirk")
				.setEmailAddress("captain@enterprise.com")
				.setFullName("James T Kirk")
				.setMerchantReference("123456")
				.save();
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
		profiler.stop();
	}
}
