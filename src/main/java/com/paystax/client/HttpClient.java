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
