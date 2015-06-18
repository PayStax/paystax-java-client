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
import java.math.BigDecimal;
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
@JsonTypeName("CARD_CAPTURE")
public class PayStaxCardCapture extends PayStaxTransaction<PayStaxCardCapture> implements Serializable {

	private static final long serialVersionUID = 8672465623896501174L;

	public PayStaxCardCapture(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.CARD_CAPTURE;
	}

	protected UUID priorTransaction;
	protected BigDecimal amount;
}
