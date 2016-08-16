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
package com.paystax.client.paymentmethod;

import com.fasterxml.jackson.annotation.*;
import com.paystax.client.LinkedResource;
import com.paystax.client.LinkedResourceAdapter;
import com.paystax.client.PayStaxAuditData;
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
@EqualsAndHashCode(exclude = {"restClient"}, callSuper = false)
@ToString(exclude = {"restClient"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
		@Type(value = PayStaxACHPaymentMethod.class, name = "ACH"),
		@Type(value = PayStaxCardPaymentMethod.class, name = "CARD"),
		@Type(value = PayStaxSEPAPaymentMethod.class, name = "SEPA")
})
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true)
public abstract class PayStaxPaymentMethod<T> extends LinkedResourceAdapter implements Serializable {

	private static final long serialVersionUID = 5359948518470475392L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	@Setter(AccessLevel.NONE)
	protected UUID id;

	@Setter(AccessLevel.NONE)
	protected PayStaxPaymentMethodType type;

	@Setter(AccessLevel.NONE)
	protected UUID payerId;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	protected PayStaxPaymentMethod(RestClient restClient) {
		this.restClient = restClient;
	}

	@SuppressWarnings("unchecked")
	public T save() throws IOException {
		if (id == null) { // New
			restClient.create("/payment_methods", this);
		} else { // Update
			restClient.update("/payment_methods/" + id, this);
		}
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T refresh() throws IOException {
		return restClient.get("/payment_methods/" + id, (T)this);
	}

}
