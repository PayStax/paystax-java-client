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

import com.paystax.client.http.QueryStringBuilder;
import com.paystax.client.http.RestClient;
import com.paystax.client.paymentmethod.PayStaxPaymentMethod;
import com.paystax.client.paymentmethod.PayStaxPaymentMethodType;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxPaymentMethodSearch extends PayStaxSearch<PayStaxPaymentMethod> implements Serializable {

	private static final long serialVersionUID = 3886823293069222591L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxPaymentMethodSearch(RestClient restClient) {
		this.restClient = restClient;
	}

	private PayStaxPaymentMethodType[] type;
	private UUID payerId;

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxPaymentMethod> search() throws IOException {
		String uri = "/payment_methods?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxPaymentMethod.class);
	}
}
