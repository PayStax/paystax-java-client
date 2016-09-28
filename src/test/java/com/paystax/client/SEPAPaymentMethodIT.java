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

import com.paystax.client.paymentmethod.PayStaxPaymentMethod;
import com.paystax.client.paymentmethod.PayStaxPaymentMethodType;
import com.paystax.client.paymentmethod.PayStaxSEPAMandateContentType;
import com.paystax.client.paymentmethod.PayStaxSEPAPaymentMethod;
import org.junit.*;
import org.slf4j.profiler.Profiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Erik R. Jensen
 */
public class SEPAPaymentMethodIT {

	private static PayStaxClient client;
	private static Profiler profiler;

	@BeforeClass
	public static void beforeClass() throws IOException {
		client = IntegrationTestHelper.getClient();
		profiler = new Profiler("SEPA Payment Method Integration Tests");
	}

	@AfterClass
	public static void afterClass() {
		profiler.stop().print();
	}

	private PayStaxPayer payer;

	@Before
	public void setup() throws IOException {
		payer = client.newPayer()
				.setFirstName("SEPA")
				.setLastName("Test")
				.setFullName("SEPA Test")
				.setEmailAddress("sepatest@paystax.com")
				.save();
	}

	@After
	public void cleanup() throws IOException {
		client.deletePayer(payer.getId());
	}

	@Test
	public void happyPathTest() throws IOException {
		Date now = new Date();
		profiler.start("New SEPA Payment Method");
		PayStaxSEPAPaymentMethod paymentMethod = client.newPaymentMethod(PayStaxSEPAPaymentMethod.class)
				.setFirstName("James")
				.setLastName("Kirk")
				.setSignatureDate(now)
				.setBic("WIREDEMMXXX")
				.setIban("DE42512308000000060004")
				.setPayerId(payer.getId())
				.setMandateType(PayStaxSEPAMandateContentType.HTML)
				.setMandate("<html><body>test</body></html>".getBytes(StandardCharsets.UTF_8))
				.setCreditorId("DE98ZZZ09999999999")
				.save();

		profiler.start("Update SEPA Payment Method");
		paymentMethod.setFirstName("Jim")
				.save();

		profiler.start("Get SEPA Payment Method (with Class)");
//		PayStaxPage<PayStaxSEPAPaymentMethod> search = client.paymentMethodSearch(PayStaxSEPAPaymentMethod.class).search();
//		search.getContent();

		paymentMethod = client.getPaymentMethod(paymentMethod.getId(), PayStaxSEPAPaymentMethod.class);
		profiler.stop();

		assertThat(paymentMethod.getType(), equalTo(PayStaxPaymentMethodType.SEPA));
		assertThat(paymentMethod.getFirstName(), equalTo("Jim"));
		assertThat(paymentMethod.getLastName(), equalTo("Kirk"));
		assertThat(paymentMethod.getSignatureDate(), equalTo(now));
		assertThat(paymentMethod.getBic(), equalTo("WIREDEMMXXX"));
		assertThat(paymentMethod.getIban(), equalTo("DE42512308000000060004"));
		assertThat(paymentMethod.getPayerId(), equalTo(payer.getId()));
		assertThat(paymentMethod.getCreditorId(), equalTo("DE98ZZZ09999999999"));
		assertThat(paymentMethod.getMandateId(), notNullValue());

//		assertThat(paymentMethod.getMandateUrl(), notNullValue());
		assertThat(paymentMethod.getMandateContentType(), equalTo(PayStaxSEPAMandateContentType.HTML));

		profiler.start("Get SEPA Payment Method Mandate");
		byte[] mandate = paymentMethod.getMandate();
		profiler.stop();
		String mandateStr = new String(mandate, StandardCharsets.UTF_8);
		assertThat(mandateStr, equalTo("<html><body>test</body></html>"));

		profiler.start("Search SEPA Payment Method");
		PayStaxPage<PayStaxPaymentMethod> page = client.paymentMethodSearch()
				.addPayerId(payer.getId())
				.search();
		profiler.stop();

		assertThat(page.getPage().getCount(), equalTo(1L));
		paymentMethod = (PayStaxSEPAPaymentMethod)page.getContent().get(0);

		assertThat(paymentMethod.getType(), equalTo(PayStaxPaymentMethodType.SEPA));
		assertThat(paymentMethod.getFirstName(), equalTo("Jim"));
		assertThat(paymentMethod.getLastName(), equalTo("Kirk"));
		assertThat(paymentMethod.getSignatureDate(), equalTo(now));
		assertThat(paymentMethod.getBic(), equalTo("WIREDEMMXXX"));
		assertThat(paymentMethod.getIban(), equalTo("DE42512308000000060004"));
		assertThat(paymentMethod.getPayerId(), equalTo(payer.getId()));
		assertThat(paymentMethod.getCreditorId(), equalTo("DE98ZZZ09999999999"));
		assertThat(paymentMethod.getMandateId(), notNullValue());
		assertThat(paymentMethod.getMandateUrl(), notNullValue());
		assertThat(paymentMethod.getMandateContentType(), equalTo(PayStaxSEPAMandateContentType.HTML));

		profiler.start("Delete SEPA Payment Method");
		client.deletePaymentMethod(paymentMethod.getId());
		profiler.stop();
	}
}
