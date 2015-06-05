package com.paystax.client.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.paystax.client.CardType;
import com.paystax.client.PayStaxAddress;
import com.paystax.client.http.RestClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Erik R. Jensen
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("CARD")
public class PayStaxCardPayment extends PayStaxPayment<PayStaxCardPayment> {

	private static final long serialVersionUID = 7362484141307541297L;

	protected CardType cardType;
	protected String accountNumber;
	protected String lastFourDigits;
	private String expMonth;
	private String expYear;
	private String cardholderName;
	private PayStaxAddress address;

	public PayStaxCardPayment(RestClient restClient) {
		super(restClient);
		this.type = PayStaxPaymentType.CARD;
	}

}
