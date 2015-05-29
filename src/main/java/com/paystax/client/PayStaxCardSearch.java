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
package com.paystax.client;

import java.util.Date;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
public class PayStaxCardSearch extends PayStaxSearch<PayStaxCardSearch> {

	private static final long serialVersionUID = 6866724642159771144L;

	private UUID customerId;

	private String lastFourDigits;

	private CardType cardType;

	private Date expirationDateBefore;

	private Date expirationDateAfter;

	private Boolean expired;

	public UUID getCustomerId() {
		return customerId;
	}

	public PayStaxCardSearch setCustomerId(UUID customerId) {
		this.customerId = customerId;
		return this;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public PayStaxCardSearch setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
		return this;
	}

	public CardType getCardType() {
		return cardType;
	}

	public PayStaxCardSearch setCardType(CardType cardType) {
		this.cardType = cardType;
		return this;
	}

	public Date getExpirationDateBefore() {
		return expirationDateBefore;
	}

	public PayStaxCardSearch setExpirationDateBefore(Date expirationDateBefore) {
		this.expirationDateBefore = expirationDateBefore;
		return this;
	}

	public Date getExpirationDateAfter() {
		return expirationDateAfter;
	}

	public PayStaxCardSearch setExpirationDateAfter(Date expirationDateAfter) {
		this.expirationDateAfter = expirationDateAfter;
		return this;
	}

	public Boolean getExpired() {
		return expired;
	}

	public PayStaxCardSearch setExpired(Boolean expired) {
		this.expired = expired;
		return this;
	}
}
