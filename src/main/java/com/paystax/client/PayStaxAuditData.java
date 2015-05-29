package com.paystax.client;

import java.util.Date;

/**
 * @author Erik R. Jensen
 */
public class PayStaxAuditData {

	protected String lastModifiedBy;
	protected Date lastModified;
	protected String createdBy;
	protected Date created;

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreated() {
		return created;
	}

}
