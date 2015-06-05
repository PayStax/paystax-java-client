package com.paystax.client.gateway;

/**
 * @author Erik R. Jensen
 */
public enum PayStaxGatewayType {

	PAYVISION(PayStaxPayvisionGateway.class);

	private Class<? extends PayStaxGateway> clazz;

	PayStaxGatewayType(Class<? extends PayStaxGateway> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends PayStaxGateway> getClazz() {
		return clazz;
	}
}
