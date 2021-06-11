package com.cgi.charm.sc.tlc.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Id;
import com.cgi.charm.cdm.tlc.Meta;
import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.cdm.tlc.SubscriptionRef;
import com.cgi.charm.cdm.tlc.TlcPublish;
import com.cgi.charm.cdm.tlc.TlcStatusPublish;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.common.util.CharmUtils;
import com.cgi.charm.sc.tlc.constants.TlcConstants;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceSubscriptions;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.sc.tlc.util.TlcUtil;
import com.cgi.charm.util.handlers.Handler;

/**
 * @author CHARM CGI Create Tlc Status Publish
 */
@Component
public class TlcStatusPublishHandler implements Handler<TlcStatusPublish, TlcSubscriptionInfo> {
	@Autowired
	private TlcQueryService tlcQueryServiceImpl;
	@Autowired
	private TlcStatusAttrMapper tlcStatusAttrMapper;

	@Autowired
	TlcInventoryAttrMapper tlcInventoryAttrMapper;

	private String version;

	private static final Logger log = Logger.getLogger(TlcStatusPublishHandler.class);

	/**
	 * Prepares TlcStatusPublish By fetching the subscriptionDetials from the
	 * Subscription Datastore
	 * 
	 * @param tlcSubscriptionInfo
	 *            prepare statusPublish by taking subscriptionInfo as param
	 * @return TlcStatusPublish returns TlcStatusPublish
	 */
	@Override
	public TlcStatusPublish handle(TlcSubscriptionInfo tlcSubscriptionInfo) {
		List<TlcPublish> deviceTlcPublishs = new ArrayList<>();

		if (tlcSubscriptionInfo == null) {
			log.info("dataStore is empty or status subscription is not initiated !!!");
			return null;
		}
		if(tlcSubscriptionInfo.isSuppressResponse())
			return null;
		String owner = tlcSubscriptionInfo.getOwner() == null?"dynac":tlcSubscriptionInfo.getOwner();
		TlcStatusPublish tlcStatusPublish = new TlcStatusPublish();
		Meta meta = new Meta();
		meta.setMessageId(new Uuid(CharmUtils.getUuid().toString()));
		meta.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		tlcStatusPublish.setMeta(meta);
		tlcStatusPublish.setSubscriptionId(new Uuid(tlcSubscriptionInfo.getSubscriptionId()));
		tlcStatusPublish.setSubscriptionRef(prepareSubscriptionRef(tlcSubscriptionInfo));
		List<TlcDeviceInfo> devicesForPublish = getTlcDevicesFromSubscription(tlcSubscriptionInfo);
		for (TlcDeviceInfo deviceInfo : devicesForPublish) {
			if(deviceInfo.getOwner().equalsIgnoreCase(owner) || owner.equalsIgnoreCase("all")) {
			TlcPublish tlcPublish = new TlcPublish();
			tlcPublish.setTlcId(new Id(deviceInfo.getTlcId()));
			tlcPublish.setTlcInventoryAttr(tlcInventoryAttrMapper.toCdm(deviceInfo));
			if (deviceInfo.getCurrentStatusAkt() != null) {
				tlcPublish.setTlcStatusAttr(tlcStatusAttrMapper.toCdm(deviceInfo));
			} else {
				tlcPublish.setTlcStatusAttr(TlcUtil.getDummyTlcStatusAttr());
			}
			deviceTlcPublishs.add(tlcPublish);
			}
		}
		tlcStatusPublish.setVersion(getVersion());
		tlcStatusPublish.setMeta(meta);
		tlcStatusPublish.withTlcPublish(deviceTlcPublishs);
		if (deviceTlcPublishs.size() > 0) {
			return tlcStatusPublish;
		} else {
			log.info("Tlc Device is not there in the datastore");
			return tlcStatusPublish;
		}
	}

	public String getVersion() {
		return version;
	}

	@Value("${tlc.version}")
	public void setVersion(String version) {
		this.version = version;
	}

	private List<TlcDeviceInfo> getTlcDevicesFromSubscription(TlcSubscriptionInfo tlcSubscriptionInfo) {
		List<TlcDeviceInfo> publishableDevices = null;
		if (tlcSubscriptionInfo.isAllTlcs()) {
			publishableDevices = tlcQueryServiceImpl.getAllTlcs();
		} else {
			publishableDevices = new ArrayList<TlcDeviceInfo>();
			List<TlcDeviceSubscriptions> tlcDeviceSubscriptionList = tlcSubscriptionInfo.getTlcDeviceSubscriptions();
			for (TlcDeviceSubscriptions tlcDeviceSubscriptions : tlcDeviceSubscriptionList) {
				TlcDeviceInfo deviceInfo = tlcQueryServiceImpl.getTlcById(tlcDeviceSubscriptions.getTlcId());

				if (deviceInfo != null) {
					publishableDevices.add(deviceInfo);
				}
			}
		}
		return publishableDevices;
	}

	/**
	 * prepareSubscriptionRef
	 * 
	 * @param tlcSubscriptionInfo
	 *            the tlcSubscriptionInfo
	 * @return SubscriptionRef
	 */
	private SubscriptionRef prepareSubscriptionRef(TlcSubscriptionInfo tlcSubscriptionInfo) {
		SubscriptionRef subscriptionRef = null;
		if (null != tlcSubscriptionInfo.getProperties()) {
			subscriptionRef = new SubscriptionRef();
			for (TlcPropertyInfo prop : tlcSubscriptionInfo.getProperties()) {
				if (TlcConstants.SOURCE.equalsIgnoreCase(prop.getKey())) {
					subscriptionRef.getProperty().add(new Property(TlcConstants.DESTINATION, prop.getValue()));
				} else if (TlcConstants.DESTINATION.equalsIgnoreCase(prop.getKey())) {
					subscriptionRef.getProperty().add(new Property(TlcConstants.SOURCE, prop.getValue()));
				}
			}
		}
		return subscriptionRef;
	}
}
