/**
 * 
 *//*
package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.SubscriptionAttr;
import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.cdm.tlc.TlcInventorySubscription;
import com.cgi.charm.cdm.tlc.UuidType;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.util.TlcUtil;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

@Component
public class TlcInventorySubscriptionMapper implements PersistenceMapper<TlcSubscriptionInfo, TlcInventorySubscription> {
	@Autowired
	private TlcSpecMapper tlcSpecMapper;
	@Autowired
	private TlcPropertyInfoMapper tlcPropertyInfoMapper;


	public SubscriptionConfig mapFromPersistenceStore(
			TlcSubscriptionInfo tlcSubscriptionInfo) {
		SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
		subscriptionConfig.setAllTlcs(tlcSubscriptionInfo.isAllTlcs());

		if (!subscriptionConfig.getAllTlcs()) {
			subscriptionConfig.setTlcSpec(tlcSpecMapper.toCdm(new ArrayList<>(
					tlcSubscriptionInfo.getTlcDeviceInfo())));
		}
		subscriptionConfig.setSubscriptionAttr(new SubscriptionAttr(
				tlcSubscriptionInfo.getSubscriptionName(), tlcSubscriptionInfo
						.getSubscriptionFrequency()));
		subscriptionConfig.setSubscriptionId(new UuidType(tlcSubscriptionInfo
				.getSubscriptionId()));
		subscriptionConfig.setSubscriptionRef(new SubscriptionRef(
				tlcPropertyInfoMapper.mapTlcPropertyInfoList(new ArrayList<>(
						tlcSubscriptionInfo.getProperties()))));
		return subscriptionConfig;
	}

	@Override
	public TlcSubscriptionInfo toCdm(
			TlcInventorySubscription inventorySubscription) {
		SubscriptionConfig subscriptionConfig = inventorySubscription
				.getSubscriptionConfig();
		TlcSubscriptionInfo subscriptionInfo = new TlcSubscriptionInfo();
		subscriptionInfo.setSubscriptionId(subscriptionConfig
				.getSubscriptionId().getUuid());
		subscriptionInfo.setSubscriptionName(subscriptionConfig
				.getSubscriptionAttr().getSubscriptionName());
		subscriptionInfo.setSubscriptionFrequency(subscriptionConfig
				.getSubscriptionAttr().getSubscriptionFrequency());
		subscriptionInfo.setAllTlcs(TlcUtil
				.getPrimitiveBooleanValue(subscriptionConfig.getAllTlcs()));
		subscriptionInfo.setProperties(new HashSet<>(tlcPropertyInfoMapper
				.toCdm(inventorySubscription.getSubscriptionConfig()
						.getSubscriptionRef().getProperty())));
		if (!subscriptionInfo.isAllTlcs()) {
			subscriptionInfo.getTlcDeviceSubscriptions().addAll(
					tlcSpecMapper.mapTlcSpec(subscriptionInfo
							.getSubscriptionId(), inventorySubscription
							.getSubscriptionConfig().getTlcSpec()));
		}
		return subscriptionInfo;
	}
	
	@Override
	public void toDomain(TlcSubscriptionInfo cdmClass, TlcInventorySubscription inventorySubscription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TlcInventorySubscription toDomain(TlcSubscriptionInfo cdmClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toCdm(TlcInventorySubscription inventorySubscription, TlcSubscriptionInfo cdmClass) {
		// TODO Auto-generated method stub
		
	}
}
*/