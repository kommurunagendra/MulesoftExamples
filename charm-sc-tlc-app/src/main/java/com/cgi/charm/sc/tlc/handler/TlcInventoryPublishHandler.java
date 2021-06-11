package com.cgi.charm.sc.tlc.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Availability;
import com.cgi.charm.cdm.tlc.DeviceState;
import com.cgi.charm.cdm.tlc.TlcInventory;
import com.cgi.charm.cdm.tlc.TlcInventoryPublish;
import com.cgi.charm.common.constants.DeviceAction;
import com.cgi.charm.sc.tlc.constants.TlcAppConfig;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.util.handlers.Handler;



/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcInventoryPublishHandler implements
		Handler<Set<TlcDeviceInfo>, TlcInventoryPublish> {
	private static final Logger logger = Logger
			.getLogger(TlcInventoryPublishHandler.class);

	@Autowired
	private TlcQueryService tlcQueryServiceImpl;
	@Autowired
	private TlcInventoryMapper tlcInventoryMapper;
	@Autowired
	private TlcCommandService tlcCommandServiceImpl;
	
	@Autowired
	private TlcAppConfig tlcAppConfig;


	/**
	 * Stores the Tlc Inventory Detials Into the Datastore
	 * @param tlcInventoryPublish InventoryPublish to store the deviceDetails
	 * @return Set returns tlcDeviceInfos
	 */
	@Override
	public Set<TlcDeviceInfo> handle(TlcInventoryPublish tlcInventoryPublish) {
		
		Set<TlcDeviceInfo> removeTlcDeviceInfoList = new HashSet<>();
		List<TlcInventory> tlcInventory = tlcInventoryPublish.getInventoryConfig()
				.getTlcInventory();
		if (tlcInventory.size() <= 0
				|| !tlcAppConfig
						.getSubscriptionId()
						.getUuid()
						.contains(
								tlcInventoryPublish.getSubscriptionId()
										.getUuid())) {
			logger.info("SubscriptionId is not matched with the id in properties file");
			return removeTlcDeviceInfoList;
		}
		if (tlcInventoryPublish.getInventoryConfig().isFullSync()) {
			tlcQueryServiceImpl.getAllTlcs().forEach(tlcDeviceInfo->removeTlcDeviceInfoList.add(tlcDeviceInfo));
			tlcCommandServiceImpl.removeAllTlcs();
		}
		for (TlcInventory inventory : tlcInventory) {
			if (inventory.getAction().toString().toLowerCase().contains(
					DeviceAction.ADD.getDeviceAction())) {
				logger.debug("add inventory");
				addTlcInventory(inventory, tlcInventoryPublish);
			} else if (inventory.getAction().toString().toLowerCase().contains(
					DeviceAction.REMOVE.getDeviceAction())
					&& !tlcInventoryPublish.getInventoryConfig().isFullSync()) {
				logger.debug("remove inventory");
				removeTlcInventory(inventory, removeTlcDeviceInfoList);
			}
		}
		return removeTlcDeviceInfoList;
	}

	/**
	 * Remove the Inventory Details of the devices from the App-Inventory, if
	 * action is "remove"
	 * 
	 * @param inventory
	 * @param tlcInventoryPublish
	 */
	private void removeTlcInventory(TlcInventory inventory,
			Set<TlcDeviceInfo> removeTlcDeviceInfoList) {
		String tlcId = inventory.getTlcId().getId();
		TlcDeviceInfo tlcDeviceInfo = tlcQueryServiceImpl.getTlcById(tlcId);
		removeTlcDeviceInfoList.add(tlcDeviceInfo);
		tlcCommandServiceImpl.removeTlcById(tlcId);
	}

	/**
	 * Add the Inventory Details of the Devices to the App-Inventory, if action
	 * is "add"
	 * 
	 * @param inventory
	 * @param tlcInventoryPublish
	 */
	private void addTlcInventory(TlcInventory inventory,
			TlcInventoryPublish tlcInventoryPublish) {
		String tlcId = inventory.getTlcId().getId();
		TlcDeviceInfo tlcDeviceInfo = tlcQueryServiceImpl.getTlcById(tlcId);
		if (tlcDeviceInfo == null) {
			tlcDeviceInfo = new TlcDeviceInfo();
		}
		tlcInventoryMapper.toDomain(inventory, tlcDeviceInfo);
		TlcDeviceInfo deviceInfo=tlcQueryServiceImpl.getTlcById(tlcDeviceInfo.getTlcId());
		if(deviceInfo!=null){
		if(deviceInfo.getApplicationId()!=null){
			tlcDeviceInfo.setApplicationId(deviceInfo.getApplicationId());
		}
		if(deviceInfo.getIntersectionNumber()!=null){
			tlcDeviceInfo.setIntersectionNumber(deviceInfo.getIntersectionNumber());
		}
		if(deviceInfo.getIntersectionDescription()!=null){
			tlcDeviceInfo.setIntersectionDescription(deviceInfo.getIntersectionDescription());
		}
		if(deviceInfo.getIveraVersion()!=null){
			tlcDeviceInfo.setIveraVersion(deviceInfo.getIveraVersion());
		}
		if(deviceInfo.getPhonenrCentral()!=null){
			tlcDeviceInfo.setPhonenrCentral(deviceInfo.getPhonenrCentral());
		}
		if(deviceInfo.getIpnrCentral()!=null){
			tlcDeviceInfo.setIpnrCentral(deviceInfo.getIpnrCentral());
		}
		if(deviceInfo.getTriggerevents()!=null){
			tlcDeviceInfo.setTriggerevents(deviceInfo.getTriggerevents());
		}
		}
		tlcDeviceInfo.setAvailability(Availability.AVAILABLE.value());
		tlcDeviceInfo.setDeviceState(DeviceState.ACTIVE.value());
		tlcDeviceInfo.setCommState("OFFLINE");
		tlcCommandServiceImpl.addTlcDevice(tlcDeviceInfo);
	}
}
