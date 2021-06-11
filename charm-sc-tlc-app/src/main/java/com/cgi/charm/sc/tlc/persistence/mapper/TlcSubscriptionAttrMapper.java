package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.SubscriptionAttr;
import com.cgi.charm.cdm.tlc.SubscriptionType;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcSubscriptionAttrMapper implements
		PersistenceMapper<SubscriptionAttr, TlcSubscriptionInfo> {


	@Override
	public SubscriptionAttr toCdm(TlcSubscriptionInfo tlcSubscriptionInfo) {
		String subscriptionName = tlcSubscriptionInfo.getSubscriptionName();
		
		SubscriptionAttr subscriptionAttr = new SubscriptionAttr();
		subscriptionAttr.setSubscriptionName(subscriptionName);
		subscriptionAttr.setSuppressResponse(tlcSubscriptionInfo.isSuppressResponse());
		subscriptionAttr.setSubscriptionType(SubscriptionType.fromValue(tlcSubscriptionInfo.getSubscriptionType()));
		return subscriptionAttr;
	}

}
