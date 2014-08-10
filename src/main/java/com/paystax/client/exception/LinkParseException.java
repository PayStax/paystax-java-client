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

/**
 * Thrown by LinkBuilder when it cannot parse a URL template.
 *
 * @author Erik R. Jensen
 */
public class LinkParseException extends RuntimeException {

	private static final long serialVersionUID = -8033483910469939757L;

	public LinkParseException(String message) {
		super(message);
	}
}