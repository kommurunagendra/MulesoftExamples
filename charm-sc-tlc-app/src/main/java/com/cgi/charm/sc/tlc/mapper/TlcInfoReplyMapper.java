package com.cgi.charm.sc.tlc.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Id;
import com.cgi.charm.cdm.tlc.MetaReply;
import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionSpec;
import com.cgi.charm.cdm.tlc.TlcInfoReply;
import com.cgi.charm.cdm.tlc.TlcInfoRequest;
import com.cgi.charm.cdm.tlc.TlcPublish;
import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSubscriptionConfigMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.util.mappers.Mapper;

/**
 * @author CHARM CGI Creates Tlc Info Reply
 */
@Component
public class TlcInfoReplyMapper implements Mapper<TlcInfoReply, TlcInfoRequest> {

	@Autowired
	private TlcQueryService tlcQueryServiceImpl;

	@Autowired
	private TlcStatusAttrMapper tlcStatusAttrMapper;

	@Autowired
	TlcInventoryAttrMapper tlcInventoryAttrMapper;

	@Autowired
	private TlcSubscriptionConfigMapper tlcSubscriptionConfigMapper;

	private static final Logger LOGGER = Logger.getLogger(TlcInfoReplyMapper.class);

	private String version;

	/**
	 * Prepares TlcInfoReply Object by fetching the Tlc Details From the
	 * Datastore
	 * 
	 * @param tlcInfoRequest
	 *            TlcInfoRequest
	 * @return TlcInfoReply returns TlcInfoReply
	 */
	public TlcInfoReply map(TlcInfoRequest tlcInfoRequest) {
		List<TlcDeviceInfo> deviceList = getDeviceList(tlcInfoRequest);
		TlcInfoReply tlcInfoReply = new TlcInfoReply();
		tlcInfoReply.setVersion(getVersion());
		String owner = tlcInfoRequest.getOwner() == null?"dynac":tlcInfoRequest.getOwner();
		List<TlcPublish> tlcPublishs = new ArrayList<>();
		for (TlcDeviceInfo deviceInfo : deviceList) {
			if(owner.equalsIgnoreCase(deviceInfo.getOwner()) || owner.equalsIgnoreCase("all")) {
			boolean isInventory = getPrimitiveBooleanValue(tlcInfoRequest.getInfoSpec().isInventory());
			boolean isStatus = getPrimitiveBooleanValue(tlcInfoRequest.getInfoSpec().isStatus());
			TlcPublish tlcPublish = new TlcPublish();
			tlcPublish.setTlcId(new Id(deviceInfo.getTlcId()));
			if(deviceInfo.getCommState().equals("ONLINE")){
			buildInfoReplyStatus(deviceInfo, tlcPublish, isStatus);
			}
			buildInfoReplyInventory(deviceInfo, tlcPublish, isInventory);
			tlcPublishs.add(tlcPublish);
		}
		}
		List<TlcSubscriptionInfo> subscriptionList = getSubscriptionInfo(tlcInfoRequest);

		List<SubscriptionConfig> subscriptionConfigs = new ArrayList<>();

		for (TlcSubscriptionInfo subscriptionInfo : subscriptionList) {
			SubscriptionConfig subscriptionConfig = tlcSubscriptionConfigMapper.toCdm(subscriptionInfo);

			subscriptionConfigs.add(subscriptionConfig);
		}
		tlcInfoReply.setTlcPublish(tlcPublishs);
		tlcInfoReply.setMetaReply(getMetaReply(tlcInfoRequest.getMeta().getMessageId()));
		tlcInfoReply.setSubscriptionConfig(subscriptionConfigs);
		return tlcInfoReply;
	}

	public String getVersion() {
		return version;
	}

	@Value("${tlc.version}")
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param requestId
	 * @return
	 */
	private MetaReply getMetaReply(Uuid requestId) {
		MetaReply reply = new MetaReply();
		reply.setRequestId(requestId);
		reply.setMessageId(requestId);
		reply.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		return reply;
	}

	/**
	 * @param deviceInfo
	 * @param tlcPublish
	 * @param isInventory
	 */
	private void buildInfoReplyInventory(TlcDeviceInfo deviceInfo, TlcPublish tlcPublish, boolean isInventory) {

		if (isInventory) {
			/*
			 * TlcInventoryAttr tlcInventoryAttr = new TlcInventoryAttr();
			 * tlcInventoryAttr.setTlcCommunication(tlcCommunicationMapper.toCdm
			 * (deviceInfo));
			 * tlcInventoryAttr.setTlcGenericAttr(tlcGenericAttrMapper
			 * .toCdm(deviceInfo));
			 */
			tlcPublish.setTlcInventoryAttr(tlcInventoryAttrMapper.toCdm(deviceInfo));
		}
	}

	private void buildInfoReplyStatus(TlcDeviceInfo deviceInfo, TlcPublish tlcPublish, boolean isStatus) {

		if (isStatus) {
			tlcPublish.setTlcStatusAttr(tlcStatusAttrMapper.toCdm(deviceInfo));
		}
	}

	/**
	 * get the DeviceList
	 * 
	 * @param tlcInfoRequest
	 *            TlcInfoRequest
	 * @return List returns deviceInfo's List
	 */

	public List<TlcDeviceInfo> getDeviceList(TlcInfoRequest tlcInfoRequest) {

		List<TlcDeviceInfo> deviceInfos = new ArrayList<>();
		boolean isAllTlcs = Boolean.TRUE.equals(tlcInfoRequest.getAllTlc());

		if (tlcQueryServiceImpl.getAllTlcs().size() == 0) {
			LOGGER.warn("Datastore Is Empty Make Sure Inventory Published");
			return deviceInfos;
		}

		if (isAllTlcs) {
			deviceInfos.addAll(tlcQueryServiceImpl.getAllTlcs());
		} else if (tlcInfoRequest.getTlcSpec() != null && tlcInfoRequest.getTlcSpec().size() > 0) {
			processDeviceList(tlcInfoRequest, deviceInfos);
		}
		return deviceInfos;
	}

	private void processDeviceList(TlcInfoRequest tlcInfoRequest, List<TlcDeviceInfo> deviceInfos) {
		List<TlcSpec> tlcSpecList = tlcInfoRequest.getTlcSpec();

		tlcSpecList.forEach(eachTlcSpec -> {
			tlcQueryServiceImpl.getAllTlcs();
			TlcDeviceInfo deviceInfo = (TlcDeviceInfo) tlcQueryServiceImpl.getTlcById(eachTlcSpec.getTlcId().getId());
			if (deviceInfo != null) {
				deviceInfos.add(deviceInfo);
			} else {
				LOGGER.warn(eachTlcSpec.getTlcId().getId() + " Device is not available in the TlcDatastore");
			}
		});
	}

	/**
	 * get the TlcSubscriptionInfo
	 * 
	 * @param tlcInfoRequest
	 *            the tlcInfoRequest
	 * @return TlcSubscriptionInfo
	 */
	private List<TlcSubscriptionInfo> getSubscriptionInfo(TlcInfoRequest tlcInfoRequest) {

		List<TlcSubscriptionInfo> subscriptionList = new ArrayList<>();
		boolean isAllSubscrition = false;
		if (tlcInfoRequest.getAllSubscriptions() != null) {
			isAllSubscrition = tlcInfoRequest.getAllSubscriptions();
		}
		if (isAllSubscrition) {
			subscriptionList.addAll(tlcQueryServiceImpl.getAllSubscriptions());

		} else if (tlcInfoRequest.getTlcSpec() != null && tlcInfoRequest.getSubscriptionSpec().size() > 0) {
			processSubscriptionInfo(tlcInfoRequest, subscriptionList);
		}

		return subscriptionList;
	}

	private void processSubscriptionInfo(TlcInfoRequest tlcInfoRequest, List<TlcSubscriptionInfo> subscriptionList) {
		List<SubscriptionSpec> subscriptionSpecList = tlcInfoRequest.getSubscriptionSpec();
		for (SubscriptionSpec eachSubscriptionSpec : subscriptionSpecList) {
			tlcQueryServiceImpl.getAllSubscriptions();
			TlcSubscriptionInfo tlcSubscriptionInfo = tlcQueryServiceImpl
					.getSubscriptionById(eachSubscriptionSpec.getSubscriptionId().getUuid());
			if (tlcSubscriptionInfo != null) {
				subscriptionList.add(tlcSubscriptionInfo);
			}
		}
	}

	/** the getPrimitiveBooleanValue
	 * @param booleanWrapper
	 *            Boolean Wrapper
	 * @return boolean returns boolean value
	 */
	public boolean getPrimitiveBooleanValue(Boolean booleanWrapper) {
		return Boolean.TRUE.equals(booleanWrapper);
	}

}
