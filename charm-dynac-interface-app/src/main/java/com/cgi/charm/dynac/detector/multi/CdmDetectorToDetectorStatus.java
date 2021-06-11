package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmdd._3.messages.CommunicationStatistics;
import org.tmdd._3.messages.DateTimeZone;
import org.tmdd._3.messages.DetectorStatus;
import org.tmdd._3.messages.DetectorStatusCharm;
import org.tmdd._3.messages.DetectorStatusDetailsCharm;
import org.tmdd._3.messages.DetectorStatusMsg;
import org.tmdd._3.messages.DeviceMode;
import org.tmdd._3.messages.DeviceStatusHeader;
import org.tmdd._3.messages.DeviceStatusHeaderCharm;
import org.tmdd._3.messages.LocalOverride;
import org.tmdd._3.messages.OrganizationInformation;
import org.tmdd._3.messages.OutstationTrafficFlow;
import org.tmdd._3.messages.PowerSource;

import com.cgi.charm.cdm.si.detector.DetectorPublish;
import com.cgi.charm.cdm.si.detector.DetectorSiInfoReply;
import com.cgi.charm.cdm.si.detector.TrafficFlow;
import com.cgi.charm.dynac.constants.DetectorConstants;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class CdmDetectorToDetectorStatus {

	@Autowired
	private DetectorStatusPublishUtil detectorStatusPublishUtil;

	/**
	 * DetectorStatusMsg
	 * @param detectorInfoReply of type DetectorSiInfoReply
	 * @return detectorStatusMsg
	 */
	public DetectorStatusMsg handle(DetectorSiInfoReply detectorInfoReply) {
		DetectorStatusMsg detectorStatusMsg = new DetectorStatusMsg();
		List<DetectorStatusCharm> detectorStatusCharmList = new ArrayList<>();
		List<DetectorStatusDetailsCharm> list;
		DetectorStatusCharm detectorStatusCharm = new DetectorStatusCharm();
		if (detectorInfoReply.getDetectorPublish().isEmpty()) {
			DeviceStatusHeader deviceStatusHeader = new DeviceStatusHeader();
			OrganizationInformation organizationInformation = new OrganizationInformation();
			organizationInformation.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
			deviceStatusHeader.setOrganizationInformation(organizationInformation);
			deviceStatusHeader.setDeviceId("0");
			deviceStatusHeader.setDeviceStatus(DetectorConstants.STATUS_UNKNOWN);
			detectorStatusCharm.setDetectorStationStatusHeader(deviceStatusHeader);
			detectorStatusCharmList.add(detectorStatusCharm);
			detectorStatusMsg.getDetectorStatusItem().addAll(detectorStatusCharmList);
		}
		for (DetectorPublish detectorPublish : detectorInfoReply.getDetectorPublish()) {
			if (detectorPublish.getOutstationId() == null) {
				continue;
			}
			if (!detectorStatusCharmList.isEmpty() && listFlag(detectorStatusCharmList, detectorPublish)) {
				continue;
			} else {
				String outStationId = detectorPublish.getOutstationId().getId();
				list = new ArrayList<DetectorStatusDetailsCharm>();

				DeviceStatusHeaderCharm deviceStatusHeaderCharm = new DeviceStatusHeaderCharm();
				OrganizationInformation organizationInformation = new OrganizationInformation();
				organizationInformation.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
				deviceStatusHeaderCharm.setOrganizationInformation(organizationInformation);
				deviceStatusHeaderCharm.setDeviceId(outStationId);
				LocalOverride localOverride = new LocalOverride();
				if (detectorPublish.getOutstationStatusAttr() != null) {
					detectorStatusLastCommTime(detectorPublish, deviceStatusHeaderCharm);

					detectorStatusCharm
							.setAuxPowerAvailable(detectorPublish.getOutstationStatusAttr().isAuxPowerAvailable());
					detectorPublishDeviceMode(detectorStatusCharm, detectorPublish);
					detectorPublishPowerSupply(detectorStatusCharm, detectorPublish);
					detectorStatusCharm.setColdStart(detectorPublish.getOutstationStatusAttr().isReportColdStart());
					detectorStatusCharm.setFatalError(detectorPublish.getOutstationStatusAttr().isFatalError());
					detectorStatusCharm
							.setMaintenanceCode(detectorPublish.getOutstationStatusAttr().getMaintenanceCode());
					detectorStatusCharm.setAidOverride(detectorPublish.getOutstationStatusAttr().isAidOverride());
					List<TrafficFlow> trafficList = detectorPublish.getOutstationStatusAttr().getTrafficFlow();
					List<OutstationTrafficFlow> outstationTrafficFlowList = new ArrayList<OutstationTrafficFlow>();
					trafficList.stream().forEach(tlist -> {
						OutstationTrafficFlow outstationTrafficFlow = new OutstationTrafficFlow();
						outstationTrafficFlow.setAidActive(tlist.isAidActive());
						outstationTrafficFlow.setAidRecommendation(tlist.getAidRecommendation());
						outstationTrafficFlow.setTrafficFlowNumber(tlist.getTrafficFlowNumber());
						outstationTrafficFlowList.add(outstationTrafficFlow);

					});
					detectorStatusCharm.getTrafficFlow().addAll(outstationTrafficFlowList);
					localOverride
							.setChannel1(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel1());
					localOverride
							.setChannel2(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel2());
					localOverride
							.setChannel3(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel3());
					localOverride
							.setChannel4(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel4());

					detectorStatusDynamicSpeed(detectorStatusCharm, detectorPublish);
					detectorStatusMeasureNumber(detectorStatusCharm, detectorPublish);
				}
				deviceStatusHeaderCharm.setDeviceType(DetectorConstants.DETECTOR_DEVICE_TYPE);
				deviceStatusHeaderCharm.setDeviceStatus(DetectorConstants.STATUS_UNKNOWN);
				detectorStatusCharm.setLocalOveride(localOverride);
				detectorStatusCharm.setDetectorStationStatusHeader(deviceStatusHeaderCharm);
				CommunicationStatistics communicationstatistics = new CommunicationStatistics();
				if (detectorPublish.getOutstationStatisticsAttr() != null) {
					communicationstatistics.setCount1(detectorPublish.getOutstationStatisticsAttr().getF1Count());
					communicationstatistics.setCount2(detectorPublish.getOutstationStatisticsAttr().getF2Count());
					communicationstatistics.setCount3(detectorPublish.getOutstationStatisticsAttr().getF3Count());
					communicationstatistics
							.setStatisticsUpdated(detectorPublish.getOutstationStatisticsAttr().isStatisticsUpdated());
				}
				detectorStatusCharm.setStatistics(communicationstatistics);
				for (DetectorPublish tempDetectorPublish : detectorInfoReply.getDetectorPublish()) {
					addDetectorStatusDetails(list, outStationId, tempDetectorPublish);
				}
				if (detectorPublish.getDetectorId() != null) {
					DetectorStatus.DetectorList detectorList = new DetectorStatus.DetectorList();
					detectorList.getDetector().addAll(list);
					detectorStatusCharm.setDetectorList(detectorList);
					detectorStatusCharmList.add(detectorStatusCharm);
					detectorStatusMsg.getDetectorStatusItem().addAll(detectorStatusCharmList);
				}
			}
		}

		detectorStatusCharmList = detectorStatusPublishUtil
				.populateStatusPublish(detectorInfoReply.getDetectorPublish());
		detectorStatusMsg.getDetectorStatusItem().addAll(detectorStatusCharmList);

		return detectorStatusMsg;
	}

	private void addDetectorStatusDetails(List<DetectorStatusDetailsCharm> list, String outStationId,
			DetectorPublish tempDetectorPublish) {
		if (tempDetectorPublish.getOutstationId() != null
				&& outStationId.equals(tempDetectorPublish.getOutstationId().getId())) {
			DetectorStatusDetailsCharm detectorStatusDetailsCharm = new DetectorStatusDetailsCharm();
			DeviceStatusHeaderCharm deviceStatusHeaderCharms = new DeviceStatusHeaderCharm();
			OrganizationInformation organizationInformations = new OrganizationInformation();
			organizationInformations.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
			deviceStatusHeaderCharms.setOrganizationInformation(organizationInformations);
			if (tempDetectorPublish.getDetectorId() != null) {
				deviceStatusHeaderCharms.setDeviceId(tempDetectorPublish.getDetectorId().getId());

				if (tempDetectorPublish.getDetectorStatusAttr() != null) {
					String replacedate = tempDetectorPublish.getDetectorStatusAttr().getLastStatusUpdate()
							.toString().replaceAll("[-:TZ]", "");
					deviceStatusHeaderCharms.setLastCommTime(getDateAndTime(replacedate));
					deviceStatusHeaderCharms.setDeviceStatus(getDeviceStatus(tempDetectorPublish));
				} else if (tempDetectorPublish.getDetectorStatusAttr() == null) {
					deviceStatusHeaderCharms.setDeviceStatus(DetectorConstants.STATUS_UNKNOWN);
				}
				deviceStatusHeaderCharms.setDeviceType(DetectorConstants.DETECTOR_DEVICE_TYPE);
				detectorStatusDetailsCharm.setDetectorStatusHeader(deviceStatusHeaderCharms);

				list.add(detectorStatusDetailsCharm);
			}

		}
	}

	private void detectorStatusMeasureNumber(DetectorStatusCharm detectorStatusCharm, DetectorPublish detectorPublish) {
		if (detectorPublish.getOutstationStatusAttr().getMeasureNumber() != null) {
			detectorStatusCharm
					.setMeasureNumber(detectorPublish.getOutstationStatusAttr().getMeasureNumber());
		}
	}

	private void detectorStatusDynamicSpeed(DetectorStatusCharm detectorStatusCharm, DetectorPublish detectorPublish) {
		if (detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed() != null) {
			detectorStatusCharm
					.setDynamicMaxSpeed(detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed());
		}
	}

	private void detectorPublishPowerSupply(DetectorStatusCharm detectorStatusCharm, DetectorPublish detectorPublish) {
		if (detectorPublish.getOutstationStatusAttr().getPowerSupply() != null) {
			switch (detectorPublish.getOutstationStatusAttr().getPowerSupply()) {
			case MAIN_POWER:
				detectorStatusCharm.setPowerSupply(PowerSource.MAIN_POWER);
				break;
			case BACKUP_POWER:
				detectorStatusCharm.setPowerSupply(PowerSource.BACKUP_POWER);
				break;
			}
		}
	}

	private void detectorPublishDeviceMode(DetectorStatusCharm detectorStatusCharm, DetectorPublish detectorPublish) {
		if (detectorPublish.getOutstationStatusAttr().getOutstationMode() != null) {
			switch (detectorPublish.getOutstationStatusAttr().getOutstationMode()) {
			case FORCED_IDLE:
				detectorStatusCharm.setDeviceMode(DeviceMode.FORCED_IDLE);
				break;
			case FORCED_LOCAL:
				detectorStatusCharm.setDeviceMode(DeviceMode.FORCED_LOCAL);
				break;
			case IDLE:
				detectorStatusCharm.setDeviceMode(DeviceMode.IDLE);
				break;
			case LOCAL:
				detectorStatusCharm.setDeviceMode(DeviceMode.LOCAL);
				break;
			case NONE:
				detectorStatusCharm.setDeviceMode(DeviceMode.NONE);
				break;
			case ONLINE:
				detectorStatusCharm.setDeviceMode(DeviceMode.ONLINE);
				break;
			}
		}
	}

	private void detectorStatusLastCommTime(DetectorPublish detectorPublish,
			DeviceStatusHeaderCharm deviceStatusHeaderCharm) {
		if (detectorPublish.getOutstationStatusAttr().getLastStatusUpdate() != null) {
			String replaceDate = detectorPublish.getOutstationStatusAttr().getLastStatusUpdate().toString()
					.replaceAll("[-:TZ]", "");
			deviceStatusHeaderCharm.setLastCommTime(getDateAndTime(replaceDate));
		}
	}

	/**
	 * getDeviceStatus 
	 * @param tempDetectorPublish of type DetectorPublish
	 * @return status
	 */
	public String getDeviceStatus(DetectorPublish tempDetectorPublish) {
		String status = null;
		switch (tempDetectorPublish.getDetectorStatusAttr().getDetectorState()) {
		case OK:
			status = "on";
			break;
		case NOK:
			status = "off";
			break;
		case DEGRADED:
			status = "marginal";
			break;
		default:
			status = "unknown";
			break;
		}
		return status;
	}

	/**
	 * DateTimeZone
	 * @param replaceDate of type String
	 * @return dateTime
	 */
	public DateTimeZone getDateAndTime(String replaceDate) {
		DateTimeZone dateTime = new DateTimeZone();
		dateTime.setDate(replaceDate.substring(0, 8));
		dateTime.setTime(replaceDate.substring(8, 14));
		return dateTime;
	}

	/**
	 * listFlag
	 * @param detectorStatusCharmList of type List<DetectorStatusCharm>
	 * @param detectorPublish of type DetectorPublish
	 * @return boolean
	 */
	public boolean listFlag(List<DetectorStatusCharm> detectorStatusCharmList, DetectorPublish detectorPublish) {
		for (DetectorStatusCharm detectorStatusCharm : detectorStatusCharmList) {
			if (detectorStatusCharm.getDetectorStationStatusHeader() != null
					&& detectorStatusCharm.getDetectorStationStatusHeader().getDeviceId() != null
					&& detectorStatusCharm.getDetectorStationStatusHeader().getDeviceId()
							.equals(detectorPublish.getOutstationId().getId())) {
				return true;
			}
		}
		return false;
	}
}
