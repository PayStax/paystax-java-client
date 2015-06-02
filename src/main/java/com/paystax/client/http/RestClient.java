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

import java.io.IOException;
import java.io.Serializable;

/**
 * Contract for all HttpClient implementations.
 *
 * @author Erik R. Jensen
 */
public interface RestClient extends Serializable {

	/**
	 * Sends an HTTP GET request and loads the response into the provided object instance.
	 *
	 * @param uri the URI to GET
	 * @param instance the instance to load the response into
	 * @param <T> the instance type
	 * @return the instance passed in for convenience
	 * @throws IOException if an error occurs
	 */
	<T> T get(String uri, T instance) throws IOException;

	/**
	 * Sends an HTTP GET request and loads the response into a new instance of the provided type.
	 *
	 * @param uri the URI to GET
	 * @param clazz the type of instance to create
	 * @param <T> the type of the instance
	 * @return the newly created instance
	 * @throws IOException if an error occurs
	 */
	<T> T get(String uri, Class<T> clazz) throws IOException;

	/**
	 * Sends an HTTP GET request and loads the response into a new instance of the provided type. This
	 * method is specifically used to load instances of object which are using generics. To get around
	 * the type erasure problem, the parameterClass is used to specify the type parameter to use for the
	 * generic class.
	 *
	 * @param uri the URI to GET
	 * @param clazz the type of instance to create
	 * @param parameterClass the type parameter class
	 * @param <T> the type of the instance
	 * @param <S> the type of the type parameter
	 * @return the newly created instance
	 * @throws IOException if an error occurs
	 */
	<T, S> T get(String uri, Class<T> clazz, Class<S> parameterClass) throws IOException;

	/**
	 * Sends an HTTP PUT request to issue an update. The response is loaded back into the given object.
	 *
	 * @param uri the URI to PUT
	 * @param o the instance to update
	 * @param <T> the type of the instance
	 * @return the instance passed in for convenience
	 * @throws IOException if an I/O error occurs
	 */
	<T> T update(String uri, T o) throws IOException;

	/**
	 * Sends an HTTP POST request to create a new instance. The response is loaded back into the last
	 * parameter of the method.
	 *
	 * @param uri the URI to POST
	 * @param o the request object
	 * @param clazz the response type
	 * @param <V> the request object type
	 * @param <T> the response object type
	 * @return the populated response object
	 * @throws IOException if an error occurs
	 */
	<V, T> V create(String uri, T o, Class<V> clazz) throws IOException;

	/**
	 * Sends an HTTP POST request to create a new instance. The response is loaded back into the request
	 * object passed into the method.
	 *
	 * @param uri the URI to POST
	 * @param o the request object
	 * @param <T> the type of the request object
	 * @return the populated request object for convenience
	 * @throws IOException if an error occurs
	 */
	<T> T create(String uri, T o) throws IOException;

	/**
	 * Sends an HTTP DELETE request to delete an instance.
	 *
	 * @param uri the URL to DELETE
	 * @throws IOException if an error occurs
	 */
	void delete(String uri) throws IOException;

	/**
	 * Sends an HTTP POST request to execute an action that doesn't return a response body.
	 *
	 * @param uri the URL to POST
	 * @throws IOException if an error occurs
	 */
	void execute(String uri) throws IOException;
}
