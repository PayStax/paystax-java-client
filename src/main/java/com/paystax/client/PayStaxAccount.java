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
 * Holds PayStax account details.
 *
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"restClient"})
@ToString(exclude = {"restClient"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxAccount implements Serializable {

	private static final long serialVersionUID = -5354120994888961403L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected UUID id;
	protected String site;
	protected String companyName;

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	@JsonIgnore
	public PayStaxAuditData getAuditData() {
		return auditData;
	}

	public PayStaxAccount save() throws IOException {
		restClient.update("/accounts/" + id, this);
		return this;
	}

	public PayStaxAccount refresh() throws IOException {
		return restClient.get("/accounts/" + id, this);
	}
}
