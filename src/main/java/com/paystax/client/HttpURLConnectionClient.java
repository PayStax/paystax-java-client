/**
 *    Copyright 2014 PayStax, LLC
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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.paystax.client.exception.PayStaxBadRequestException;
import com.paystax.client.exception.PayStaxException;
import com.paystax.client.exception.PayStaxForbiddenException;
import com.paystax.client.exception.PayStaxNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * A simple HTTP client used to send get, update and create requests. This class uses
 * HttpURLConnection internally.
 *
 * @author Erik R. Jensen
 */
public class HttpURLConnectionClient implements HttpClient, Serializable {

	private static final long serialVersionUID = -5275552394410711694L;
	private static final Logger log = LoggerFactory.getLogger(HttpURLConnectionClient.class);

	protected static ObjectMapper mapper = new ObjectMapper();
	protected String authorization;

	/**
	 * Creates a new HTTP client.
	 *
	 * @param username the PayStax username
	 * @param password the PayStax password
	 */
	public HttpURLConnectionClient(String username, String password) {
		authorization = username + ":" + password;
		authorization = "Basic " + DatatypeConverter.printBase64Binary(authorization.getBytes(Charset.forName("UTF-8")));
		mapper = new ObjectMapper();
	}

	protected void logRequest(HttpURLConnection conn, String body) {
		StringBuilder sb = new StringBuilder("\nHTTP Request:\n")
				.append("  URL: ").append(conn.getURL()).append("\n")
				.append("  Request Method: ").append(conn.getRequestMethod()).append("\n");
		Map<String, List<String>> headers = conn.getRequestProperties();
		sb.append("  Request Headers:\n");
		for (String key : headers.keySet()) {
			if (key != null) {
				sb.append("    ").append(key).append(": ")
						.append(headers.get(key)).append("\n");
			}
		}
		sb.append("  Request Body:\n").append(body);
		log.debug(sb.toString());
	}

	protected void logResponse(HttpURLConnection conn, String body) throws IOException {
		StringBuilder sb = new StringBuilder("\nHTTP Response:\n")
				.append("  Response Code: ").append(conn.getResponseCode()).append("\n");
		Map<String, List<String>> headers = conn.getHeaderFields();
		sb.append("  Response Headers:\n");
		for (String key : headers.keySet()) {
			if (key != null) {
				sb.append("    ").append(key).append(": ")
						.append(headers.get(key)).append("\n");
			}
		}
		sb.append("  Response Body:\n").append(body);
		log.debug(sb.toString());
	}

	/**
	 * Helper method to read the contents of an InputStream to a String.
	 * This method will not close the stream.
	 *
	 * @param in the InputStream to rea
	 * @return the contents of the stream as a String
	 * @throws IOException if an I/O error occurs
	 */
	protected String readString(InputStream in) throws IOException {
		InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
		StringBuilder sb = new StringBuilder();
		char[] buf = new char[8192];
		for (int read = reader.read(buf); read >= 0; read = reader.read(buf)) {
			sb.append(buf, 0, read);
		}
		return sb.toString();
	}

	/**
	 * Helper method to completely read the error stream.
	 *
	 * @param conn the connection
	 * @return the error message or null
	 */
	protected String readError(HttpURLConnection conn) {
		InputStream err = null;
		try {
			err = conn.getErrorStream();
			if (err != null) {
				return readString(err);
			}
		} catch (IOException x) {
			log.warn("An I/O error occurred reading the HTTP error stream: " + x.getMessage(), x);
		} finally {
			if (err != null) {
				try {
					err.close();
				} catch (IOException x) { /* do nothing */ }
			}
		}
		return null;
	}

	/**
	 * Used for error handling.
	 *
	 * @param x the exception thrown or null
	 * @param conn the connection
	 * @return the exception to throw
	 * @throws IOException if an I/O error occurs
	 */
	protected IOException getError(IOException x, HttpURLConnection conn) throws IOException {
		if (conn != null) {
			String error = readError(conn);
			if (log.isDebugEnabled()) {
				logResponse(conn, error);
			}
			int status = conn.getResponseCode();
			switch (status) {
				case 404:
					return new PayStaxNotFoundException(error);
				case 400:
					if (error != null) {
						List<Error> errors = mapper.readValue(error, mapper.getTypeFactory()
								.constructCollectionType(List.class, PayStaxError.class));
						return new PayStaxBadRequestException("Received error: " + error, errors);
					}
					throw new PayStaxBadRequestException();
				case 403:
					return new PayStaxForbiddenException(error); // TODO
				default:
					return new PayStaxException(error);
			}
		}
		return x;
	}

	/**
	 * Helper method to setup the connection.
	 *
	 * @param method the HTTP method to use
	 * @param url the URL to query
	 * @return the configured connection
	 * @throws IOException if an I/O error occurs
	 */
	protected HttpURLConnection setup(HttpMethod method, String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Authorization", authorization);
		conn.setRequestMethod(method.toString());
		conn.setConnectTimeout(120 * 1000); // 2 minutes // TODO Make configurable
		conn.setReadTimeout(120 * 1000); // 2 minutes // TODO Make configurable
		conn.setRequestProperty("Accept", "application/vnd.paystax.v1+json"); // TODO Make constant
		conn.setRequestProperty("User-Agent", "PayStax Java Client"); // TODO Add Version
		return conn;
	}

	/**
	 * Helper method to cleanup connection resources after use.
	 *
	 * @param conn the connection or null
	 * @param in the input stream or null
	 * @param out the output stream or null
	 */
	protected void cleanup(HttpURLConnection conn, InputStream in, OutputStream out) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException x) { /* do nothing */ }
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException x) { /* do nothing */ }
		}
		if (conn != null) {
			conn.disconnect();
		}
	}

	public <T> T get(String url, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, url);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			ObjectReader reader = mapper.readerForUpdating(o);
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				reader.readValue(res);
			} else {
				reader.readValue(in);
			}
			return o;
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * Sends an HTTP GET request to retrieve an object.
	 *
	 * @param url the resource URL
	 * @param clazz the type class
	 * @param <T> the type
	 * @return the object
	 * @throws IOException if an I/O error occurs
	 */
	public <T> T get(String url, Class<T> clazz) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, url);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return mapper.readValue(res, clazz);
			} else {
				return mapper.readValue(in, clazz);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * Sends an HTTP GET request to retrieve an object.
	 *
	 * @param url the resource URL
	 * @param clazz the type class
	 * @param parameterClass the parameter type class
	 * @param <T> the type
	 * @param <S> the parameter type
	 * @return the object
	 * @throws IOException if an I/O error occurs
	 */
	public <T, S> T get(String url, Class<T> clazz, Class<S> parameterClass) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, url);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			JavaType type = mapper.getTypeFactory().constructParametricType(clazz, parameterClass);
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return mapper.readValue(res, type);
			} else {
				return mapper.readValue(in, type);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * Sends an HTTP PUT request to update an object.
	 *
	 * @param url the resource URL
	 * @param o the object to be updated
	 * @param <T> the type
	 * @throws IOException if an I/O error occurs
	 */
	public <T> T update(String url, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			conn = setup(HttpMethod.GET, url);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			if (log.isDebugEnabled()) {
				String body = mapper.writeValueAsString(o);
				logRequest(conn, body);
				conn.connect();
				out = conn.getOutputStream();
				out.write(body.getBytes(Charset.forName("UTF-8")));
			} else {
				conn.connect();
				out = conn.getOutputStream();
				mapper.writeValue(out, o);
			}

			in = conn.getInputStream();
			ObjectReader reader = mapper.readerForUpdating(o);
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				reader.readValue(res);
			} else {
				reader.readValue(in);
			}
			return o;
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, out);
		}
	}

	/**
	 * Sends an HTTP POST request to create an object.
	 *
	 * @param url the resource URL
	 * @param o the object to be updated
	 * @param <T> the type
	 * @throws IOException if an I/O error occurs
	 */
	public <T> T create(String url, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			conn = setup(HttpMethod.GET, url);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			if (log.isDebugEnabled()) {
				String body = mapper.writeValueAsString(o);
				logRequest(conn, body);
				conn.connect();
				out = conn.getOutputStream();
				out.write(body.getBytes(Charset.forName("UTF-8")));
			} else {
				conn.connect();
				out = conn.getOutputStream();
				mapper.writeValue(out, o);
			}

			in = conn.getInputStream();
			ObjectReader reader = mapper.readerForUpdating(o);
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				reader.readValue(res);
			} else {
				reader.readValue(in);
			}
			return o;
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, out);
		}
	}

	/**
	 * Sends an HTTP DELETE request to delete an resource.
	 *
	 * @param url the resource URL
	 * @throws IOException if an I/O error occurs
	 */
	public void delete(String url) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.DELETE, url);
			logRequest(conn, null);
			conn.connect();
			in = conn.getInputStream();
			readString(in); // read the stream to completion
			logResponse(conn, null);
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * Sends an HTTP POST request to execute an action.
	 *
	 * @param url the URL to send the POST request to
	 * @throws IOException if an I/O error occurs
	 */
	public void execute(String url) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.POST, url);
			logRequest(conn, null);
			conn.connect();
			in = conn.getInputStream();
			readString(in); // read the stream to completion
			logResponse(conn, null);
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

}
