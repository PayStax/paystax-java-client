package com.paystax.client;

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
 * @author Erik R. Jensen
 */
public class LinkBuilder {

	protected String url;
	protected String query = "";
	protected List<String> queryParameters = new ArrayList<String>();
	protected List<String> pathVariables = new ArrayList<String>();

	public LinkBuilder(String url) {
		int idx = url.indexOf("{?");
		if (idx != -1) {
			String query = url.substring(idx, url.length());
			queryParameters.addAll(Arrays.asList(query.split(",")));
			this.url = url.substring(0, idx);
		} else {
			this.url = url;
		}
		idx = url.indexOf("{");
		while (idx != -1) {
			int end = url.indexOf("}", idx);
			pathVariables.add(url.substring(idx + 1, end));
			idx = url.indexOf("{", end);
		}
	}

	public LinkBuilder expand(Object ... values) {
		for (int i = 0; i < values.length; i++) {
			this.url = url.replace("{" + pathVariables.get(i) + "}", values[i].toString());
		}
		return this;
	}

	public LinkBuilder addQueryParameter(String key, Object value) {
		try {
			query += (query.isEmpty() ? "?" : "&")
					+ key + "=" + URLEncoder.encode(value.toString(), "UTF-8");
		} catch (UnsupportedEncodingException x) {
			throw new IllegalStateException(x);
		}
		return this;
	}

	public LinkBuilder addQueryParameter(Field field, Object o) {
		String fieldName = field.getName();
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			Method method = o.getClass().getMethod(methodName);
			Object val = method.invoke(o);
			if (val != null) {
				if (val instanceof Collection) {
					Collection vals = (Collection)val;
					for (Object ov : vals) {
						addQueryParameter(fieldName, ov);
					}
				} else {
					addQueryParameter(fieldName, val);
				}
			}
		} catch (NoSuchMethodException x) {
			// do nothing, skip field
		} catch (InvocationTargetException x) {
			// do nothing, skip field
		} catch (IllegalAccessException x) {
			// do nothing, skip field
		}
		return this;
	}

	public LinkBuilder addQueryParameters(Object o) {
		Class clazz = o.getClass();
		while (!clazz.equals(Object.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				if (!Modifier.isStatic(field.getModifiers())) { // ignore all static fields
					addQueryParameter(field, o);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return this;
	}

	public List<String> getPathVariables() {
		return pathVariables;
	}

	public List<String> getQueryParameters() {
		return queryParameters;
	}

	public String toString() {
		return url + query;
	}

}
