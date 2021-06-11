/*
 *  Copyright (c) 2017 Pavani Rudravaram.All Rights Reserved.
 *  
 *  TlcStatusPublishInboundHandler.java
 */
package com.cgi.charm.sc.tlc.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.cdm.tlc.Meta;
import com.cgi.charm.cdm.tlc.TlcPublish;
import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.cdm.tlc.TlcStatusPublish;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.common.util.CharmUtils;
import com.cgi.charm.sc.tlc.constants.TlcAppConfig;
import com.cgi.charm.sc.tlc.constants.TlcConstants;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcOtmcDeviceStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.util.handlers.Handler;

@Component
public class TlcStatusPublishInboundHandler implements Handler<List<TlcStatusPublish>, TlcStatusPublish> {

	private static final Logger LOG = Logger.getLogger(TlcStatusPublishInboundHandler.class);
	
	 @Autowired
	    private TlcAppConfig tlcAppConfig;

	 @Autowired
	private TlcQueryService tlcQueryServiceImpl;
	 
	 @Autowired
	 private TlcCommandService tlcCommandServiceImpl;
	 
	 @Autowired
	 private TlcOtmcDeviceStatusAttrMapper tlcOtmcDeviceStatusAttrMapper;
	 
	private String version;

	@Autowired
	private TlcStatusAttrMapper tlcStatusAttrMapper;
	 
	@Override
	public List<TlcStatusPublish> handle(TlcStatusPublish tlcStatusPublish) {
		List<TlcStatusPublish> tlcPublishToSendList = null;
        LOG.debug("Handling Received Status Publish");
        String subscriptionId = tlcStatusPublish.getSubscriptionId().getUuid();
        
        if(subscriptionId.equalsIgnoreCase(tlcAppConfig.getOtmcInboundstatusSubscriptionId().getUuid().toString()))
        	tlcPublishToSendList = handleTlcStatusPublish(tlcStatusPublish);
        else if(subscriptionId.equalsIgnoreCase(tlcAppConfig.getOtmcServicesstatusSubscriptionId().getUuid().toString()))
        	tlcPublishToSendList = handleTlcStatusPublishOtmcDeviceStatus(tlcStatusPublish);
		return tlcPublishToSendList;
	}

	private List<TlcStatusPublish> handleTlcStatusPublishOtmcDeviceStatus(TlcStatusPublish tlcStatusPublish) {
		
		
		List<TlcPublish> tlcPublishListToSend = new ArrayList<TlcPublish>();
		
		TlcDeviceInfo tlcDevice = null;
		for(TlcPublish tlcPublish:tlcStatusPublish.getTlcPublish())
		{
			String tlcId = tlcPublish.getTlcId().getId();
			tlcDevice = tlcQueryServiceImpl.getTlcById(tlcId); 
			String availability;
			if(tlcDevice != null)
			{
				availability =  tlcDevice.getAvailability();
				tlcOtmcDeviceStatusAttrMapper.toDomain(tlcPublish.getTlcStatusAttr().getOtmcDeviceStatus(), tlcDevice);
				tlcCommandServiceImpl.addTlcDevice(tlcDevice);
				if(!tlcDevice.getAvailability().equalsIgnoreCase(availability))
					tlcPublishListToSend.add(tlcPublish);
			}
		}
		
		if(tlcPublishListToSend.size()>0)
			return prepareStatusPublishForOtmcOutbound(tlcPublishListToSend);
		else
			return null;
	}
	
	
	private List<TlcStatusPublish> handleTlcStatusPublish(TlcStatusPublish tlcStatusPublish) {
		
		TlcDeviceInfo tlcDevice = null;
		
		for(TlcPublish tlcPublish:tlcStatusPublish.getTlcPublish())
		{
			tlcDevice = tlcQueryServiceImpl.getTlcById(tlcPublish.getTlcId().getId());
			if(tlcDevice != null) {
				tlcStatusAttrMapper.toDomain(tlcPublish.getTlcStatusAttr(), tlcDevice);
				tlcCommandServiceImpl.addTlcDevice(tlcDevice);
			}
		}
			return prepareStatusPublishForSubscribers(tlcStatusPublish);
	}

	private List<TlcStatusPublish> prepareStatusPublishForSubscribers(TlcStatusPublish tlcStatusPublish) {
		List<TlcStatusPublish> statusPublishToSend = new ArrayList<TlcStatusPublish>();
		
		List<TlcSubscriptionInfo> subscriptions = tlcQueryServiceImpl.getAllSubscriptions();
		for (TlcSubscriptionInfo tlcSubscriptionInfo : subscriptions) {
			TlcStatusPublish tlcStatusPublishToSend = prepareTlcStatusPublishToSend(tlcSubscriptionInfo,tlcStatusPublish.getTlcPublish());
			if(tlcStatusPublishToSend.getTlcPublish().size()>0)
				statusPublishToSend.add(tlcStatusPublishToSend);
		}
		return statusPublishToSend;
	}

	private List<TlcStatusPublish> prepareStatusPublishForOtmcOutbound(List<TlcPublish> tlcPublishListToSend) {
		List<TlcStatusPublish> statusPublishToSend = new ArrayList<TlcStatusPublish>();
		
		List<TlcSubscriptionInfo> subscriptions = tlcQueryServiceImpl.getAllSubscriptions();
		for (TlcSubscriptionInfo tlcSubscriptionInfo : subscriptions) {
			if(getSource(tlcSubscriptionInfo).equalsIgnoreCase(tlcAppConfig.getOtmcOutboundStatusPropertySource())) {
				TlcStatusPublish tlcStatusPublish = prepareTlcStatusPublishToSend(tlcSubscriptionInfo,tlcPublishListToSend);
			if(tlcStatusPublish.getTlcPublish().size()>0)
				statusPublishToSend.add(tlcStatusPublish);
			}
		}
		return statusPublishToSend;
	}

	private TlcStatusPublish prepareTlcStatusPublishToSend(TlcSubscriptionInfo tlcSubscriptionInfo,List<TlcPublish> tlcPublishList) {
		TlcStatusPublish tlcStatusPublishToSend = new TlcStatusPublish();
		tlcStatusPublishToSend.setSubscriptionId(new Uuid(tlcSubscriptionInfo.getSubscriptionId()));
		Meta meta = new Meta();
		meta.setMessageId(new Uuid(CharmUtils.getUuid().toString()));
		meta.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		tlcStatusPublishToSend.setMeta(meta);
		tlcStatusPublishToSend.setVersion(getVersion());
		
		if(null != tlcSubscriptionInfo.getProperties() && !tlcSubscriptionInfo.getProperties().isEmpty()) {
            SubscriptionRef subscriptionRef = new SubscriptionRef();
            subscriptionRef.setProperty(getPropertyList(tlcSubscriptionInfo.getProperties()));
            tlcStatusPublishToSend.setSubscriptionRef(subscriptionRef);
        }
		
		for(TlcPublish tlcPublish:tlcPublishList) {
			boolean subscriber = getSubscriber(tlcSubscriptionInfo, tlcPublish.getTlcId().getId());
			if(subscriber)
				tlcStatusPublishToSend.getTlcPublish().add(tlcPublish);
		}
		return tlcStatusPublishToSend;
	}
	
	 private List<Property> getPropertyList(Set<TlcPropertyInfo> propertyInfoSet){
	        List<Property> propertyList = new ArrayList<>();

	        for(TlcPropertyInfo tlcPropertyInfo : propertyInfoSet){
	            Property  property = new Property();
	            if(tlcPropertyInfo.getKey().equalsIgnoreCase("source")){
	                property.setKey("destination");
	            }else{
	                property.setKey("source");
	            }
	            property.setValue(tlcPropertyInfo.getValue());
	            propertyList.add(property);
	        }
	        return propertyList;
	    }
	
	private String getSource(TlcSubscriptionInfo tlcSubscriptionInfo) {
		
		String source = "";
		if (null != tlcSubscriptionInfo && null != tlcSubscriptionInfo.getProperties()
				&& !tlcSubscriptionInfo.getProperties().isEmpty()) {
			for (TlcPropertyInfo prop : tlcSubscriptionInfo.getProperties()) {
				if (TlcConstants.SOURCE.equalsIgnoreCase(prop.getKey())) {
					source=prop.getValue();
				}
			}

		}
		return source;
	}

	private boolean getSubscriber(TlcSubscriptionInfo tlcSubscriptionInfo, String tlcId) {
		if (Boolean.TRUE.equals(tlcSubscriptionInfo.isAllTlcs())) {
			return true;
		} else {
			for (TlcSpec tlcSpec : tlcSubscriptionInfo.getTlcSpec()) {
				if (tlcSpec.getTlcId().getId().equals(tlcId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getVersion() {
		return version;
	}

	@Value("${tlc.version}")
	public void setVersion(String version) {
		this.version = version;
	}	

}
