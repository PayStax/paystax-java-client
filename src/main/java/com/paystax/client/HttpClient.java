package com.paystax.client;

import java.io.IOException;
import java.io.Serializable;

/**
 * Contract for all HttpClient implementations.
 *
 * @author Erik R. Jensen
 */
public interface HttpClient extends Serializable {

	<T> T get(String url, T instance) throws IOException;

	<T> T get(String url, Class<T> clazz) throws IOException;

	<T, S> T get(String url, Class<T> clazz, Class<S> parameterClass) throws IOException;

	<T> void update(String url, T o) throws IOException;

	<T> void create(String url, T o) throws IOException;

	void delete(String url) throws IOException;
}
