package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.SubscriptionAttr;
import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcSubscriptionConfigMapper implements
           PersistenceMapper<SubscriptionConfig, TlcSubscriptionInfo> {
	
	@Autowired
	private TlcSubscriptionRefMapper tlcSubscriptionRefMapper;

	/*@Override
	public SubscriptionConfig map(TlcSubscriptionInfo tlcSubscriptionInfo) {
		String subscriptionId = tlcSubscriptionInfo.getSubscriptionId();
		boolean alltlcs = tlcSubscriptionInfo.isAllTlcs() ;
		SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
		subscriptionConfig.setSubscriptionId(new UuidType(subscriptionId));
		if (alltlcs) {
			subscriptionConfig.setAllTlcs(alltlcs);
		}
		SubscriptionAttr attr = new TlcSubscriptionAttrMapper()
				.toCdm(tlcSubscriptionInfo);
		subscriptionConfig.setSubscriptionAttr(attr);
		subscriptionConfig.setSubscriptionRef(new TlcSubscriptionRefMapper()
				.toCdm(tlcSubscriptionInfo));
		if(tlcSubscriptionInfo.getTlcSpec()!=null && tlcSubscriptionInfo.getTlcSpec().size()>0){
		subscriptionConfig.setTlcSpec(tlcSubscriptionInfo.getTlcSpec());
		}
		return subscriptionConfig;
	}*/

	@Override
	public SubscriptionConfig toCdm(TlcSubscriptionInfo tlcSubscriptionInfo) {
		String subscriptionId = tlcSubscriptionInfo.getSubscriptionId();
		boolean alltlcs = tlcSubscriptionInfo.isAllTlcs() ;
		SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
		subscriptionConfig.setSubscriptionId(new Uuid(subscriptionId));;
		if (alltlcs) {
			subscriptionConfig.setAllTlcs(alltlcs);
		}
		SubscriptionAttr attr = new TlcSubscriptionAttrMapper()
				.toCdm(tlcSubscriptionInfo);
		subscriptionConfig.setSubscriptionAttr(attr);
		SubscriptionRef subscriptionRef = tlcSubscriptionRefMapper.toCdm(tlcSubscriptionInfo);
		if(subscriptionRef!=null){
		subscriptionConfig.setSubscriptionRef(subscriptionRef);
		}
		if(tlcSubscriptionInfo.getTlcSpec()!=null && tlcSubscriptionInfo.getTlcSpec().size()>0){
		subscriptionConfig.setTlcSpec(tlcSubscriptionInfo.getTlcSpec());
		}
		return subscriptionConfig;
	}
}