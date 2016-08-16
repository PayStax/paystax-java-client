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
package com.paystax.client.transaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Erik R. Jensen
 */
@Data
public class PayStaxGatewayResponse implements Serializable {

	private static final long serialVersionUID = 6239443112839921006L;

	@Setter(AccessLevel.NONE)
	private String transactionId;

	@Setter(AccessLevel.NONE)
	private String authCode;

	@Setter(AccessLevel.NONE)
	private String statusCode;

	@Setter(AccessLevel.NONE)
	private String statusText;

	@Setter(AccessLevel.NONE)
	private String reasonCode;

	@Setter(AccessLevel.NONE)
	private String reasonText;

	@Setter(AccessLevel.NONE)
	private String avsResponseCode;

	@Setter(AccessLevel.NONE)
	private String avsResponseText;

	@Setter(AccessLevel.NONE)
	private String cvvResponseCode;

	@Setter(AccessLevel.NONE)
	private String cvvResponseText;
}
