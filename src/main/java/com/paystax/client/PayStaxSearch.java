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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Base class for all PayStax search objects. This class encapsulates paging information
 * and provides the functionality to convert a search object to a query string to be used
 * with an HTTP GET request.
 *
 * @author Erik R. Jensen
 */
public abstract class PayStaxSearch<T> implements Serializable {

	private static final long serialVersionUID = 5135491902572250515L;
	protected int page = 0;
	protected int size = 25;
	protected List<PayStaxSortBy> sort = new ArrayList<PayStaxSortBy>();

	public int getPage() {
		return page;
	}

	@SuppressWarnings("unchecked")
	public T setPage(int page) {
		this.page = page;
		return (T)this;
	}

	public int getSize() {
		return size;
	}

	@SuppressWarnings("unchecked")
	public T setSize(int size) {
		this.size = size;
		return (T)this;
	}

	public List<PayStaxSortBy> getSort() {
		return sort;
	}

	@SuppressWarnings("unchecked")
	public T setSort(List<PayStaxSortBy> sort) {
		this.sort = sort;
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T addSort(PayStaxSortBy ... sort) {
		this.sort.addAll(Arrays.asList(sort));
		return (T)this;
	}

}
