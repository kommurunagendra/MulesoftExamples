/*
 *  Copyright (c) 2017 Pavani Rudravaram. All Rights Reserved.
 *  
 *  Filename: TlcStatusSubscriptionOutboundHandler.java
 */
package com.cgi.charm.sc.tlc.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.cdm.tlc.SubscriptionAttr;
import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.cdm.tlc.SubscriptionType;
import com.cgi.charm.cdm.tlc.TlcStatusSubscription;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.sc.tlc.constants.TlcAppConfig;
import com.cgi.charm.sc.tlc.util.TlcUtil;
import com.cgi.charm.util.handlers.Handler;

/**
 * The Class TlcStatusSubscriptionOutboundHandler.
 */
@Component
public class TlcStatusSubscriptionOutboundHandler implements
        Handler<TlcStatusSubscription, String> {

   
    @Autowired
    private TlcAppConfig tlcAppConfig;


    /*
     * (non-Javadoc)
     *
     * @see com.cgi.charm.util.handlers.Handler#handle(java.lang.Object)
     */
    @Override
    public TlcStatusSubscription handle(String input) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        Uuid subscriptionId;
        String subscriptionName;
        SubscriptionRef subscriptionRef;
        String subscriptionSource;
        String subscriptionDestination;
        switch (input) {
            case "otmcServices":
                subscriptionId = tlcAppConfig.getOtmcServicesstatusSubscriptionId();
                subscriptionName = tlcAppConfig.getOtmcServicesStatusSubscriptionName();
                subscriptionSource = tlcAppConfig.getOtmcServicesStatusPropertySource();
                subscriptionDestination = tlcAppConfig.getOtmcServicesStatusPropertyDestination();
                subscriptionRef = getSubscriptionRef(subscriptionSource, subscriptionDestination);
                subscriptionConfig = getSubscriptionConfig(subscriptionId, subscriptionName, subscriptionRef);
                break;
            case "otmcInbound":
                subscriptionId = tlcAppConfig.getOtmcInboundstatusSubscriptionId();
                subscriptionName = tlcAppConfig.getOtmcInboundstatusSubscriptionName();
                subscriptionSource = tlcAppConfig.getOtmcInboundstatusPropertySource();
                subscriptionDestination = tlcAppConfig.getOtmcInboundstatusPropertyDestination();
                subscriptionRef = getSubscriptionRef(subscriptionSource, subscriptionDestination);
                subscriptionConfig = getSubscriptionConfig(subscriptionId, subscriptionName, subscriptionRef);
                break;
        }
        return getTlcStatusSubscription(subscriptionConfig);
    }


    private TlcStatusSubscription getTlcStatusSubscription(SubscriptionConfig subscriptionConfig) {
        TlcStatusSubscription tlcStatusSubscription = new TlcStatusSubscription();
        tlcStatusSubscription.setVersion(tlcAppConfig.getCdmVersion());
        tlcStatusSubscription.setMeta(TlcUtil.commonConfigMeta());
        tlcStatusSubscription.setSubscriptionConfig(subscriptionConfig);
        return tlcStatusSubscription;
    }


    private SubscriptionConfig getSubscriptionConfig(Uuid subscriptionId, String subscriptionName, SubscriptionRef subscriptionRef) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        subscriptionConfig.setSubscriptionId(subscriptionId);
        subscriptionConfig.setSubscriptionRef(subscriptionRef);
        SubscriptionAttr subscriptionAttr = new SubscriptionAttr();
        subscriptionAttr.setSubscriptionName(subscriptionName);
        subscriptionAttr.setSubscriptionType(SubscriptionType.STATUS);
        subscriptionConfig.setSubscriptionAttr(subscriptionAttr);
        subscriptionConfig.setAllTlcs(true);
        return subscriptionConfig;
    }

    private SubscriptionRef getSubscriptionRef(String source, String destination) {
        SubscriptionRef subscriptionRef = new SubscriptionRef();

        List<Property> propertyList = new ArrayList<Property>();
        propertyList.add(getProperty("source", source));
        propertyList.add(getProperty("destination", destination));

        subscriptionRef.setProperty(propertyList);
        return subscriptionRef;
    }

    private Property getProperty(String key, String value) {
        Property property = new Property();
        property.setKey(key);
        property.setValue(value);
        return property;
    }
}
