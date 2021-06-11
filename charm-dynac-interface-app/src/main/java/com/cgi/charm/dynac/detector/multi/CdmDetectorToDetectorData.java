package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmdd._3.messages.DateTimeZone;
import org.tmdd._3.messages.DetectorData;
import org.tmdd._3.messages.DetectorDataDetailCharm;
import org.tmdd._3.messages.DetectorDataMsg;
import org.tmdd._3.messages.OrganizationInformation;

import com.cgi.charm.cdm.si.detector.DetectorSiInfoReply;
import com.cgi.charm.dynac.constants.DetectorConstants;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class CdmDetectorToDetectorData {
	
	@Autowired
	DetectorDataPublishUtil detectorDataPublishUtil;

	/**
	 * DetectorDataMsg
	 * @param detectorInfoReply of DetectorSiInfoReply
	 * @return detectorDataMsg
	 */
	public DetectorDataMsg handle(DetectorSiInfoReply detectorInfoReply) {
		List<DetectorData> detectorDataList = new ArrayList<>();
		DetectorDataMsg detectorDataMsg = new DetectorDataMsg();
		
		if (detectorInfoReply.getDetectorPublish().isEmpty()) {
			DetectorData detectorData = new DetectorData();
			DetectorDataDetailCharm detectorDataDetailCharm = new DetectorDataDetailCharm();
			OrganizationInformation organizationInformation = new OrganizationInformation();
			organizationInformation.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
			detectorData.setOrganizationInformation(organizationInformation);
			detectorDataDetailCharm.setDetectorId("0");
			DateTimeZone dateTime = new DateTimeZone();
			dateTime.setDate("19700101");
			dateTime.setTime("000000");
			detectorDataDetailCharm.setDetectionTimeStamp(dateTime);
			detectorDataDetailCharm.setVehicleCount(0);
			DetectorData.DetectorList detectorList = new DetectorData.DetectorList();
			detectorList.getDetectorDataDetail().add(detectorDataDetailCharm);
			detectorData.setDetectorList(detectorList);
			detectorDataList.add(detectorData);
		}
		detectorDataList.addAll(detectorDataPublishUtil.populateDataPublish(detectorInfoReply.getDetectorPublish()));
		detectorDataMsg.getDetectorDataItem().addAll(detectorDataList);
		return detectorDataMsg;
	}

	
}
