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

import com.paystax.client.gateway.AuthorizeNetEnvironment;
import com.paystax.client.gateway.PayStaxAuthorizeNetGateway;
import com.paystax.client.junit.ConditionalIgnoreRule;
import com.paystax.client.transaction.PayStaxCardAuth;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Integration tests for Authorize.Net
 *
 * @author Erik R. Jensen
 */
@Slf4j
public class AuthorizeNetIT {

	private static PayStaxClient client;
	private static String loginId;
	private static String transactionKey;
	private static AuthorizeNetEnvironment environment;

	@Rule
	public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

	@BeforeClass
	public static void init() throws IOException {
		client = IntegrationTestHelper.getClient();
		Properties props = IntegrationTestHelper.getConfig();
		loginId = props.getProperty("authnet.loginid");
		transactionKey = props.getProperty("authnet.transkey");
		environment = AuthorizeNetEnvironment.valueOf(props.getProperty("authnet.environment"));
	}

	public static class AuthorizeNetEnabled implements ConditionalIgnoreRule.IgnoreCondition {
		@Override
		public boolean isSatisfied() {
			return !(loginId != null && transactionKey != null && environment != null);
		}
	}

	private String getExpirationYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);
		return Integer.toString(cal.get(Calendar.YEAR));
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

	private PayStaxAuthorizeNetGateway getGateway() throws IOException {
		PayStaxAuthorizeNetGateway gateway = client.newGateway(PayStaxAuthorizeNetGateway.class);
		gateway.setEnvironment(environment)
				.setLoginId(loginId)
				.setTransactionKey(transactionKey)
				.setName("AUTHNET-" + System.currentTimeMillis())
				.save();
		return gateway;
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = AuthorizeNetIT.AuthorizeNetEnabled.class)
	public void testAuth() throws IOException {
		log.info("Testing Authorize.Net Auth Transaction");
		PayStaxAuthorizeNetGateway gateway = getGateway();
		PayStaxCardAuth tx = client.newTransaction(PayStaxCardAuth.class)
				.setMerchantReference(UUID.randomUUID().toString())
				.setGatewayId(gateway.getId())
				.setAmount(getAmount())
				.setCurrency("USD")
				.setAccountNumber("4242424242424242")
				.setExpMonth("08")
				.setExpYear(getExpirationYear())
				.save();

		assertTrue(tx.isSuccess());

		log.info("Save transaction: " + tx.toString());
	}

}