package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.http.RestClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@JsonTypeName("SEPA_DEBIT_AITH")
public class PayStaxSEPADebitAuth extends PayStaxTransaction<PayStaxSEPADebitAuth> {

	private static final long serialVersionUID = 2857286769031205687L;

	protected UUID priorTransaction;
	protected BigDecimal amount;

	public PayStaxSEPADebitAuth(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.SEPA_DEBIT_AUTH;
	}
}
