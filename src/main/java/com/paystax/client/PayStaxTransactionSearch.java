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
import com.paystax.client.transaction.PayStaxTransaction;
import com.paystax.client.transaction.PayStaxTransactionType;
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
public class PayStaxTransactionSearch extends PayStaxSearch<PayStaxTransactionSearch> implements Serializable {

	private static final long serialVersionUID = -5233448519530177735L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxTransactionSearch(RestClient restClient) {
		this.restClient = restClient;
	}

	private PayStaxTransactionType[] type;
	private String merchantReferenceEquals;
	private String merchantReferenceContains;
	private String merchantReferenceStartsWith;

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxTransaction> search() throws IOException {
		String uri = "/payments?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxTransaction.class);
	}
}
