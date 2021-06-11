package com.cgi.charm.sc.tlc.persistence.model;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.cgi.charm.common.persistence.InfoImpl;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcPropertyInfo extends InfoImpl {
	private static final long serialVersionUID = 6331395633736170885L;
	private String key;
	private String value;
	private Set<TlcSubscriptionInfo> tlcSubscriptionInfo;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<TlcSubscriptionInfo> getTlcSubscriptionInfo() {
		return tlcSubscriptionInfo;
	}

	public void setTlcSubscriptionInfo(
			Set<TlcSubscriptionInfo> tlcSubscriptionInfo) {
		this.tlcSubscriptionInfo = tlcSubscriptionInfo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
