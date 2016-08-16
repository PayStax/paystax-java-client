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
public enum PayStaxPermission {
	CREATE_ACCOUNT,
	VIEW_ACCOUNT,
	UPDATE_ACCOUNT,
	DELETE_ACCOUNT,
	CREATE_USER,
	VIEW_USER,
	UPDATE_USER,
	DELETE_USER,
	CREATE_PAYER,
	VIEW_PAYER,
	UPDATE_PAYER,
	DELETE_PAYER,
	CREATE_GATEWAY,
	VIEW_GATEWAY,
	UPDATE_GATEWAY,
	DELETE_GATEWAY,
	CREATE_PAYMENT_METHOD,
	VIEW_PAYMENT_METHOD,
	UPDATE_PAYMENT_METHOD,
	DELETE_PAYMENT_METHOD,
	VIEW_FULL_ACCOUNT_NUMBER,
	CREATE_PAYMENT,
	VIEW_PAYMENT,
	UPDATE_PAYMENT,
	EXECUTE_PAYMENT;
}
