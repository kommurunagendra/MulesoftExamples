package com.cgi.charm.sc.tlc.persistence.dao;

import java.util.List;

import com.cgi.charm.common.persistence.InfoImpl;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

/**
 * @author shyam.avula
 *
 */
public interface TlcPersistenceDAO {

	/**
	 * addEntity
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	void addEntity(InfoImpl deviceInfo);

	/**
	 * removeEntity
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	void removeEntity(InfoImpl deviceInfo);

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
	 *            SubcriptionId
	 */
	void removeSubscriptionById(String subscriptionId);

	/* void removeSubscriptionsForDevice(String tlcId); */

	/**
	 * removeDevicesForSubscription
	 * 
	 * @param subscriptionId
	 *            SubscriptionID
	 */
	void removeDevicesForSubscription(String subscriptionId);

	/**
	 * removeAllTlcs remove all the tlc devices from the datastore
	 */
	void removeAllTlcs();

	/**
	 * TlcDeviceInfo
	 * 
	 * @param tlcId
	 *            TlcId
	 * @return tlcDeviceInfo DeviceInfo
	 */
	TlcDeviceInfo getTlcById(String tlcId);

	/**
	 * TlcSubscriptionInfo
	 * 
	 * @param subscriptionId
	 *            SubscriptionId
	 * @return Object returns Object
	 */
	TlcSubscriptionInfo getSubscriptionById(String subscriptionId);

	/**
	 * TlcDeviceInfo
	 * 
	 * @return List returns DeviceInfo's
	 */
	List<TlcDeviceInfo> getAllTlcs();

	/**
	 * the TlcSubscriptionInfo
	 * 
	 * @param subscriptionType
	 *            String
	 * @return List list
	 */
	List<TlcSubscriptionInfo> getAllSubscriptionsByType(String subscriptionType);

	/**
	 * the TlcSubscriptionInfo
	 * 
	 * @param tlcId
	 *            string
	 * @return List tlcSubscriptionInfo
	 */
	List<TlcSubscriptionInfo> getSubscriptionsForDevice(String tlcId);

	/**
	 * the TlcDeviceInfo
	 * 
	 * @param subscriptionId
	 *            String
	 * @return List deviceInfo
	 */
	List<TlcDeviceInfo> getTlcForSubscription(String subscriptionId);

	/**
	 * the TlcSubscriptionInfoa
	 * 
	 * @return List
	 */
	List<TlcSubscriptionInfo> getAllSubscriptions();
}
