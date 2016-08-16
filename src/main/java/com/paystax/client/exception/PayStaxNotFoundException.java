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
package com.paystax.client.exception;

/**
 * @author Erik R. Jensen
 */
public class PayStaxNotFoundException extends PayStaxException {

	private static final long serialVersionUID = -5198771349467795833L;

	public PayStaxNotFoundException() {}

	public PayStaxNotFoundException(String message) {
		super(message);
	}

	public PayStaxNotFoundException(String message, Throwable t) {
		super(message, t);
	}

	public PayStaxNotFoundException(Throwable t) {
		super(t);
	}

}
