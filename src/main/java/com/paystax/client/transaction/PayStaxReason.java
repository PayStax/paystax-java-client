package com.paystax.client.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Erik R. Jensen
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayStaxReason implements Serializable {

	private static final long serialVersionUID = -1621306657442456637L;

	protected int code;
	protected String name;
	protected String description;
}
