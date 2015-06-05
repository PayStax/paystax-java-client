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
