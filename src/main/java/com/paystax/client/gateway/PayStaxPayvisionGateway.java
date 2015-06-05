package com.paystax.client.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.http.RestClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("PAYVISION")
public class PayStaxPayvisionGateway extends PayStaxGateway<PayStaxPayvisionGateway> {

	private static final long serialVersionUID = 339363027799799274L;

	private String url;
	private String queryUrl;
	private String username;
	private String password;

	public PayStaxPayvisionGateway(RestClient restClient) {
		super(restClient);
		this.type = PayStaxGatewayType.PAYVISION;
	}
}
