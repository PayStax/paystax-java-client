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
