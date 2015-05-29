package com.paystax.client;

import com.paystax.client.http.QueryStringBuilder;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Erik R. Jensen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayStaxAccountSearch extends PayStaxSearch<PayStaxAccountSearch> implements Serializable {

	private static final long serialVersionUID = -9163637814523272606L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected RestClient restClient;

	protected PayStaxAccountSearch(RestClient restClient) {
		this.restClient = restClient;
	}

	private String siteEquals;
	private String siteStartsWith;
	private String siteContains;
	private String companyNameContains;
	private String companyNameStartsWith;

	@SuppressWarnings("unchecked")
	public PayStaxPage<PayStaxAccount> search() throws IOException {
		String uri = "/accounts?" + new QueryStringBuilder().add(this).toQueryString();
		return restClient.get(uri, PayStaxPage.class, PayStaxAccount.class);
	}
}
