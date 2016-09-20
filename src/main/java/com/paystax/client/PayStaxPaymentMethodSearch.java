/**
 *    Copyright 2013-2016 PayStax, LLC
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
import com.paystax.client.paymentmethod.PayStaxPaymentMethodType;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxPaymentMethodSearch<T> extends PayStaxSearch<T> implements Serializable {

	private static final long serialVersionUID = 3886823293069222591L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;
	private Class<T> clazz;

	protected PayStaxPaymentMethodSearch(RestClient restClient,Class<T> clazz) {
		this.restClient = restClient;
		this.clazz = clazz;
	}

	private PayStaxPaymentMethodType[] type;
	private List<UUID> payerId;
	private List<UUID> paymentMethodId;

	public PayStaxPaymentMethodSearch addPayerId(UUID ... ids) {
		if (payerId == null) {
			payerId = new ArrayList<>(ids.length);
		}
		payerId.addAll(Arrays.asList(ids));
		return this;
	}

	public PayStaxPaymentMethodSearch addPaymentMethodId(UUID ... ids) {
		if (paymentMethodId == null) {
			paymentMethodId = new ArrayList<>(ids.length);
		}
		paymentMethodId.addAll(Arrays.asList(ids));
		return this;
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<T> search() throws IOException {
		String uri = "/payment_methods?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, clazz);
	}
}
