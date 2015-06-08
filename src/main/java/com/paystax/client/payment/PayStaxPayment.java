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
package com.paystax.client.payment;

import com.fasterxml.jackson.annotation.*;
import com.paystax.client.PayStaxAuditData;
import com.paystax.client.http.RestClient;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
		@JsonSubTypes.Type(value = PayStaxCardPayment.class, name = "CARD")})
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true)
public abstract class PayStaxPayment<T extends PayStaxPayment> implements Serializable {

	private static final long serialVersionUID = -6981071149271621814L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	protected UUID id;

	@Setter(AccessLevel.NONE)
	protected PayStaxPaymentType type;

	@Setter(AccessLevel.NONE)
	protected UUID gatewayId;

	@Setter(AccessLevel.NONE)
	protected BigDecimal amount;

	@Setter(AccessLevel.NONE)
	protected String currency;

	@Setter(AccessLevel.NONE)
	protected String merchantReference;

	@Setter(AccessLevel.NONE)
	protected PayStaxPaymentStatus status;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	protected PayStaxPaymentAction action;

	protected PayStaxPayment() {}

	protected PayStaxPayment(RestClient restClient) {
		this.restClient = restClient;
	}

	@SuppressWarnings("unchecked")
	public T setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T setAmount(BigDecimal amount) {
		this.amount = amount;
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T setCurrency(String currency) {
		this.currency = currency;
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
		return (T)this;
	}

	@JsonIgnore
	public PayStaxAuditData getAuditData() {
		return this.auditData;
	}

	@SuppressWarnings("unchecked")
	public T save() throws IOException {
		if (id == null) { // New payment
			restClient.create("/payments", this);
		} else { // Update payment
			restClient.update("/payments/" + id, this);
		}
		this.action = null; // clear out the action if there was one
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T refresh() throws IOException {
		return (T)restClient.get("/payments/" + id, this);
	}

}
