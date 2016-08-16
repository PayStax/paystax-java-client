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

import com.paystax.client.exception.LinkParseException;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Unit tests for LinkBuilder.
 *
 * @author Erik R. Jensen
 */
public class LinkBuilderTest {

	public static class TestType {

		private String param1;
		private String param2;

		public String getParam1() {
			return param1;
		}

		public void setParam1(String param1) {
			this.param1 = param1;
		}

		public String getParam2() {
			return param2;
		}

		public void setParam2(String param2) {
			this.param2 = param2;
		}
	}

	@Test
	public void testParse() {
		LinkBuilder lb = new LinkBuilder("http://example.com/collection/{id}{?action1,action2}");
		assertTrue(lb.getPathVariables().contains("id"));
		assertTrue(lb.getQueryParameters().contains("action1"));
		assertTrue(lb.getQueryParameters().contains("action2"));
	}

	@Test(expected = LinkParseException.class)
	public void testBadParse1() {
		new LinkBuilder("http://example.com/collection/id}{?action1,action2");
	}

	@Test(expected = LinkParseException.class)
	public void testBadParse2() {
		new LinkBuilder("http://example.com/collection/{id{?action1,action2");
	}

	@Test(expected = LinkParseException.class)
	public void testParseEmptyPathVariable() {
		new LinkBuilder("http://example.com/collection/{}");
	}

	@Test
	public void testParseEmptyQueryParameters() {
		new LinkBuilder("http://example.com/collection{?}");
	}

	@Test
	public void testExpand() {
		String url = new LinkBuilder("http://example.com/collection1/{colId1}/collection2/{colId2}")
				.expand("test1", "test2").toString();
		assertEquals("http://example.com/collection1/test1/collection2/test2", url);
	}

	@Test
	public void testAddQueryParameter() {
		String url = new LinkBuilder("http://example.com/collection{?param1,param2}")
				.addQueryParameter("param1", "test1")
				.addQueryParameter("param2", "test2").toString();
		assertEquals("http://example.com/collection?param1=test1&param2=test2", url);
	}

	@Test
	public void testAddQueryParameters() {
		TestType t = new TestType();
		t.setParam1("test1");
		t.setParam2("test2");
		String url = new LinkBuilder("http://example.com/collection{?param1,param2}")
				.addQueryParameters(t).toString();
		assertEquals("http://example.com/collection?param1=test1&param2=test2", url);
	}
}
