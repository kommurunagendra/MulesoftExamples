/**
 * 
 */
package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionType;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.util.TlcUtil;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcStatusSubscriptionMapper implements PersistenceMapper<TlcSubscriptionInfo, SubscriptionConfig> {

	/*
	 * @Autowired private TlcPropertyInfoMapper tlcPropertyInfoMapper;
	 */
	@Autowired
	private TlcSpecMapper tlcSpecMapper;
	@Autowired
	private TlcSubscriptionAttrMapper tlcSubscriptionAttrMapper;
	@Autowired
	private TlcSubscriptionRefMapper tlcSubscriptionRefMapper;
	@Autowired
	private TlcPropertyInfoMapper tlcPropertyInfoMapper;

	/**
	 * map the SubscriptionConfig
	 * 
	 * @param subscriptionInfo
	 *            SubscriptionInfo
	 * @return SubscriptionTlc
	 */
	public SubscriptionConfig mapFromPersistenceDataStore(TlcSubscriptionInfo subscriptionInfo) {
		SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
		subscriptionConfig.setAllTlcs(subscriptionInfo.isAllTlcs());
		subscriptionConfig.setSubscriptionId(new Uuid(subscriptionInfo.getSubscriptionId()));
		subscriptionConfig.setSubscriptionAttr(tlcSubscriptionAttrMapper.toCdm(subscriptionInfo));
		subscriptionConfig.setSubscriptionRef(tlcSubscriptionRefMapper.toCdm(subscriptionInfo));
		subscriptionConfig.setOwner(subscriptionInfo.getOwner());
		subscriptionConfig.setTlcSpec(subscriptionInfo.getTlcSpec());
		return subscriptionConfig;
	}

	@Override
	public TlcSubscriptionInfo toCdm(SubscriptionConfig subscriptionConfig) {
		TlcSubscriptionInfo subscriptionInfo = new TlcSubscriptionInfo();
		subscriptionInfo.setSubscriptionId(subscriptionConfig.getSubscriptionId().getUuid());
		subscriptionInfo.setSubscriptionType(SubscriptionType.STATUS.value());
		if(subscriptionConfig.getSubscriptionAttr().getSuppressResponse() !=null){
		subscriptionInfo.setSuppressResponse(subscriptionConfig.getSubscriptionAttr().getSuppressResponse());
		}
		subscriptionInfo.setSubscriptionName(subscriptionConfig.getSubscriptionAttr().getSubscriptionName());
		subscriptionInfo.setAllTlcs(TlcUtil.getPrimitiveBooleanValue(subscriptionConfig.getAllTlcs()));
		subscriptionInfo.setOwner(subscriptionConfig.getOwner());
		if (subscriptionConfig.getSubscriptionRef() != null
				&& subscriptionConfig.getSubscriptionRef().getProperty().size() > 0) {
			subscriptionInfo.setProperties(
					new HashSet<>(tlcPropertyInfoMapper.toCdm(subscriptionConfig.getSubscriptionRef().getProperty())));
		}
		/*
		 * subscriptionInfo.setProperties(new HashSet<>(tlcPropertyInfoMapper
		 * .map(subscriptionTlc.getSubscriptionRef().getProperty())));
		 */

		if (!subscriptionInfo.isAllTlcs()) {
			subscriptionInfo.getTlcDeviceSubscriptions().addAll(tlcSpecMapper
					.mapTlcSpec(subscriptionConfig.getSubscriptionId().getUuid(), subscriptionConfig.getTlcSpec()));
			subscriptionInfo.getTlcSpec().addAll(subscriptionConfig.getTlcSpec());
		}
		return subscriptionInfo;
	}
}
