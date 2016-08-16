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

/**
 * @author Erik R. Jensen
 */
public class PayStaxSortBy {

	private String field;
	private PayStaxSortDirection direction;

	public PayStaxSortBy() {}

	public PayStaxSortBy(String field, PayStaxSortDirection direction) {
		this.field = field;
		this.direction = direction;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(field);
		if (direction != null) {
			sb.append(",").append(direction.toString().toLowerCase());
		}
		return sb.toString();
	}
}
