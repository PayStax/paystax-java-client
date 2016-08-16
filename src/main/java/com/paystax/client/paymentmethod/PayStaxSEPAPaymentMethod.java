/**
 *    Copyright 2015-2016 PayStax, LLC
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.http.RestClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
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
@JsonTypeName("SEPA")
public class PayStaxSEPAPaymentMethod extends PayStaxPaymentMethod<PayStaxSEPAPaymentMethod> {

	private static final long serialVersionUID = 8683929547504544700L;

	public PayStaxSEPAPaymentMethod(RestClient restClient) {
		super(restClient);
		this.type = PayStaxPaymentMethodType.SEPA;
	}

	private String firstName;
	private String lastName;
	private String bic;
	private String iban;
	private String mandateId;
	private Date signatureDate;
	private PayStaxSEPAMandateContentType mandateType;
	private byte[] mandate;
	private String creditorId;

	public PayStaxSEPAPaymentMethod setPayerId(UUID payerId) {
		this.payerId = payerId;
		return this;
	}

}
