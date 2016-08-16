/**
 *    Copyright 2015-2016 PayStax, LLC
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

import com.paystax.client.gateway.PayStaxGateway;
import com.paystax.client.gateway.PayStaxGatewayType;
import com.paystax.client.http.QueryStringBuilder;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxGatewaySearch extends PayStaxSearch<PayStaxGatewaySearch> implements Serializable {

	private static final long serialVersionUID = 1447292837237129379L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxGatewaySearch(RestClient restClient) {
		this.restClient = restClient;
	}

	private List<PayStaxGatewayType> type = new ArrayList<PayStaxGatewayType>();
	private String nameEquals;
	private String nameStartsWith;
	private String nameContains;

	public PayStaxGatewaySearch addType(PayStaxGatewayType type) {
		this.type.add(type);
		return this;
	}

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxGateway> search() throws IOException {
		String uri = "/gateways?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxGateway.class);
	}

}
