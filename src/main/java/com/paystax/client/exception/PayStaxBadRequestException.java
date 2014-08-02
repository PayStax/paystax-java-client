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
package com.paystax.client.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik R. Jensen
 */
public class PayStaxBadRequestException extends PayStaxException {

	private static final long serialVersionUID = -7372223617979515476L;

	protected List<Error> errors = new ArrayList<Error>();

	public PayStaxBadRequestException() {}

	public PayStaxBadRequestException(String message) {
		super(message);
	}

	public PayStaxBadRequestException(String message, List<Error> errors) {
		super(message);
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
