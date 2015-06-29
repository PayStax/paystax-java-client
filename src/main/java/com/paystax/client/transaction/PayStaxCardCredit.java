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
package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
@EqualsAndHashCode(callSuper = true, exclude = {"accountNumber"})
@ToString(callSuper = true, exclude = {"accountNumber"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD_CREDIT")
public class PayStaxCardCredit extends PayStaxTransaction<PayStaxCardCredit> implements Serializable {

	private static final long serialVersionUID = -2573569865576599651L;

	public PayStaxCardCredit(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.CARD_CREDIT;
	}

	protected BigDecimal amount;
	protected String currency;
	protected String accountNumber;

	@Setter(AccessLevel.NONE)
	protected String lastFourDigits;

	protected String expMonth;
	protected String expYear;
	protected String cardholderName;

	public PayStaxCardCredit setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
		return this;
	}

	public PayStaxCardCredit setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
		return this;
	}

	public PayStaxCardCredit setMerchantPayerId(String merchantPayerId) {
		this.merchantPayerId = merchantPayerId;
		return this;
	}

	public PayStaxCardCredit setPayerId(UUID payerId) {
		this.payerId = payerId;
		return this;
	}

}
