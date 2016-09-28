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

import com.paystax.client.paymentmethod.*;
import com.paystax.client.transaction.PayStaxCardType;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.profiler.Profiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Erik R. Jensen
 */
@Slf4j
public class PaymentMethodIT {

	private static PayStaxClient client;
	private static Profiler profiler;

	@BeforeClass
	public static void init() throws IOException {
		client = IntegrationTestHelper.getClient();
		profiler = new Profiler("Payment Method Integration Tests");
	}

	@Test
	public void testPaymentMethodSearch() throws IOException {
		PayStaxPayer payer1 = client.newPayer()
				.setFirstName("Test")
				.setLastName("One")
				.save();

		PayStaxSEPAPaymentMethod paymentMethod1 = client.newPaymentMethod(PayStaxSEPAPaymentMethod.class)
				.setFirstName("James")
				.setLastName("Kirk")
				.setSignatureDate(new Date())
				.setBic("WIREDEMMXXX")
				.setIban("DE42512308000000060004")
				.setPayerId(payer1.getId())
				.setMandateType(PayStaxSEPAMandateContentType.HTML)
				.setMandate("<html><body>test</body></html>".getBytes(StandardCharsets.UTF_8))
				.setCreditorId("DE98ZZZ09999999999")
				.save();

		PayStaxPayer payer2 = client.newPayer()
				.setFirstName("Test")
				.setLastName("Two")
				.save();

		PayStaxSEPAPaymentMethod paymentMethod2 = client.newPaymentMethod(PayStaxSEPAPaymentMethod.class)
				.setFirstName("James")
				.setLastName("Kirk")
				.setSignatureDate(new Date())
				.setBic("WIREDEMMXXX")
				.setIban("DE42512308000000060004")
				.setPayerId(payer2.getId())
				.setMandateType(PayStaxSEPAMandateContentType.HTML)
				.setMandate("<html><body>test</body></html>".getBytes(StandardCharsets.UTF_8))
				.setCreditorId("DE98ZZZ09999999999")
				.save();

		PayStaxCardPaymentMethod cardPaymentMethod = client.newPaymentMethod(PayStaxCardPaymentMethod.class)
				.setPayerId(payer2.getId())
				.setCardType(PayStaxCardType.VISA)
				.setAccountNumber("4111111111111111")
				.setExpMonth(Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1))
				.setExpYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR) + 2))
				.setCardholderName("Jim Kirk")
				.setAddress(new PayStaxAddress().setPostalCode("84062"))
				.save();

		PayStaxPage<PayStaxPaymentMethod> res = client.paymentMethodSearch().search();
		assertThat(res.getPage().getCount(), equalTo(3l));

		res = client.paymentMethodSearch().addPayerId(payer1.getId()).search();
		assertThat(res.getPage().getCount(), equalTo(1l));
		assertThat(res.getContent().get(0).getPayerId(), equalTo(payer1.getId()));

		res = client.paymentMethodSearch().addPayerId(payer2.getId()).search();
		assertThat(res.getPage().getCount(), equalTo(2l));
		assertThat(res.getContent().get(0).getPayerId(), equalTo(payer2.getId()));

		res = client.paymentMethodSearch().addType(PayStaxPaymentMethodType.SEPA).search();
		assertThat(res.getPage().getCount(), equalTo(2l));

	}

}
