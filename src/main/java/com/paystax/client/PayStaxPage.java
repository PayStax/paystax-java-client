/**
 *    Copyright 2015 PayStax, LLC
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

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paystax.client.http.QueryStringBuilder;
import com.paystax.client.http.RestClient;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Erik R. Jensen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(exclude = {"links"})
public class PayStaxPage<T> implements Serializable {

	private static final long serialVersionUID = 8067470993453933199L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	protected List<T> content;

	@Setter(AccessLevel.NONE)
	protected Map<String, String> links;

	@Setter(AccessLevel.NONE)
	protected PayStaxPageMetadata page;

	public boolean hasNext() {
		return links.containsKey("next");
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<T> next() throws IOException {
		return hasNext()
				? restClient.get(links.get("next"), PayStaxPage.class, PayStaxAccount.class)
				: this;
	}

	public boolean hasPrev() {
		return links.containsKey("prev");
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<T> prev() throws IOException {
		return hasPrev()
				? restClient.get(links.get("prev"), PayStaxPage.class, PayStaxAccount.class)
				: this;
	}
}
