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

import com.netradius.commons.lang.ValidationHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Slf4j
public class IntegrationTestHelper {

	private static Properties config;
	private static PayStaxClient client;
	private static PayStaxPayer payer;

	private static Properties read(final File file) {
		log.info("Loading integration test settings from " + file.getAbsolutePath());
		try (FileInputStream in = new FileInputStream(file)) {
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException x) {
			log.error("Error reading " + file.getAbsolutePath() + ": " + x.getMessage(), x);
		}
		return null;
	}

	private static boolean loadConfig() {
		// Try and read from default location first
		File file = new File("integration.properties");
		if (file.exists()) {
			config = read(file);
		}

		// Try alternate location
		if (config == null) {

			// Check for system property
			String loc = System.getProperty("config");
			if (loc == null) {
				loc = System.getProperty("CONFIG");
			}

			// Check environment variable
			if (loc == null) {
				loc = System.getenv("CONFIG");
			}
			if (loc == null) {
				loc = System.getenv("config");
			}

			if (loc != null) {
				file = new File(loc);
				if (file.exists()) {
					config = read(file);
				}
			}
		}

		return config != null;
	}

	private static void init() throws IOException {
		if (config == null) {

			log.info("Initializing account for integration tests");

			if (!loadConfig()) {
				throw new IllegalStateException("Unable to load integration test settings");
			}

			String username = config.getProperty("username");
			ValidationHelper.checkForEmpty(username);
			String password = config.getProperty("password");
			ValidationHelper.checkForEmpty(password);
			String url = config.getProperty("url");
			ValidationHelper.checkForEmpty(url);

			String firstName = config.getProperty("site.firstName");
			ValidationHelper.checkForEmpty(firstName);
			String lastName = config.getProperty("site.lastName");
			ValidationHelper.checkForEmpty(lastName);
			String companyName = config.getProperty("site.companyName");
			ValidationHelper.checkForEmpty(companyName);
			String emailAddress = config.getProperty("site.emailAddress");
			ValidationHelper.checkForEmpty(emailAddress);
			String siteUsername = config.getProperty("site.username");
			ValidationHelper.checkForEmpty(siteUsername);
			String sitePassword = config.getProperty("site.password");
			ValidationHelper.checkForEmpty(sitePassword);

			String alias = config.getProperty("site.alias");
			if (alias == null || alias.trim().isEmpty()) {
				alias = UUID.randomUUID().toString().replaceAll("-", "");
			}

			int idx1 = url.lastIndexOf("/") + 1;
			int idx2 = url.indexOf(".");
			String siteUrl = url.substring(0, idx1) + alias + url.substring(idx2, url.length());

			log.info("Setting up new account for integration tests");
			new PayStaxClient(url, username, password)
					.newAccount()
					.setSite(alias)
					.setUsername(siteUsername)
					.setPassword(sitePassword)
					.setFirstName(firstName)
					.setLastName(lastName)
					.setCompanyName(companyName)
					.setEmailAddress(emailAddress)
					.save();

			client = new PayStaxClient(siteUrl, siteUsername, sitePassword);
		}
	}

	public static PayStaxClient getClient() throws IOException {
		init();
		return client;
	}

	public static PayStaxPayer getPayer() throws IOException {
		init();
		return payer;
	}

	public static Properties getConfig() throws IOException {
		if (config == null) {
			loadConfig();
		}
		return config;
	}

}
