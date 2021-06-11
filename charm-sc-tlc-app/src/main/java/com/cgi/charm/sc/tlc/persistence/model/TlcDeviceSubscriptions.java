package com.cgi.charm.sc.tlc.persistence.model;

import org.springframework.stereotype.Component;

import com.cgi.charm.common.persistence.InfoImpl;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcDeviceSubscriptions extends InfoImpl {

	private static final long serialVersionUID = -6824185637396068313L;
	private String tlcId;
	private String subscriptionId;

	public String getTlcId() {
		return tlcId;
	}

	public void setTlcId(String tlcId) {
		this.tlcId = tlcId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}
