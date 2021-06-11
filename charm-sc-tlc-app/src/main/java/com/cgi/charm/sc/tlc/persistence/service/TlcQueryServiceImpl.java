/**
 * 
 */
package com.cgi.charm.sc.tlc.persistence.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TlcQueryServiceImpl implements TlcQueryService {

	@Autowired
	private TlcPersistenceDAO tlcPersistenceDAOImpl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcQueryService#getTlcById(java
	 * .lang.String)
	 */
	@Override
	public TlcDeviceInfo getTlcById(String tlcId) {
		TlcDeviceInfo tlcDeviceInfo = tlcPersistenceDAOImpl.getTlcById(tlcId);
		if (null != tlcDeviceInfo) {
			tlcDeviceInfo.setTlcSubscriptionInfo(new HashSet<>(
					tlcPersistenceDAOImpl.getSubscriptionsForDevice(tlcId)));
		}
		return tlcDeviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcQueryService#getSubscriptionById
	 * (java.lang.String)
	 */
	@Override
	public TlcSubscriptionInfo getSubscriptionById(String subscriptionId) {
		TlcSubscriptionInfo subscriptionInfo = tlcPersistenceDAOImpl
				.getSubscriptionById(subscriptionId);
		if (null != subscriptionInfo) {
			if (!subscriptionInfo.isAllTlcs()) {
				subscriptionInfo.setTlcDeviceInfo(new HashSet<>(
						tlcPersistenceDAOImpl
								.getTlcForSubscription(subscriptionId)));
			}
		}
		return subscriptionInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcQueryService#getAllTlcs()
	 */
	@Override
	public List<TlcDeviceInfo> getAllTlcs() {
		List<TlcDeviceInfo> tlcList = tlcPersistenceDAOImpl.getAllTlcs();
		for (TlcDeviceInfo tlcDeviceInfo : tlcList) {
			tlcDeviceInfo
					.setTlcSubscriptionInfo(new HashSet<>(
							tlcPersistenceDAOImpl
									.getSubscriptionsForDevice(tlcDeviceInfo
											.getTlcId())));
		}
		return tlcList;
	}

	@Override
	public List<TlcSubscriptionInfo> getAllSubscriptions(){
		List<TlcSubscriptionInfo> subscriptionsList = tlcPersistenceDAOImpl
				.getAllSubscriptions();

		return subscriptionsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgi.charm.sc.tlc.persistence.service.TlcQueryService#
	 * getAllSubscriptionsByType(java.lang.String)
	 */
	/*@Override
	public List<TlcSubscriptionInfo> getAllSubscriptionsByType(
			String subscriptionType) {
		List<TlcSubscriptionInfo> subscriptionList = tlcPersistenceDAOImpl
				.getAllSubscriptionsByType(subscriptionType);
		for (TlcSubscriptionInfo tlcSubscriptionInfo : subscriptionList) {
			if (null != tlcSubscriptionInfo) {
				if (!tlcSubscriptionInfo.isAllTlcs()) {
					tlcSubscriptionInfo.setTlcDeviceInfo(new HashSet<>(
							tlcPersistenceDAOImpl
									.getTlcForSubscription(tlcSubscriptionInfo
											.getSubscriptionId())));
				}
			}
		}
		return subscriptionList;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgi.charm.sc.tlc.persistence.service.TlcQueryService#isInventoryEmpty
	 * ()
	 */
	/*@Override
	public boolean isInventoryEmpty() {
		if (getAllTlcs().size() <= 0) {
			return true;
		}
		return false;
	}*/

}
