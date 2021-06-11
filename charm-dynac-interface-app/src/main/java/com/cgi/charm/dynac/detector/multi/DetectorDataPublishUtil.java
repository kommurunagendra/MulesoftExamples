package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.tmdd._3.messages.DateTimeZone;
import org.tmdd._3.messages.DetectorData;
import org.tmdd._3.messages.DetectorDataDetailCharm;
import org.tmdd._3.messages.OrganizationInformation;

import com.cgi.charm.cdm.si.detector.DetectorPublish;
import com.cgi.charm.cdm.si.detector.Intensity;
import com.cgi.charm.cdm.si.detector.Speed;
import com.cgi.charm.dynac.constants.DetectorConstants;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class DetectorDataPublishUtil {

	private OrganizationInformation organizationInformation;

	/**
	 * populateDataPublish
	 * @param detectorPublishList of type List<DetectorPublish>
	 * @return detectorDataList
	 */
	public List<DetectorData> populateDataPublish(List<DetectorPublish> detectorPublishList) {

		organizationInformation = new OrganizationInformation();
		organizationInformation.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
		List<DetectorData> detectorDataList = new ArrayList<>();

		for (DetectorPublish detectorPublish : detectorPublishList) {
			DetectorData detectorData = new DetectorData();

			detectorData.setOrganizationInformation(this.organizationInformation);
			DetectorDataDetailCharm detectorDataDetailCharm = new DetectorDataDetailCharm();
			if (detectorPublish.getFepId() != null) {
				detectorDataDetailCharm.setSiteId(detectorPublish.getFepId().getId());
			}
			if (detectorPublish.getOutstationId() != null) {
				detectorDataDetailCharm.setStationId(detectorPublish.getOutstationId().getId());
			}
			if (detectorPublish.getDetectorId() != null) {
				detectorDataDetailCharm.setDetectorId(detectorPublish.getDetectorId().getId());
			}
			DateTimeZone dateTime = new DateTimeZone();
			dateTime.setDate("19700101");
			dateTime.setTime("000000");
			detectorDataDetailCharm.setDetectionTimeStamp(dateTime);

			addDetectorStatusAttrs(detectorPublish, detectorDataDetailCharm);

			DetectorData.DetectorList detectorList = new DetectorData.DetectorList();
			detectorList.getDetectorDataDetail().add(detectorDataDetailCharm);
			detectorData.setDetectorList(detectorList);

			detectorDataList.add(detectorData);
		}
		return detectorDataList;
	}

	/**
	 * @param detectorPublish
	 * @param detectorDataDetailCharm
	 */
	private void addDetectorStatusAttrs(DetectorPublish detectorPublish,
			DetectorDataDetailCharm detectorDataDetailCharm) {

		if (detectorPublish.getDetectorStatusAttr() != null) {
			String replaceDateTime = detectorPublish.getDetectorStatusAttr().getLastStatusUpdate().toString()
					.replaceAll("[-:TZ]", "");

			DateTimeZone dateTime = new DateTimeZone();
			dateTime.setDate(replaceDateTime.substring(0, 8));
			dateTime.setTime(replaceDateTime.substring(8, 14));
			detectorDataDetailCharm.setDetectionTimeStamp(dateTime);
			// default speed value
			detectorDataDetailCharm.setVehicleSpeed((short) 255);
			// update speed value based condition kwh or No Traffic
			Speed speed = detectorPublish.getDetectorStatusAttr().getSpeed();
			if (speed.getKmh() != null) {
				detectorDataDetailCharm.setVehicleSpeed(speed.getKmh().shortValue());
			} else if (speed.getNoTraffic() != null) {
				detectorDataDetailCharm.setVehicleSpeed((short) 0);
			}

			// default value
			detectorDataDetailCharm.setVehicleCount(99999);

			// update VehicleCOunt if intensity count received
			Intensity intensity = detectorPublish.getDetectorStatusAttr().getIntensity();
			if (intensity.getCount() != null) {
				detectorDataDetailCharm.setVehicleCount(intensity.getCount());
			}
		}

	}
}
