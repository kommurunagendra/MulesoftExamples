package com.cgi.charm.sc.tlc.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Id;
import com.cgi.charm.cdm.tlc.Meta;
import com.cgi.charm.cdm.tlc.TlcPublish;
import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.cdm.tlc.TlcStatusPublish;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.common.util.CharmUtils;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.sc.tlc.util.TlcUtil;

/**
 * @author CHARM CGI
 *
 */
@Component
public class TlcMulStatusPublishHandler {

	@Autowired
	private TlcQueryService tlcQueryServiceImpl;

	@Autowired
	private TlcStatusAttrMapper tlcStatusAttrMapper;

	@Autowired
	TlcInventoryAttrMapper tlcInventoryAttrMapper;

	private String version;

	/** handle the TlcStatusPublish
	 * @param deviceInfo
	 *            to prepare StatusPublish
	 * @return List returns statusPublishList
	 */
	public List<TlcStatusPublish> handle(TlcDeviceInfo deviceInfo) {
		List<TlcStatusPublish> listOfTlcStatusPublish = new ArrayList<>();
		String owner;
		List<TlcSubscriptionInfo> tlcSubscriptionInfoList = tlcQueryServiceImpl.getAllSubscriptions();
		for (TlcSubscriptionInfo tlcSubscriptionInfo : tlcSubscriptionInfoList) {
			owner = tlcSubscriptionInfo.getOwner() == null?"dynac":tlcSubscriptionInfo.getOwner();
			boolean subscriber = getSubscriber(tlcSubscriptionInfo, deviceInfo);
			if (subscriber) {
				if(deviceInfo.getOwner().equalsIgnoreCase(owner) || owner.equalsIgnoreCase("all")) {
				TlcStatusPublish tlcStatusPublish = new TlcStatusPublish();
				tlcStatusPublish.setSubscriptionId(new Uuid(tlcSubscriptionInfo.getSubscriptionId()));
				TlcPublish tlcPublish = new TlcPublish();
				tlcPublish.setTlcId(new Id(deviceInfo.getTlcId()));
				tlcPublish.setTlcInventoryAttr(tlcInventoryAttrMapper.toCdm(deviceInfo));
				if (deviceInfo.getCurrentStatusAkt() != null) {
					tlcPublish.setTlcStatusAttr(tlcStatusAttrMapper.toCdm(deviceInfo));
				} else {
					tlcPublish.setTlcStatusAttr(TlcUtil.getDummyTlcStatusAttr());
				}
				tlcStatusPublish.getTlcPublish().add(tlcPublish);
				Meta meta = new Meta();
				meta.setMessageId(new Uuid(CharmUtils.getUuid().toString()));
				meta.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
				tlcStatusPublish.setMeta(meta);
				tlcStatusPublish.setVersion(getVersion());
				
				listOfTlcStatusPublish.add(tlcStatusPublish);
			}
			}
		}
		return listOfTlcStatusPublish;
	}

	private boolean getSubscriber(TlcSubscriptionInfo tlcSubscriptionInfo, TlcDeviceInfo deviceInfo) {
		if (Boolean.TRUE.equals(tlcSubscriptionInfo.isAllTlcs())) {
			return true;
		} else {
			for (TlcSpec tlcSpec : tlcSubscriptionInfo.getTlcSpec()) {
				if (tlcSpec.getTlcId().getId().equals(deviceInfo.getTlcId())) {
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
