package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.http.RestClient;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, exclude = {"accountNumber"})
@ToString(callSuper = true, exclude = {"accountNumber"})
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD_CREDIT")
public class PayStaxCardCredit extends PayStaxTransaction<PayStaxCardCredit> implements Serializable {

	private static final long serialVersionUID = -2573569865576599651L;

	public PayStaxCardCredit(RestClient restClient) {
		super(restClient);
		this.type = PayStaxTransactionType.CARD_CREDIT;
	}

	protected BigDecimal amount;
	protected String currency;
	protected String accountNumber;

	@Setter(AccessLevel.NONE)
	protected String lastFourDigits;

	protected String expMonth;
	protected String expYear;
	protected String cardholderName;

	public PayStaxCardCredit setGatewayId(UUID gatewayId) {
		this.gatewayId = gatewayId;
		return this;
	}

	public PayStaxCardCredit setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
		return this;
	}

	public PayStaxCardCredit setMerchantPayerId(String merchantPayerId) {
		this.merchantPayerId = merchantPayerId;
		return this;
	}

	public PayStaxCardCredit setPayerId(UUID payerId) {
		this.payerId = payerId;
		return this;
	}

}
