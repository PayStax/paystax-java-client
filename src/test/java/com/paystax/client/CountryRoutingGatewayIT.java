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

import com.paystax.client.gateway.PayStaxCountryRoutingGateway;
import com.paystax.client.gateway.PayStaxFakeGateway;
import com.paystax.client.gateway.PayStaxGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.profiler.Profiler;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.paystax.client.IntegrationTestHelper.*;

/**
 * @author Erik R. Jensen
 */
@Slf4j
public class CountryRoutingGatewayIT {

	private static PayStaxClient client;
	private static PayStaxFakeGateway fakeGateway;
	private static Profiler profiler;

	@BeforeClass
	public static void init() throws IOException {
		client = newAccount();
		fakeGateway = client.newGateway(PayStaxFakeGateway.class)
				.setName("Integration Test Fake Gateway")
				.save();
		profiler = new Profiler("Country Routing Gateway Integration Tests");
	}

	@Test
	public void test() throws IOException {
		profiler.start("New Country Routing Gateway");
		PayStaxCountryRoutingGateway gateway = client.newGateway(PayStaxCountryRoutingGateway.class)
				.setName("Integration TestCountry Routing Gateway")
				.setDefaultGateway(fakeGateway.getId())
				.addRoute("US", fakeGateway.getId())
				.save();
		profiler.start("Search Gateways");
		PayStaxPage<PayStaxGateway> gateways = client.gatewaySearch().setNameEquals("Integration TestCountry Routing Gateway")
				.search();
		assertThat(gateways.getPage().getCount(), equalTo(1l));
		PayStaxCountryRoutingGateway foundGateway = (PayStaxCountryRoutingGateway)gateways.getContent().get(0);
		assertThat(foundGateway.getId(), equalTo(foundGateway.getId()));
	}

}
