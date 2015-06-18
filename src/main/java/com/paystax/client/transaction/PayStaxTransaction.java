package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.*;
import com.paystax.client.PayStaxAuditData;
import com.paystax.client.exception.PayStaxException;
import com.paystax.client.http.RestClient;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
		@Type(value = PayStaxCardAuth.class, name = "CARD_AUTH"),
		@Type(value = PayStaxCardAuthCapture.class, name = "CARD_AUTH_CAPTURE"),
		@Type(value = PayStaxCardCapture.class, name = "CARD_CAPTURE"),
		@Type(value = PayStaxCardVoid.class, name = "CARD_VOID"),
		@Type(value = PayStaxCardRefund.class, name = "CARD_REFUND"),
		@Type(value = PayStaxCardCredit.class, name = "CARD_CREDIT")})
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true)
public abstract class PayStaxTransaction<T> implements Serializable {

	private static final long serialVersionUID = 5029468493996020971L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	protected UUID id;

	@Setter(AccessLevel.NONE)
	protected PayStaxTransactionType type;

	@Setter(AccessLevel.NONE)
	protected UUID gatewayId;

	@Setter(AccessLevel.NONE)
	protected String merchantReference;

	@Setter(AccessLevel.NONE)
	protected String merchantPayerId;

	@Setter(AccessLevel.NONE)
	protected UUID payerId;

	@Setter(AccessLevel.NONE)
	protected UUID dynamicGatewayId;

	@Setter(AccessLevel.NONE)
	protected PayStaxStatus status;

	@Setter(AccessLevel.NONE)
	protected PayStaxReason reason;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	@Setter(AccessLevel.NONE)
	protected PayStaxGatewayResponse gatewayResponse;

	protected PayStaxTransaction(RestClient restClient) {
		this.restClient = restClient;
	}

	@SuppressWarnings("unchecked")
	public T save() throws IOException {
		if (id != null) {
			throw new PayStaxException("Cannot execute a transaction more than once");
		}
		restClient.create("/transactions", this);
		return (T)this;
	}

	public boolean isSuccess() {
		return status != null && status.getCode() == 1;
	}

}
