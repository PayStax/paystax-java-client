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
package com.paystax.client;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Holds user details.
 *
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"password", "restClient"})
@ToString(exclude = {"password", "restClient"})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class PayStaxUserAccount implements Serializable {

	private static final long serialVersionUID = -4985093425300206829L;

	@JsonIgnore
	@JacksonInject("restClient")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected UUID id;
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;
	protected List<String> roles = new ArrayList<String>();
	protected List<String> permissions = new ArrayList<String>();

	@Setter(AccessLevel.NONE)
	protected List<String> effectivePermissions = new ArrayList<String>();

	@Setter(AccessLevel.NONE)
	protected PayStaxAuditData auditData;

	protected PayStaxUserAccount(RestClient restClient) {
		this.restClient = restClient;
	}

	public PayStaxUserAccount addRole(String role) {
		roles.add(role);
		return this;
	}

	public PayStaxUserAccount addRole(PayStaxRole role) {
		roles.add(role.toString());
		return this;
	}

	public PayStaxUserAccount addPermission(String permission) {
		permissions.add(permission);
		return this;
	}

	public PayStaxUserAccount addPermission(PayStaxPermission permission) {
		permissions.add(permission.toString());
		return this;
	}

	@JsonIgnore
	public PayStaxAuditData getAuditData() {
		return auditData;
	}

	public PayStaxUserAccount save() throws IOException {
		if (id == null) { // New user account
			restClient.create("/users", this);
		} else { // Update account
			restClient.update("/users/" + id, this);
		}
		return this;
	}

	public PayStaxUserAccount refresh() throws IOException {
		return restClient.get("/users/" + id, this);
	}

}
