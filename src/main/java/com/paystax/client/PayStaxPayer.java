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
package com.paystax.client;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Holds payer information.
 *
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class PayStaxPayer implements Serializable {

	private static final long serialVersionUID = 7925218385785128677L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected UUID id;
	protected String merchantReference;
	protected String firstName;
	protected String lastName;
	protected String fullName;
	protected String emailAddress;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	protected PayStaxPayer(RestClient restClient) {
		this.restClient = restClient;
	}

	@JsonIgnore
	public PayStaxAuditData getAuditData() {
		return auditData;
	}

	public PayStaxPayer save() throws IOException {
		if (id == null) { // New payer
			restClient.create("/payers", this);
		} else { // Update payer
			restClient.update("/payers/" + id, this);
		}
		return this;
	}

	public PayStaxPayer refresh() throws IOException {
		return restClient.get("/payers/" + id, this);
	}
}
