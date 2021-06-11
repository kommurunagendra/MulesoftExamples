package com.cgi.charm.sc.tlc.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcControl;
import com.cgi.charm.cdm.tlc.TlcControlRequest;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

/**
 * @author CHARM CGI Handles The Control Req Msg
 */
@Component
public class TlcControlRequestHandler {

	@Autowired
	private TlcQueryService tlcQueryServiceImpl;

	@Autowired
	private TlcCommandService tlcCommandServiceImpl;

	private static final Logger logger = Logger
			.getLogger(TlcControlRequestHandler.class);

	/**
	 * Store The TlcControlAttr In to the Inventory
	 * @param tlcControlRequest controlRequestObject
	 * @return List returns list of DeviceInfos
	 */
	public List<TlcDeviceInfo> handle(TlcControlRequest tlcControlRequest) {
		List<TlcControl> tlcControls = tlcControlRequest.getTlcControl();
		List<TlcDeviceInfo> deviceInfos = new ArrayList<>();
		for (TlcControl tlcControl : tlcControls) {
			String tlcId = tlcControl.getTlcId().getId();
			if (tlcQueryServiceImpl.getTlcById(tlcId) == null) {
				logger.info(tlcId + "device is not added in the inventory");
				continue;
			}
			TlcDeviceInfo tlcDeviceInfo = tlcQueryServiceImpl.getTlcById(tlcId);
			Date dateUtil = Date.from(tlcControl.getTlcControlAttr().getControlTimestamp());

			java.sql.Timestamp timeStampMessageCreateDate = new java.sql.Timestamp(
					dateUtil.getTime());
			tlcDeviceInfo.setControlTimeStamp(timeStampMessageCreateDate);
			tlcDeviceInfo.setIveraObject(tlcControl.getTlcControlAttr()
					.getIveraObject());
			tlcCommandServiceImpl.addTlcDevice(tlcDeviceInfo);
			deviceInfos.add(tlcDeviceInfo);
		}
		return deviceInfos;
	}
}
