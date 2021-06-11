package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceSubscriptions;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */

@Component
public class TlcSpecMapper implements
          PersistenceMapper<List<TlcSpec>, List<TlcDeviceInfo>> {
	/**
	 * Tlcspec to persistence device subscriptions
	 * @param subscriptionId subscriptionId
	 * @param tlcSpecList tlcSpecList
	 * @return List
	 */
	public List<TlcDeviceSubscriptions> mapTlcSpec(String subscriptionId,
			List<TlcSpec> tlcSpecList) {
		List<TlcDeviceSubscriptions> deviceSubscriptionList = new ArrayList<>();
		for (TlcSpec detectorSpec : tlcSpecList) {
			TlcDeviceSubscriptions deviceSubscription = new TlcDeviceSubscriptions();
			deviceSubscription.setTlcId(detectorSpec.getTlcId().getId());
			deviceSubscription.setSubscriptionId(subscriptionId);
			deviceSubscriptionList.add(deviceSubscription);
		}
		return deviceSubscriptionList;
	}

	/*@Override
	public List<TlcSpec> toCdm(List<TlcDeviceInfo> tlcDeviceInfoList) {
		List<TlcSpec> tlcSpecList = new ArrayList<TlcSpec>();
		for (TlcDeviceInfo tlcDeviceInfo : tlcDeviceInfoList) {
			tlcSpecList.add(new TlcSpec(new IdType(tlcDeviceInfo.getTlcId())));
		}
		return tlcSpecList;
	}*/

}
