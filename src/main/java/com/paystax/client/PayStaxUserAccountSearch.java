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
 * Holds search criteria used to find users.
 *
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxUserAccountSearch extends PayStaxSearch<PayStaxUserAccountSearch> implements Serializable {

	private static final long serialVersionUID = -6796555244109344478L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxUserAccountSearch(RestClient restClient) {
		this.restClient = restClient;
	}

	private String usernameEquals;
	private String usernameStartsWith;
	private String usernameContains;
	private String firstNameEquals;
	private String firstNameStartsWith;
	private String firstNameContains;
	private String lastNameEquals;
	private String lastNameStartsWith;
	private String lastNameContains;
	private String emailAddressEquals;
	private String emailAddressStartsWith;
	private String emailAddressContains;

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxUserAccount> search() throws IOException {
		String uri = "/accounts?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxUserAccount.class);
	}
}
