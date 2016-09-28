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
		client = IntegrationTestHelper.getClient();
		fakeGateway = IntegrationTestHelper.getFakeGateway();
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
		assertThat(gateways.getPage().getCount(), equalTo(1));
		PayStaxCountryRoutingGateway foundGateway = (PayStaxCountryRoutingGateway)gateways.getContent().get(0);
		assertThat(foundGateway.getId(), equalTo(foundGateway.getId()));
	}

}
