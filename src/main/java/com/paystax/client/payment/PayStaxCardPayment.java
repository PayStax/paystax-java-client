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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.PayStaxAddress;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, exclude = {"securityCode", "accountNumber"})
@ToString(callSuper = true, exclude = {"securityCode", "accountNumber"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD")
public class PayStaxCardPayment extends PayStaxPayment<PayStaxCardPayment> {

	private static final long serialVersionUID = 7362484141307541297L;

	@Setter(AccessLevel.NONE)
	protected String cardType;
	protected String accountNumber;
	protected String securityCode;
	private String expMonth;
	private String expYear;
	private String cardholderName;
	private PayStaxAddress address;

	@Setter(AccessLevel.NONE)
	protected String lastFourDigits;

	public PayStaxCardPayment setCardType(String cardType) {
		this.cardType = cardType != null ? cardType.toUpperCase() : null;
		return this;
	}

	public PayStaxCardPayment setCardType(PayStaxCardType cardType) {
		this.cardType = cardType != null ? cardType.toString() : null;
		return this;
	}

	public PayStaxCardPayment(RestClient restClient) {
		super(restClient);
		this.type = PayStaxPaymentType.CARD;
	}

}
