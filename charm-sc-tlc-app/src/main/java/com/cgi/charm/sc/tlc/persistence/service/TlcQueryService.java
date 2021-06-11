/**
 * 
 */
package com.cgi.charm.sc.tlc.persistence.service;

import java.util.List;

import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

/**
 * @author shyam.avula
 *
 */
public interface TlcQueryService {

	/**
	 * TlcDeviceInfo
	 * 
	 * @param tlcId
	 *            TlcId
	 * @return Object object
	 */
	TlcDeviceInfo getTlcById(String tlcId);

	/**
	 * TlcSubscriptionInfo
	 * 
	 * @param subscriptionId
	 *            SubscriptionId
	 * @return Object
	 */
	TlcSubscriptionInfo getSubscriptionById(String subscriptionId);

	/**
	 * @return List
	 */
	List<TlcDeviceInfo> getAllTlcs();

	/**
	 * @return List
	 */
	List<TlcSubscriptionInfo> getAllSubscriptions();

	/*
	 * List<TlcSubscriptionInfo> getAllSubscriptionsByType(String
	 * subscriptionType);
	 */

	/**
	 * @return boolean
	 *//*
		 * boolean isInventoryEmpty();
		 */

}
