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
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static com.paystax.client.IntegrationTestHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Erik R. Jensen
 */
public class AccountIT {

	private static PayStaxClient client;

	@BeforeClass
	public static void init() throws IOException {
		client = getDefaultClient();
	}

	@Test(expected = PayStaxBadRequestException.class)
	public void testSiteUnavailable() throws IOException {
		try {
			client.newAccount()
					.setSite("master")
					.setUsername("test")
					.setPassword("Password1$")
					.setFirstName(givenName())
					.setLastName(surname())
					.setCompanyName(companyName())
					.setEmailAddress(emailAddress())
					.save();
		} catch (PayStaxBadRequestException x) {
			assertThat(x.getErrors().size(), equalTo(1));
			assertThat(x.getErrors().get(0).getField(), equalTo("site"));
			throw x;
		}
	}

}
