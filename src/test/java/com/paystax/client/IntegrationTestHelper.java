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
import com.paystax.client.exception.PayStaxBadRequestException;
import com.paystax.client.gateway.PayStaxFakeGateway;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Methods to assist with integration tests.
 *
 * @author Erik R. Jensen
 */
@Slf4j
public class IntegrationTestHelper {

	private static Random rand = new Random();
	private static Properties config;
	private static String username;
	private static String password;
	private static String url;
	private static List<String> companies;
	private static List<String> givenNames;
	private static List<String> surnames;


	private static PayStaxClient client;
	private static PayStaxFakeGateway fakeGateway;

	public static char[] alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static char[] alphaLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public static char[] alphaUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static char[] numeric = "01234567890".toCharArray();
	public static char[] alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	public static char[] alphaLowerNumeric = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	public static char[] punct = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray();

	/**
	 * Reads properties from a configuration file.
	 *
	 * @param file the file to read
	 * @return null if the file could not be loaded
	 */
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

	/**
	 * Loads the configuration.
	 *
	 * @return true if the config was loaded successfully, false if otherwise
	 */
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

	private static void init() {
		if (config == null) {
			log.info("Initializing account for integration tests");

			if (!loadConfig()) {
				throw new IllegalStateException("Unable to load integration test settings");
			}

			username = config.getProperty("username");
			ValidationHelper.checkForEmpty(username);
			password = config.getProperty("password");
			ValidationHelper.checkForEmpty(password);
			url = config.getProperty("url");
			ValidationHelper.checkForEmpty(url);

			client = new PayStaxClient(url, username, password);
		}
	}

	public static Properties getConfig() throws IOException {
		init();
		return config;
	}

	/**
	 * Returns the default client. This client usually has permissions only to create new accounts.
	 *
	 * @return the default client
	 */
	public static PayStaxClient getDefaultClient() {
		init();
		return client;
	}

	/**
	 * Returns a random character from a sequence of characters.
	 *
	 * @param chars the sequence of characters
	 * @return the random character
	 */
	public static char random(final char[] chars) {
		int idx = rand.nextInt(chars.length);
		return chars[idx];
	}

	/**
	 * Chooses a random number between min inclusive and max inclusive.
	 *
	 * @param min the minimim number
	 * @param max the maximum number
	 * @return the randon number
	 */
	public static int random(int min, int max) {
		if (min == max) {
			return 1;
		}
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than or equal to min");
		}
		return rand.nextInt(max - min) + min;
	}

	/**
	 * Generates a random string.
	 *
	 * @param min the minimum number of characters allowed
	 * @param max the maximum number of characters allowed
	 * @param chars the characters to choose from or null
	 * @return the random string
	 */
	public static String random(final int min, final int max, char[] chars) {
		if (chars == null) {
			chars = alphaNumeric;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= random(min, max); i++) {
			sb.append(random(chars));
		}
		return sb.toString();
	}

	/**
	 * Reads lines from a file on the classpath into a List.
	 *
	 * @param path the path to the file on the classpath
	 * @return the lines
	 */
	private static List<String> readLinesClasspath(String path) {
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(IntegrationTestHelper.class.getResourceAsStream(path),StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
			return list;
		} catch (IOException x) {
			throw new IllegalArgumentException("Unable to read file [" + path + "] from classpath: " + x.getMessage(), x);
		}
	}

	/**
	 * Returns a random site name that's guranteed to be unused at the time it's generated.
	 *
	 * @return a valid unused random site name
	 */
	public static String site() {
		return random(1, 50, alphaLowerNumeric);
	}

	/**
	 * Converts a site name into a url
	 * @param site the site name
	 * @return the url
	 */
	public static String url(String site) {
		init();
		int idx1 = url.lastIndexOf("/") + 1;
		int idx2 = url.indexOf(".");
		return url.substring(0, idx1) + site + url.substring(idx2, url.length());
	}

	/**
	 * Returns a random given name.
	 *
	 * @return a random given name
	 */
	public static String givenName() {
		if (givenNames == null) {
			givenNames = readLinesClasspath("/givennames.txt");
		}
		return givenNames.get(rand.nextInt(givenNames.size()));
	}

	/**
	 * Returns a random surname.
	 *
	 * @return a random surname
	 */
	public static String surname() {
		if (surnames == null) {
			surnames = readLinesClasspath("/surnames.txt");
		}
		return surnames.get(rand.nextInt(surnames.size()));
	}

	/**
	 * Retruns a random company name.
	 *
	 * @return a random company name
	 */
	public static String companyName() {
		if (companies == null) {
			companies = readLinesClasspath("/companies.txt");
		}
		return companies.get(rand.nextInt(companies.size()));
	}

	/**
	 * Returns a random email address from example.com.
	 *
	 * @return a random example.com emaila ddress
	 */
	public static String emailAddress() {
		return random(1, 64, alphaNumeric) + "@example.com";
	}

	/**
	 * Returns a random username.
	 *
	 * @return a random username
	 */
	public static String username() {
		String username = random(1, 255, alphaNumeric);
		log.trace("Generated username [" + username + "]");
		return username;
	}

	/**
	 * Returns a random password.
	 *
	 * @return a random password
	 */
	public static String password() {
		String password = random(1, 1, punct)
				+ random(1, 1, alphaUpper)
				+ random(6, 253, alphaNumeric);
		log.trace("Generated password [" + password + "]");
		return password;
	}

	/**
	 * Creates a new account and passes back an instantiated client to the account.
	 *
	 * @return a client attached to the account
	 * @throws IOException if the account could not be setup
	 */
	public static PayStaxClient newAccount() throws IOException {
		init();
		int attempt = 0;
		while (attempt < 5) {
			String username = username();
			String password = password();
			String site = site();
			attempt++;
			try {
				getDefaultClient().newAccount()
						.setSite(site)
						.setUsername(username)
						.setPassword(password)
						.setFirstName(givenName())
						.setLastName(surname())
						.setCompanyName(companyName())
						.setEmailAddress(emailAddress())
						.save();
			} catch (PayStaxBadRequestException x) {
				if (x.getErrors().size() == 1 && x.getErrors().get(0).getField().equals("site")) {
					continue;
				}
				throw x;
			}
			return new PayStaxClient(url(site), username, password);
		}
		throw new IllegalStateException("Unable to come create a new account after 5 attempts");
	}
}
