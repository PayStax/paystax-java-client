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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Erik R. Jensen
 */
public class PayStaxPage<T> implements Serializable {

	private static final long serialVersionUID = 1499037857536792009L;

	@JsonIgnore
	protected PayStaxClient client;
	@JsonIgnore
	protected Class<T> clazz;

	protected List<T> content;
	protected Map<String, String> links;
	protected PayStaxPageMetadata page;

	public PayStaxPage() {}

	public PayStaxPage(PayStaxClient client, Class<T> clazz) {
		this.client = client;
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public PayStaxClient getClient() {
		return client;
	}

	public void setClient(PayStaxClient client) {
		this.client = client;
	}

	public List<T> getContent() {
		return content;
	}

	public Map<String, String> getLinks() {
		return links;
	}

	public PayStaxPageMetadata getPage() {
		return page;
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<T> next() throws IOException {
		if (links.get("next") != null) {
			PayStaxPage<T> page = client.getHttpClient().get(
					new LinkBuilder(links.get("next")).toString(),
					PayStaxPage.class,
					clazz);
			page.setClient(client);
			page.setClazz(clazz);
			return page;
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<T> prev() throws IOException {
		if (links.get("prev") != null) {
			PayStaxPage<T> page = client.getHttpClient().get(
					new LinkBuilder(links.get("prev")).toString(),
					PayStaxPage.class,
					clazz);
			page.setClient(client);
			page.setClazz(clazz);
			return page;
		}
		return this;
	}

	@Override
	public String toString() {
		return "PayStaxPage{" +
				"content=" + content +
				", links=" + links +
				", page=" + page +
				'}';
	}
}
