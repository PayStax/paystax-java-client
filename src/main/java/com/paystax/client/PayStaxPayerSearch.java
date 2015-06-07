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

import com.paystax.client.http.QueryStringBuilder;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxPayerSearch extends PayStaxSearch<PayStaxPayerSearch> implements Serializable {

	private static final long serialVersionUID = 1496118278694634588L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxPayerSearch(RestClient restClient) {
		this.restClient = restClient;
	}

	protected String merchantReferenceEquals;
	protected String merchantReferenceContains;
	protected String merchantReferenceStartsWith;
	protected String firstNameEquals;
	protected String firstNameContains;
	protected String firstNameStartsWith;
	protected String lastNameEquals;
	protected String lastNameContains;
	protected String lastNameStartsWith;
	protected String fullNameEquals;
	protected String fullNameContains;
	protected String fullNameStartsWith;
	protected String emailAddressEquals;
	protected String emailAddressContains;
	protected String emailAddressStartsWith;

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxPayer> search() throws IOException {
		String uri = "/payers?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxPayer.class);
	}
}
