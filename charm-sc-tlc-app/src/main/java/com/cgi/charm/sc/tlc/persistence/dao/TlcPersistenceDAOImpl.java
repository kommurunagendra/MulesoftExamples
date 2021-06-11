/**
 * 
 */
package com.cgi.charm.sc.tlc.persistence.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cgi.charm.common.persistence.InfoImpl;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceSubscriptions;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

import jdk.nashorn.internal.parser.Lexer;

/** 
 * @author shyam.avula
 *
 */
@Repository
public class TlcPersistenceDAOImpl implements TlcPersistenceDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#addEntity(com.
	 * cgi.charm.common.persistence.InfoImpl)
	 */
	@Override
	public void addEntity(InfoImpl deviceInfo) {
		hibernateTemplate.saveOrUpdate(deviceInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#removeEntity(com
	 * .cgi.charm.common.persistence.InfoImpl)
	 */
	@Override
	public void removeEntity(InfoImpl deviceInfo) {
		hibernateTemplate.delete(deviceInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#removeTlcById(
	 * java.lang.String)
	 */
	@Override
	public void removeTlcById(String tlcId) {
		TlcDeviceInfo TlcDeviceInfoById=getTlcById(tlcId);
		if(TlcDeviceInfoById !=null){
		hibernateTemplate.delete(TlcDeviceInfoById);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#removeSubscriptionById
	 * (java.lang.String)
	 */
	@Override
	public void removeSubscriptionById(String subscriptionId) {
		hibernateTemplate.delete(getSubscriptionById(subscriptionId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#removeAllTlcs()
	 */
	@Override
	public void removeAllTlcs() {
		hibernateTemplate.deleteAll(getAllTlcs());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#
	 * removeAllSubscriptionsByType(java.lang.String)
	 */
	/*@Override
	public void removeAllSubscriptionsByType(String subscriptionType) {
		hibernateTemplate
				.deleteAll(getAllSubscriptionsByType(subscriptionType));
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#getTlcById(java
	 * .lang.String)
	 */
	@Override
	public TlcDeviceInfo getTlcById(String tlcId) {
		return hibernateTemplate.get(TlcDeviceInfo.class, tlcId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#getSubscriptionById
	 * (java.lang.String)
	 */
	@Override
	public TlcSubscriptionInfo getSubscriptionById(String subscriptionId) {
		return hibernateTemplate.get(TlcSubscriptionInfo.class, subscriptionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#getAllTlcs()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TlcDeviceInfo> getAllTlcs() {

		return (List<TlcDeviceInfo>) hibernateTemplate
				.find("from TlcDeviceInfo");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TlcSubscriptionInfo> getAllSubscriptions() {
		return (List<TlcSubscriptionInfo>) hibernateTemplate
				.find("from TlcSubscriptionInfo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#
	 * getAllSubscriptionsByType(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TlcSubscriptionInfo> getAllSubscriptionsByType(
			String subscriptionType) {
		return (List<TlcSubscriptionInfo>) hibernateTemplate.find(
				"from TlcSubscriptionInfo where subscriptionType=?",
				subscriptionType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#
	 * removeSubscriptionsForDevice(java.lang.String)
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public void removeSubscriptionsForDevice(String tlcId) {
		List<TlcDeviceSubscriptions> subscriptionsForDevice = (List<TlcDeviceSubscriptions>) hibernateTemplate
				.find("from TlcDeviceSubscriptions where tlcId=?", tlcId);
		for (TlcDeviceSubscriptions deviceSubscription : subscriptionsForDevice) {
			removeEntity(deviceSubscription);
		}
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#
	 * removeDevicesForSubscription(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeDevicesForSubscription(String subscriptionId) {
		List<TlcDeviceSubscriptions> devicesForSubscription = (List<TlcDeviceSubscriptions>) hibernateTemplate
				.find("from TlcDeviceSubscriptions where subscriptionId=?",
						subscriptionId);
		for (TlcDeviceSubscriptions deviceSubscription : devicesForSubscription) {
			removeEntity(deviceSubscription);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#
	 * getSubscriptionsForDevice(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TlcSubscriptionInfo> getSubscriptionsForDevice(String tlcId) {
		return (List<TlcSubscriptionInfo>) hibernateTemplate
				.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
						tlcId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO#getTlcForSubscription
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TlcDeviceInfo> getTlcForSubscription(String subscriptionId) {
		return (List<TlcDeviceInfo>) hibernateTemplate
				.find("from TlcDeviceInfo tdi ,TlcDeviceSubscriptions ts where ts.subscriptionId = ? AND ts.tlcId=tdi.tlcId",
						subscriptionId);
	}
}
