package com.cgi.charm.sc.tlc.persistence.service;

import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

/**
 * @author shyam.avula
 *
 */
public interface TlcCommandService {

	/**
	 * addTlcDevice
	 * 
	 * @param tlcDeviceInfo
	 *            TlcDeviceInfo
	 */
	void addTlcDevice(TlcDeviceInfo tlcDeviceInfo);

	/**
	 * addSubscription
	 * 
	 * @param subscriptionInfo
	 *            SubscriptionInfo
	 */
	void addSubscription(TlcSubscriptionInfo subscriptionInfo);

	/**
	 * removeTlcDevice
	 * 
	 * @param tlcDeviceInfo
	 *            TlcDeviceInfo
	 */
	void removeTlcDevice(TlcDeviceInfo tlcDeviceInfo);

	/* void removeSubscription(TlcSubscriptionInfo subscriptionInfo); */

	/**
	 * removeTlcById
	 * 
	 * @param tlcId
	 *            TlcId
	 */
	void removeTlcById(String tlcId);

	/**
	 * removeSubscriptionById
	 * 
	 * @param subscriptionId
	 *            SubscriptionId
	 */
	void removeSubscriptionById(String subscriptionId);

	/**
	 * removeAllTlcs remove all tlc devices from the database
	 */
	void removeAllTlcs();

	/**
	 * @param subscriptionType
	 */
	/* void removeAllSubscriptionsByType(String subscriptionType); */

}
