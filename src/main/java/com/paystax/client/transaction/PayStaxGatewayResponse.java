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
