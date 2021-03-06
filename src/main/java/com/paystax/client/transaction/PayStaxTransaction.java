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
		@Type(value = PayStaxCardCredit.class, name = "CARD_CREDIT"),
		@Type(value = PayStaxSEPA.class, name = "SEPA"),
		@Type(value = PayStaxSEPAAuth.class, name = "SEPA_AUTH"),
		@Type(value = PayStaxSEPADebit.class, name = "SEPA_DEBIT"),
		@Type(value = PayStaxSEPADebitAuth.class, name = "SEPA_DEBIT_AUTH"),
		@Type(value = PayStaxSEPACredit.class, name = "SEPA_CREDIT"),
		@Type(value = PayStaxSEPARefund.class, name = "SEPA_REFUND"),
		@Type(value = PayStaxSEPAVoid.class, name = "SEPA_VOID")})
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
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
	@JsonTypeId
	protected PayStaxTransactionType type;

	// TODO This doesn't belong on this parent type
	@Setter(AccessLevel.NONE)
	protected UUID gatewayId;

	@Setter(AccessLevel.NONE)
	protected String merchantReference;

	@Setter(AccessLevel.NONE)
	protected String merchantPayerId;

	@Setter(AccessLevel.NONE)
	protected UUID payerId;

	// TODO This doesn't belong on this parent type
	@Setter(AccessLevel.NONE)
	protected UUID dynamicGatewayId;

	@Setter(AccessLevel.NONE)
	protected PayStaxStatus status;

	@Setter(AccessLevel.NONE)
	protected PayStaxReason reason;

	// TODO We should not be sending this in our JSON to the server
	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	// TODO We should not be sending this in our JSON to the server
	@Setter(AccessLevel.NONE)
	protected PayStaxGatewayResponse gatewayResponse;

	protected PayStaxTransaction(RestClient restClient) {
		this.restClient = restClient;
	}

	@SuppressWarnings("unchecked")
	public T setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T save() throws IOException {
		if (id != null) {
			throw new PayStaxException("Cannot execute a transaction more than once");
		}
		restClient.create("/transactions", this);
		return (T)this;
	}

	@JsonIgnore
	public boolean isSuccess() {
		return status != null && status.getCode() == 1;
	}

}
