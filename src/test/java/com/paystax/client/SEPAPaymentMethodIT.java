package com.paystax.client;

import com.paystax.client.paymentmethod.PayStaxSEPAPaymentMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Erik R. Jensen
 */
public class SEPAPaymentMethodIT {

	private static PayStaxClient client;

	@BeforeClass
	public static void init() throws IOException {
		client = IntegrationTestHelper.getClient();
	}

	@Test
	public void test() {

		client.newPaymentMethod(PayStaxSEPAPaymentMethod.class)
				.setFirstName("Jim")
				.setLastName("Kirm");

	}
}
