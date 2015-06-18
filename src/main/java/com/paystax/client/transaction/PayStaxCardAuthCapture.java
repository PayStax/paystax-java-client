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
package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.PayStaxAddress;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD_AUTH_CAPTURE")
public class PayStaxCardAuthCapture extends PayStaxTransaction<PayStaxCardAuthCapture> implements Serializable {

	private static final long serialVersionUID = 3593771791073880622L;

	public PayStaxCardAuthCapture(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.CARD_AUTH_CAPTURE;
	}

	protected UUID priorTransaction;
	protected BigDecimal amount;
	protected String currency;
	protected PayStaxCardType cardType;
	protected String accountNumber;

	@Setter(AccessLevel.NONE)
	protected String lastFourDigits;

	protected String securityCode;
	protected String expMonth;
	protected String expYear;
	protected String cardholderName;
	protected PayStaxAddress address;


	public PayStaxCardAuthCapture setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
		return this;
	}

	public PayStaxCardAuthCapture setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
		return this;
	}

	public PayStaxCardAuthCapture setMerchantPayerId(String merchantPayerId) {
		this.merchantPayerId = merchantPayerId;
		return this;
	}

	public PayStaxCardAuthCapture setPayerId(UUID payerId) {
		this.payerId = payerId;
		return this;
	}
}
