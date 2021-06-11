package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.List;

import org.ntcip.c2c_message_administration.C2CMessagePublication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmdd._3.messages.DetectorData;
import org.tmdd._3.messages.DetectorDataUpdateMsg;

import com.cgi.charm.cdm.si.detector.DetectorSiDataPublish;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class CdmDetectorToDataPublish {

	@Autowired
	private DetectorDataPublishUtil detectorDataPublishUtil;

	/**
	 * DetectorDataUpdateMsg 
	 * @param detectorDataPublish of type DetectorSiDataPublish
	 * @return detectorDataUpdateMsg
	 */
	public DetectorDataUpdateMsg handle(DetectorSiDataPublish detectorDataPublish) {
		DetectorDataUpdateMsg detectorDataUpdateMsg = new DetectorDataUpdateMsg();
		C2CMessagePublication c2cMessagePublication = new C2CMessagePublication();
		c2cMessagePublication.setSubscriptionID(detectorDataPublish.getSubscriptionId().getUuid());
		detectorDataUpdateMsg.setC2CMsgAdmin(c2cMessagePublication);
		List<DetectorData> detectorDataList = new ArrayList<>();
		detectorDataList.addAll(detectorDataPublishUtil.populateDataPublish(detectorDataPublish.getDetectorPublish()));
		detectorDataUpdateMsg.getDetectorDataItem().addAll(detectorDataList);
		return detectorDataUpdateMsg;
	}

}
