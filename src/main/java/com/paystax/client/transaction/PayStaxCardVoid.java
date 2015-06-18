package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.http.RestClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD_VOID")
public class PayStaxCardVoid extends PayStaxTransaction<PayStaxCardVoid> implements Serializable {

	private static final long serialVersionUID = 6900139804564398107L;

	public PayStaxCardVoid(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.CARD_VOID;
	}

	protected UUID priorTransaction;
}
