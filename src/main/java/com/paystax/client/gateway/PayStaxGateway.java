package com.paystax.client.gateway;

import com.fasterxml.jackson.annotation.*;
import com.paystax.client.PayStaxAuditData;
import com.paystax.client.http.RestClient;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
		@JsonSubTypes.Type(value = PayStaxPayvisionGateway.class, name = "PAYVISION")})
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true)
public abstract class PayStaxGateway<T extends PayStaxGateway> implements Serializable {

	private static final long serialVersionUID = 8518874465789854110L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	protected UUID id;

	@Setter(AccessLevel.NONE)
	protected PayStaxGatewayType type;

	@Setter(AccessLevel.NONE)
	protected String name;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	protected PayStaxGateway() {}

	protected PayStaxGateway(RestClient restClient) {
		this.restClient = restClient;
	}

	@SuppressWarnings("unchecked")
	public T setName(String name) {
		this.name = name;
		return (T)this;
	}

	@JsonIgnore
	public PayStaxAuditData getAuditData() {
		return auditData;
	}

	@SuppressWarnings("unchecked")
	public T save() throws IOException {
		if (id == null) { // New gateway
			restClient.create("/gateways", this);
		} else { // Update gateway
			restClient.update("/gateways/" + id, this);
		}
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T refresh() throws IOException {
		return (T)restClient.get("/gateways/" + id, this);
	}
}
