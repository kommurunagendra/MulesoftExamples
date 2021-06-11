package com.cgi.charm.sc.tlc.persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.common.persistence.InfoImpl;

/**
 * @author shyam.avula
 *
 */
public class TlcSubscriptionInfo extends InfoImpl {
	private static final long serialVersionUID = -7258848067382018942L;
	private String subscriptionId;
	private String subscriptionName;
	private String subscriptionType;
	private boolean suppressResponse;
	private boolean allTlcs;
		
	private List<TlcSpec> tlcSpec;
	private List<Property> propertyList;
	private List<TlcDeviceSubscriptions> tlcDeviceSubscriptions;
	private Set<TlcPropertyInfo> properties;
	private Set<TlcDeviceInfo> tlcDeviceInfo;
	private String owner;
	
	public boolean isSuppressResponse() {
		return suppressResponse;
	}

	
	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public void setSuppressResponse(boolean suppressResponse) {
		this.suppressResponse = suppressResponse;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	
	public List<TlcDeviceSubscriptions> getTlcDeviceSubscriptions() {
		if (null == tlcDeviceSubscriptions) {
			tlcDeviceSubscriptions = new ArrayList<>();
		}
		return tlcDeviceSubscriptions;
	}

	public void setTlcDeviceSubscriptions(
			List<TlcDeviceSubscriptions> tlcDeviceSubscriptions) {
		this.tlcDeviceSubscriptions = tlcDeviceSubscriptions;
	}

	/**
	 * @return the tlcDeviceInfo
	 */
	public Set<TlcDeviceInfo> getTlcDeviceInfo() {
		return tlcDeviceInfo;
	}

	/**
	 * @param tlcDeviceInfo
	 *            the tlcDeviceInfo to set
	 */
	public void setTlcDeviceInfo(Set<TlcDeviceInfo> tlcDeviceInfo) {
		this.tlcDeviceInfo = tlcDeviceInfo;
	}

	/**
	 * @return the subscriptionId
	 */
	public String getSubscriptionId() {
		return subscriptionId;
	}

	/**
	 * @param subscriptionId
	 *            the subscriptionId to set
	 */
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	/**
	 * @return the subscriptionName
	 */
	public String getSubscriptionName() {
		return subscriptionName;
	}

	/**
	 * @param subscriptionName
	 *            the subscriptionName to set
	 */
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return the allTlcs
	 */
	public boolean isAllTlcs() {
		return allTlcs;
	}

	/**
	 * @param allTlcs
	 *            the allTlcs to set
	 */
	public void setAllTlcs(boolean allTlcs) {
		this.allTlcs = allTlcs;
	}

	/**
	 * @return the tlcSpec
	 */
	public List<TlcSpec> getTlcSpec() {
		if (null == tlcSpec) {
			tlcSpec = new ArrayList<>();
		}
		return tlcSpec;
	}

	/**
	 * @param tlcSpec
	 *            the tlcSpec to set
	 */
	public void setTlcSpec(List<TlcSpec> tlcSpec) {
		this.tlcSpec = tlcSpec;
	}

	/**
	 * @return the properties
	 */
	public Set<TlcPropertyInfo> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Set<TlcPropertyInfo> properties) {
		this.properties = properties;
	}

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

}
