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
package com.paystax.client.http;

import com.paystax.client.PayStaxError;
import com.paystax.client.exception.*;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * A simple HTTP client used to send get, update and create requests. This class uses
 * HttpURLConnection internally.
 *
 * @author Erik R. Jensen
 */
@Slf4j
public class URLConnectionRestClient extends JacksonRestClient implements Serializable {

	private static final long serialVersionUID = -5275552394410711694L;
	protected String authorization;
	protected String url;

	/**
	 * Creates a new HTTP client.
	 *
	 * @param username the PayStax username
	 * @param password the PayStax password
	 */
	public URLConnectionRestClient(String url, String username, String password) {
		authorization = username + ":" + password;
		authorization = "Basic " + DatatypeConverter.printBase64Binary(authorization.getBytes(Charset.forName("UTF-8")));
		this.url = url;
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
	@SuppressWarnings("unchecked")
	protected IOException getError(IOException x, HttpURLConnection conn) throws IOException {
		if (conn != null) {
			String error = readError(conn);
			if (log.isDebugEnabled()) {
				logResponse(conn, error);
			}
			int status = conn.getResponseCode();
			switch (status) {
				case 401:
					return new PayStaxAuthenticationFailedException(error);
				case 404:
					return new PayStaxNotFoundException(error);
				case 400:
					if (error != null) {
						return new PayStaxBadRequestException("Received error: " + error,
								readValue(error, List.class, PayStaxError.class));
					}
					throw new PayStaxBadRequestException();
				case 403:
					return new PayStaxForbiddenException(error); // TODO
				default:
					return new PayStaxException(error, x);
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
		if (!url.startsWith("http")) {
			url = this.url + url;
		}
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Authorization", authorization);
		conn.setRequestProperty("Accept", "application/vnd.paystax.v1+json"); // TODO Make constant
		conn.setRequestProperty("User-Agent", "PayStax Java Client"); // TODO Add Version
		conn.setRequestMethod(method.toString());
		conn.setConnectTimeout(120 * 1000); // 2 minutes // TODO Make configurable
		conn.setReadTimeout(120 * 1000); // 2 minutes // TODO Make configurable
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

	/**
	 * {@inheritDoc}
	 */
	public <T> T get(String uri, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, uri);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return readValue(res, o);
			} else {
				return readValue(in, o);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T get(String uri, Class<T> clazz) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, uri);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return readValue(res, clazz);
			} else {
				return readValue(in, clazz);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T, S> T get(String uri, Class<T> clazz, Class<S> parameterClass) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.GET, uri);
			if (log.isDebugEnabled()) {
				logRequest(conn, null);
			}
			conn.connect();
			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return readValue(res, clazz, parameterClass);
			} else {
				return readValue(in, clazz, parameterClass);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T update(String uri, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			conn = setup(HttpMethod.PUT, uri);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			if (log.isDebugEnabled()) {
				String body = writeValue(o);
				logRequest(conn, body);
				conn.connect();
				out = conn.getOutputStream();
				out.write(body.getBytes(Charset.forName("UTF-8")));
			} else {
				conn.connect();
				out = conn.getOutputStream();
				writeValue(out, o);
			}

			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				readValue(res, o);
			} else {
				readValue(in, o);
			}
			return o;
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, out);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <V, T> V create(String uri, T o, Class<V> clazz) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			conn = setup(HttpMethod.POST, uri);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			if (log.isDebugEnabled()) {
				String body = writeValue(o);
				logRequest(conn, body);
				conn.connect();
				out = conn.getOutputStream();
				out.write(body.getBytes(Charset.forName("UTF-8")));
			} else {
				conn.connect();
				out = conn.getOutputStream();
				writeValue(out, o);
			}

			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return readValue(res, clazz);
			} else {
				return readValue(in, clazz);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, out);
		}
	}

	@Override
	public <T> T create(String uri, T o) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			conn = setup(HttpMethod.POST, uri);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			if (log.isDebugEnabled()) {
				String body = writeValue(o);
				logRequest(conn, body);
				conn.connect();
				out = conn.getOutputStream();
				out.write(body.getBytes(Charset.forName("UTF-8")));
			} else {
				conn.connect();
				out = conn.getOutputStream();
				writeValue(out, o);
			}

			in = conn.getInputStream();
			if (log.isDebugEnabled()) {
				String res = readString(in);
				logResponse(conn, res);
				return readValue(res, o);
			} else {
				return readValue(in, o);
			}
		} catch (IOException x) {
			throw getError(x, conn);
		} finally {
			cleanup(conn, in, out);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String uri) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.DELETE, uri);
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
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String uri) throws IOException {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			conn = setup(HttpMethod.POST, uri);
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
