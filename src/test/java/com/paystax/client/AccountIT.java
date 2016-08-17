package com.paystax.client;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Erik R. Jensen
 */
public class AccountIT {

	private static PayStaxClient client;

	@BeforeClass
	public static void init() throws IOException {
		client = IntegrationTestHelper.getClient();
	}

	@Test
	public void test() throws IOException {

		PayStaxAccount account = client.getAccount();

	}


}
