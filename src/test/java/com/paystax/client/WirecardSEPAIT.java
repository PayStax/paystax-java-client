package com.paystax.client;

import com.paystax.client.gateway.PayStaxGateway;
import com.paystax.client.gateway.PayStaxWirecardGateway;
import com.paystax.client.junit.ConditionalIgnoreRule;
import com.paystax.client.paymentmethod.PayStaxPaymentMethod;
import com.paystax.client.paymentmethod.PayStaxSEPAMandateContentType;
import com.paystax.client.paymentmethod.PayStaxSEPAPaymentMethod;
import com.paystax.client.transaction.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.profiler.Profiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static com.paystax.client.IntegrationTestHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
	private static PayStaxPayer payer;
	private static PayStaxGateway<PayStaxWirecardGateway> gateway;
	private static PayStaxPaymentMethod<PayStaxSEPAPaymentMethod> paymentMethod;

	@Rule
	public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

	public static class WirecardEnabled implements ConditionalIgnoreRule.IgnoreCondition {
		@Override
		public boolean isSatisfied() {
			return !(url != null && username != null && password != null && merchantId != null);
		}
	}

	private static PayStaxWirecardGateway newGateway() throws IOException {
		return client.newGateway(PayStaxWirecardGateway.class)
				.setUrl(url)
				.setUsername(username)
				.setPassword(password)
				.setMerchantId(merchantId)
				.setName("WIRECARD-" + System.currentTimeMillis())
				.save();
	}

	private static PayStaxPayer newPayer() throws IOException {
		return client.newPayer()
				.setFirstName("James")
				.setLastName("Kirk")
				.setFullName("James Kirk")
				.setEmailAddress("sepatest@paystax.com")
				.save();
	}

	private static PayStaxSEPAPaymentMethod newPaymentMethod(PayStaxPayer payer) throws IOException {
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

	@BeforeClass
	public static void init() throws IOException {
		client = newAccount();
		profiler = new Profiler("Wirecard SEPA Integration Tests");
		Properties props = IntegrationTestHelper.getConfig();
		url = props.getProperty("wirecard.url");
		username = props.getProperty("wirecard.username");
		password = props.getProperty("wirecard.password");
		merchantId = props.getProperty("wirecard.merchantId");
		gateway = newGateway();
		payer = newPayer();
		paymentMethod = newPaymentMethod(payer);
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void testAuthAndDebitAndVoid() throws IOException {
		profiler.start("Wirecard SEPA Test Auth And Debit");
		PayStaxSEPAAuth auth = client.newTransaction(PayStaxSEPAAuth.class)
				.setAmount(amount())
				.setPaymentMethodId(paymentMethod.getId())
				.setGatewayId(gateway.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.save();

		assertThat(auth.getId(), notNullValue());
		assertThat(auth.isSuccess(), equalTo(true));

		PayStaxSEPADebitAuth debit = client.newTransaction(PayStaxSEPADebitAuth.class)
				.setMerchantReference(UUID.randomUUID().toString())
				.setPriorTransaction(auth.getId())
				.save();

		assertThat(debit.getId(), notNullValue());
		assertThat(debit.isSuccess(), equalTo(true));

		PayStaxSEPAVoid svoid = client.newTransaction(PayStaxSEPAVoid.class)
				.setMerchantReference(UUID.randomUUID().toString())
				.setPriorTransaction(debit.getId())
				.save();

		assertThat(svoid.getId(), notNullValue());
		assertThat(svoid.isSuccess(), equalTo(true));

		profiler.stop();
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void testDebit() throws IOException {
		profiler.start("Wirecard SEPA Test Debit");
		PayStaxSEPADebit debit = client.newTransaction(PayStaxSEPADebit.class)
				.setAmount(amount())
				.setPaymentMethodId(paymentMethod.getId())
				.setGatewayId(gateway.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.save();

		assertThat(debit.getId(), notNullValue());
		assertThat(debit.isSuccess(), equalTo(true));

		profiler.stop();
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void testDebitAndVoid() throws IOException {
		profiler.start("Wirecard SEPA Test Debit And Void");

		PayStaxSEPADebit debit = client.newTransaction(PayStaxSEPADebit.class)
				.setAmount(amount())
				.setPaymentMethodId(paymentMethod.getId())
				.setGatewayId(gateway.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.save();

		assertThat(debit.getId(), notNullValue());
		assertThat(debit.isSuccess(), equalTo(true));

		PayStaxSEPAVoid svoid = client.newTransaction(PayStaxSEPAVoid.class)
				.setMerchantReference(UUID.randomUUID().toString())
				.setPriorTransaction(debit.getId())
				.save();

		assertThat(svoid.getId(), notNullValue());
		assertThat(svoid.isSuccess(), equalTo(true));

		profiler.stop();
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void testCredit() throws IOException {
		profiler.start("Wirecard SEPA Test Credit");

		PayStaxSEPACredit credit = client.newTransaction(PayStaxSEPACredit.class)
				.setAmount(amount())
				.setPaymentMethodId(paymentMethod.getId())
				.setGatewayId(gateway.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.save();

		assertThat(credit.getId(), notNullValue());
		assertThat(credit.isSuccess(), equalTo(true));

		profiler.stop();
	}

	@Test
	@ConditionalIgnoreRule.ConditionalIgnore(condition = WirecardSEPAIT.WirecardEnabled.class)
	public void testCreditAndVoid() throws IOException {
		profiler.start("Wirecard SEPA Test Credit And Void");

		PayStaxSEPACredit credit = client.newTransaction(PayStaxSEPACredit.class)
				.setAmount(amount())
				.setPaymentMethodId(paymentMethod.getId())
				.setGatewayId(gateway.getId())
				.setMerchantReference(UUID.randomUUID().toString())
				.save();

		assertThat(credit.getId(), notNullValue());
		assertThat(credit.isSuccess(), equalTo(true));

		PayStaxSEPAVoid svoid = client.newTransaction(PayStaxSEPAVoid.class)
				.setMerchantReference(UUID.randomUUID().toString())
				.setPriorTransaction(credit.getId())
				.save();

		assertThat(svoid.getId(), notNullValue());
		assertThat(svoid.isSuccess(), equalTo(true));

		profiler.stop();
	}
}
