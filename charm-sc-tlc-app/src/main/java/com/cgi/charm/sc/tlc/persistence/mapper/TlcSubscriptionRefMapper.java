package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcSubscriptionRefMapper implements
		PersistenceMapper<SubscriptionRef, TlcSubscriptionInfo> {

	
	@Override
	public SubscriptionRef toCdm(TlcSubscriptionInfo tlcSubscriptionInfo) {
		SubscriptionRef subscriptionRef =null;
		if(tlcSubscriptionInfo.getProperties().size()>0){
		subscriptionRef = new SubscriptionRef();
		Set<TlcPropertyInfo> propertyInfo = tlcSubscriptionInfo.getProperties();
		List<Property> propertyList = new ArrayList<Property>();
		Property property = new Property();
		for (TlcPropertyInfo eachPropertyInfo : propertyInfo) {
			property.setKey(eachPropertyInfo.getKey());
			property.setValue(eachPropertyInfo.getValue());
		}
		propertyList.add(property);
		subscriptionRef.setProperty(propertyList);
		}
		return subscriptionRef;
	}

}
