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

import com.paystax.client.gateway.PayStaxWirecardGateway;
import com.paystax.client.junit.ConditionalIgnoreRule;
import com.paystax.client.paymentmethod.PayStaxSEPAMandateContentType;
import com.paystax.client.paymentmethod.PayStaxSEPAPaymentMethod;
import com.paystax.client.transaction.PayStaxSEPA;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.profiler.Profiler;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static com.paystax.client.IntegrationTestHelper.*;

/**
 * @author Erik R. Jensen
 */
@Slf4j
public class WirecardSEPAIT {

	private static PayStaxClient client;
	private static Profiler profiler;
	private static String url;
	private static String username;
	private static String password;
	private static String merchantId;

	@Rule
	public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

	@BeforeClass
	public static void init() throws IOException {
		client = newAccount();
		profiler = new Profiler("Wirecard SEPA Integration Tests");
		Properties props = IntegrationTestHelper.getConfig();
		url = props.getProperty("wirecard.url");
		username = props.getProperty("wirecard.username");
		password = props.getProperty("wirecard.password");
		merchantId = props.getProperty("wirecard.merchantId");
	}

	public static class WirecardEnabled implements ConditionalIgnoreRule.IgnoreCondition {
		@Override
		public boolean isSatisfied() {
			return !(url != null && username != null && password != null && merchantId != null);
		}
	}

	private BigDecimal getAmount() {
		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.SECOND) + 500;
		if (cal.get(Calendar.MINUTE) % 2 == 0) {
			num += 60;
		}
		char[] p = Integer.toString(num).toCharArray();
		assertThat(p.length, equalTo(3));
		return new BigDecimal(p[0] + "." + p[1] + p[2]);
	}

	private PayStaxWirecardGateway getGateway() throws IOException {
		return client.newGateway(PayStaxWirecardGateway.class)
				.setUrl(url)
				.setUsername(username)
				.setPassword(password)
				.setMerchantId(merchantId)
				.setName("WIRECARD-" + System.currentTimeMillis())
				.save();
	}

	private PayStaxPayer getPayer() throws IOException {
		return client.newPayer()
				.setFirstName("James")
				.setLastName("Kirk")
				.setFullName("James Kirk")
				.setEmailAddress("sepatest@paystax.com")
				.save();
	}

	private PayStaxSEPAPaymentMethod getPaymentMethod(PayStaxPayer payer) throws IOException {
		return client.newPaymentMethod(PayStaxSEPAPaymentMethod.class)
				.setFirstName("James")
				.setLastName("Kirk")
				.setSignatureDate(new Date())
				.setBic("WIREDEMMXXX")
				.setIban("DE42512308000000060004")
				.setPayerId(payer.getId())
				.setMandateType(PayStaxSEPAMandateContentType.HTML)
				.setMandate("<html><body>test</body></html>".getBytes(StandardCharsets.UTF_8))
				.setCreditorId("DE98ZZZ09999999999")
				.save();
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void test() throws IOException {
		profiler.start("New SEPA Gateway");
		PayStaxWirecardGateway gateway = getGateway();
		profiler.start("New SEPA Payer");
		PayStaxPayer payer = getPayer();
		profiler.start("New Payment Method");
		PayStaxSEPAPaymentMethod paymentMethod = getPaymentMethod(payer);
		profiler.start("New SEPA Transaction");
		PayStaxSEPA tx = client.newTransaction(PayStaxSEPA.class)
				.setAmount(getAmount())
				.setPaymentMethodId(paymentMethod.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.setGatewayId(gateway.getId())
				.save();
		profiler.stop();

		assertTrue(tx.isSuccess());
		log.info("Saved transaction: " + tx.toString());
	}
}
