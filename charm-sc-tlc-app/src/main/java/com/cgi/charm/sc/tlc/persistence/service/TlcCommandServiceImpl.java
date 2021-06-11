/**
 * 
 */
package com.cgi.charm.sc.tlc.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceSubscriptions;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

/**
 * @author shyam.avula
 *
 */
@Service
@Transactional
public class TlcCommandServiceImpl implements TlcCommandService {

	@Autowired
	private TlcPersistenceDAO tlcPersistenceDAOImpl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#addTlcDevice
	 * (com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo)
	 */
	@Override
	public void addTlcDevice(TlcDeviceInfo tlcDeviceInfo) {
		tlcPersistenceDAOImpl.addEntity(tlcDeviceInfo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#addSubscription
	 * (com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo)
	 */
	@Override
	public void addSubscription(TlcSubscriptionInfo subscriptionInfo) {
		tlcPersistenceDAOImpl.addEntity(subscriptionInfo);
		for (TlcDeviceSubscriptions deviceSubscription : subscriptionInfo
				.getTlcDeviceSubscriptions()) {
			tlcPersistenceDAOImpl.addEntity(deviceSubscription);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#removeTlcDevice
	 * (com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo)
	 */
	@Override
	public void removeTlcDevice(TlcDeviceInfo tlcDeviceInfo) {
		tlcPersistenceDAOImpl.removeEntity(tlcDeviceInfo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#removeSubscription
	 * (com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo)
	 */
	/*@Override
	public void removeSubscription(TlcSubscriptionInfo subscriptionInfo) {
		tlcPersistenceDAOImpl.removeEntity(subscriptionInfo);
		for (TlcDeviceSubscriptions deviceSubscription : subscriptionInfo
				.getTlcDeviceSubscriptions()) {
			tlcPersistenceDAOImpl.removeEntity(deviceSubscription);
		}
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#removeTlcById
	 * (java.lang.String)
	 */
	@Override
	public void removeTlcById(String tlcId) {
		tlcPersistenceDAOImpl.removeTlcById(tlcId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#
	 * removeSubscriptionById(java.lang.String)
	 */
	@Override
	public void removeSubscriptionById(String subscriptionId) {
		tlcPersistenceDAOImpl.removeSubscriptionById(subscriptionId);
		tlcPersistenceDAOImpl.removeDevicesForSubscription(subscriptionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#removeAllTlcs
	 * ()
	 */
	@Override
	public void removeAllTlcs() {
		tlcPersistenceDAOImpl.removeAllTlcs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.service.TlcCommandService#
	 * removeAllSubscriptionsByType(java.lang.String)
	 */
	/*@Override
	public void removeAllSubscriptionsByType(String subscriptionType) {
		tlcPersistenceDAOImpl.removeAllSubscriptionsByType(subscriptionType);
	}*/

}
