package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.tmdd._3.messages.CommunicationStatistics;
import org.tmdd._3.messages.DateTimeZone;
import org.tmdd._3.messages.DetectorStatus;
import org.tmdd._3.messages.DetectorStatusCharm;
import org.tmdd._3.messages.DetectorStatusDetailsCharm;
import org.tmdd._3.messages.DeviceMode;
import org.tmdd._3.messages.DeviceStatusHeaderCharm;
import org.tmdd._3.messages.LocalOverride;
import org.tmdd._3.messages.OrganizationInformation;
import org.tmdd._3.messages.OutstationTrafficFlow;
import org.tmdd._3.messages.PowerSource;

import com.cgi.charm.cdm.si.detector.DetectorPublish;
import com.cgi.charm.cdm.si.detector.TrafficFlow;
import com.cgi.charm.dynac.constants.DetectorConstants;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class DetectorStatusPublishUtil {

	private OrganizationInformation organizationInformation;

	/**
	 * populateStatusPublish
	 * @param detectorPublishList of type List<DetectorPublish>
	 * @return detectorStatusCharmList
	 */
	public List<DetectorStatusCharm> populateStatusPublish(List<DetectorPublish> detectorPublishList) {
		organizationInformation = new OrganizationInformation();
		this.organizationInformation.setOrganizationId(DetectorConstants.ORGANIZATION_ID);
		Map<String, DetectorStatusCharm> detectorStatusCharmMap = new LinkedHashMap<>();

		// add Fep & outstations
		for (DetectorPublish detectorPublish : detectorPublishList) {

			addFepStatus(detectorPublish, detectorStatusCharmMap);

			addOSStatus(detectorPublish, detectorStatusCharmMap);

		}

		// add detectors
		addDetectors(detectorPublishList, detectorStatusCharmMap);

		return detectorStatusCharmMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

	}

	/**
	 * @param detectorPublishList
	 * @param detectorStatusCharmMap
	 */
	private void addDetectors(List<DetectorPublish> detectorPublishList,
			Map<String, DetectorStatusCharm> detectorStatusCharmMap) {

		for (DetectorPublish tempDetectorPublish : detectorPublishList) {
			DetectorStatusCharm detectorStatusCharm = findOrCreateParentDetectorStatusCharm(detectorStatusCharmMap,
					tempDetectorPublish);
			if (tempDetectorPublish.getDetectorId() != null) {
				DetectorStatusDetailsCharm detectorStatusDetailsCharm = new DetectorStatusDetailsCharm();
				DeviceStatusHeaderCharm deviceStatusHeaderCharms = new DeviceStatusHeaderCharm();

				deviceStatusHeaderCharms.setOrganizationInformation(this.organizationInformation);

				deviceStatusHeaderCharms.setDeviceId(tempDetectorPublish.getDetectorId().getId());
				if (tempDetectorPublish.getDetectorStatusAttr() != null) {
					
					String replacedate = tempDetectorPublish.getDetectorStatusAttr().getLastStatusUpdate().toString();
					deviceStatusHeaderCharms.setLastCommTime(getDateAndTime(replacedate));
					deviceStatusHeaderCharms.setDeviceStatus(getDeviceStatus(tempDetectorPublish));
				} else {
					deviceStatusHeaderCharms.setDeviceStatus(DetectorConstants.STATUS_UNKNOWN);
				}
				deviceStatusHeaderCharms.setDeviceType(DetectorConstants.DETECTOR_DEVICE_TYPE);
				detectorStatusDetailsCharm.setDetectorStatusHeader(deviceStatusHeaderCharms);

				if (detectorStatusCharm.getDetectorList() == null) {
					detectorStatusCharm.setDetectorList(new DetectorStatus.DetectorList());
				}

				detectorStatusCharm.getDetectorList().getDetector().add(detectorStatusDetailsCharm);

			}
		}

	}

	/**
	 * @param detectorStatusCharmMap
	 * @return osDetectorStatusCharm
	 */
	private DetectorStatusCharm findOrCreateParentDetectorStatusCharm(
			Map<String, DetectorStatusCharm> detectorStatusCharmMap, DetectorPublish detectorPublish) {

		DetectorStatusCharm detectorStatusCharm = null;

		if (detectorPublish.getOutstationId() != null
				&& detectorPublish.getOutstationId().getId() != null) {
			
			
			String outStationId = detectorPublish.getOutstationId().getId();
			detectorStatusCharm = new DetectorStatusCharm();

			DeviceStatusHeaderCharm deviceStatusHeaderCharm = new DeviceStatusHeaderCharm();

			deviceStatusHeaderCharm.setOrganizationInformation(this.organizationInformation);
			deviceStatusHeaderCharm.setDeviceId(outStationId);
			LocalOverride localOverride = new LocalOverride();

			if (detectorPublish.getOutstationStatusAttr() != null) {
				if (detectorPublish.getOutstationStatusAttr().getLastStatusUpdate() != null) {
					String replaceDate = detectorPublish.getOutstationStatusAttr().getLastStatusUpdate().toString();
					deviceStatusHeaderCharm.setLastCommTime(getDateAndTime(replaceDate));
				}
				detectorStatusCharm
						.setAuxPowerAvailable(detectorPublish.getOutstationStatusAttr().isAuxPowerAvailable());
				if (detectorPublish.getOutstationStatusAttr().getOutstationMode() != null) {
					detectorStatusCharmDeviceMode(detectorPublish, detectorStatusCharm);
				}
				if (detectorPublish.getOutstationStatusAttr().getPowerSupply() != null) {
					detectorStatusCharmPowerSupply(detectorPublish, detectorStatusCharm);
				}
				detectorStatusCharm.setColdStart(detectorPublish.getOutstationStatusAttr().isReportColdStart());
				detectorStatusCharm.setFatalError(detectorPublish.getOutstationStatusAttr().isFatalError());
				detectorStatusCharm.setMaintenanceCode(detectorPublish.getOutstationStatusAttr().getMaintenanceCode());
				detectorStatusCharm.setAidOverride(detectorPublish.getOutstationStatusAttr().isAidOverride());
				List<TrafficFlow> trafficList = detectorPublish.getOutstationStatusAttr().getTrafficFlow();
				List<OutstationTrafficFlow> outstationTrafficFlowList = new ArrayList<>();
				trafficList.stream().forEach(tlist -> {
					OutstationTrafficFlow outstationTrafficFlow = new OutstationTrafficFlow();
					outstationTrafficFlow.setAidActive(tlist.isAidActive());
					outstationTrafficFlow.setAidRecommendation(tlist.getAidRecommendation());
					outstationTrafficFlow.setTrafficFlowNumber(tlist.getTrafficFlowNumber());
					outstationTrafficFlowList.add(outstationTrafficFlow);

				});
				detectorStatusCharm.getTrafficFlow().addAll(outstationTrafficFlowList);
				localOverride.setChannel1(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel1());
				localOverride.setChannel2(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel2());
				localOverride.setChannel3(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel3());
				localOverride.setChannel4(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel4());

				if (detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed() != null) {
					detectorStatusCharm
							.setDynamicMaxSpeed(detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed());
				}
				if (detectorPublish.getOutstationStatusAttr().getMeasureNumber() != null) {
					detectorStatusCharm.setMeasureNumber(detectorPublish.getOutstationStatusAttr().getMeasureNumber());
				}
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

			detectorStatusCharmMap.put(outStationId, detectorStatusCharm);
		} else {
			// check if there is common parent detectorStatusCharm is available
			detectorStatusCharm = detectorStatusCharmMap.get("detectors");
			if (detectorStatusCharm == null) {
				detectorStatusCharm = new DetectorStatusCharm();
				detectorStatusCharmMap.put("detectors", detectorStatusCharm);
			}
		}
		return detectorStatusCharm;
	}

	private void detectorStatusCharmPowerSupply(DetectorPublish detectorPublish,
			DetectorStatusCharm detectorStatusCharm) {
		switch (detectorPublish.getOutstationStatusAttr().getPowerSupply()) {
		case MAIN_POWER:
			detectorStatusCharm.setPowerSupply(PowerSource.MAIN_POWER);
			break;
		case BACKUP_POWER:
			detectorStatusCharm.setPowerSupply(PowerSource.BACKUP_POWER);
			break;
		}
	}

	private void detectorStatusCharmDeviceMode(DetectorPublish detectorPublish,
			DetectorStatusCharm detectorStatusCharm) {
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

	/**
	 * @param detectorPublish
	 * @param detectorStatusCharmMap
	 */
	private void addOSStatus(DetectorPublish detectorPublish, Map<String, DetectorStatusCharm> detectorStatusCharmMap) {
		
		if (detectorPublish.getOutstationId() != null
				&& detectorStatusCharmMap.get(detectorPublish.getOutstationId().getId()) == null) {

			String outStationId = detectorPublish.getOutstationId().getId();

			DetectorStatusCharm detectorStatusCharm = new DetectorStatusCharm();
			DeviceStatusHeaderCharm deviceStatusHeaderCharm = new DeviceStatusHeaderCharm();

			deviceStatusHeaderCharm.setOrganizationInformation(this.organizationInformation);
			deviceStatusHeaderCharm.setDeviceId(outStationId);
			LocalOverride localOverride = new LocalOverride();

			if (detectorPublish.getOutstationStatusAttr() != null) {
				if (detectorPublish.getOutstationStatusAttr().getLastStatusUpdate() != null) {
					String replaceDate = detectorPublish.getOutstationStatusAttr().getLastStatusUpdate().toString();
					deviceStatusHeaderCharm.setLastCommTime(getDateAndTime(replaceDate));
				}
				detectorStatusCharm
						.setAuxPowerAvailable(detectorPublish.getOutstationStatusAttr().isAuxPowerAvailable());
				if (detectorPublish.getOutstationStatusAttr().getOutstationMode() != null) {
					detectorStatusCharmDeviceMode(detectorPublish, detectorStatusCharm);
				}
				if (detectorPublish.getOutstationStatusAttr().getPowerSupply() != null) {
					detectorStatusCharmPowerSupply(detectorPublish, detectorStatusCharm);
				}
				detectorStatusCharm.setColdStart(detectorPublish.getOutstationStatusAttr().isReportColdStart());
				detectorStatusCharm.setFatalError(detectorPublish.getOutstationStatusAttr().isFatalError());
				detectorStatusCharm.setMaintenanceCode(detectorPublish.getOutstationStatusAttr().getMaintenanceCode());
				detectorStatusCharm.setAidOverride(detectorPublish.getOutstationStatusAttr().isAidOverride());
				List<TrafficFlow> trafficList = detectorPublish.getOutstationStatusAttr().getTrafficFlow();
				List<OutstationTrafficFlow> outstationTrafficFlowList = new ArrayList<>();
				trafficList.stream().forEach(tlist -> {
					OutstationTrafficFlow outstationTrafficFlow = new OutstationTrafficFlow();
					outstationTrafficFlow.setAidActive(tlist.isAidActive());
					outstationTrafficFlow.setAidRecommendation(tlist.getAidRecommendation());
					outstationTrafficFlow.setTrafficFlowNumber(tlist.getTrafficFlowNumber());
					outstationTrafficFlowList.add(outstationTrafficFlow);

				});
				detectorStatusCharm.getTrafficFlow().addAll(outstationTrafficFlowList);
				localOverride.setChannel1(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel1());
				localOverride.setChannel2(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel2());
				localOverride.setChannel3(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel3());
				localOverride.setChannel4(detectorPublish.getOutstationStatusAttr().getLocalOverride().isChannel4());

				if (detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed() != null) {
					detectorStatusCharm
							.setDynamicMaxSpeed(detectorPublish.getOutstationStatusAttr().getDynamicMaxSpeed());
				}
				if (detectorPublish.getOutstationStatusAttr().getMeasureNumber() != null) {
					detectorStatusCharm.setMeasureNumber(detectorPublish.getOutstationStatusAttr().getMeasureNumber());
				}
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

			// add it to map
			detectorStatusCharmMap.put(outStationId, detectorStatusCharm);
		}

	}

	/**
	 * map fep status attributes to detectoStatusMsg
	 * 
	 * @param detectorPublish
	 * @param detectorStatusCharmMap
	 */
	private void addFepStatus(DetectorPublish detectorPublish,
			Map<String, DetectorStatusCharm> detectorStatusCharmMap) {

		if (detectorPublish.getFepId() != null
				&& detectorStatusCharmMap.get(detectorPublish.getFepId().getId()) == null) {

			if(detectorPublish.getFepStatusAttr() != null){
				if (detectorPublish.getFepStatusAttr().getLastStatusUpdate() != null
					|| detectorPublish.getFepStatusAttr().getFepMode() != null) {
				DetectorStatusCharm fepStatusCharm = new DetectorStatusCharm();
				DeviceStatusHeaderCharm fepStatusHeaderCharm = new DeviceStatusHeaderCharm();
				fepStatusHeaderCharm.setOrganizationInformation(this.organizationInformation);
				fepStatusHeaderCharm.setDeviceId(detectorPublish.getFepId().getId());
				fepStatusHeaderCharm.setDeviceStatus(DetectorConstants.STATUS_UNKNOWN);
				fepStatusHeaderCharm.setLastCommTime(
						getDateAndTime(detectorPublish.getFepStatusAttr().getLastStatusUpdate().toString()));
				fepStatusCharm.setDetectorStationStatusHeader(fepStatusHeaderCharm);
				fepStatusCharm.setDeviceMode(DeviceMode.fromValue(detectorPublish.getFepStatusAttr().getFepMode()));
				detectorStatusCharmMap.put(detectorPublish.getFepId().getId(), fepStatusCharm);
			}
			}
		}
	}

	/**
	 * @param tempDetectorPublish
	 * @return status
	 */
	private String getDeviceStatus(DetectorPublish tempDetectorPublish) {
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
	 * @param dateInStr
	 * @return dateTime
	 */
	private DateTimeZone getDateAndTime(String dateInStr) {
		String replaceDate = dateInStr.replaceAll("[-:TZ]", "");
		DateTimeZone dateTime = new DateTimeZone();
		dateTime.setDate(replaceDate.substring(0, 8));
		dateTime.setTime(replaceDate.substring(8, 14));
		return dateTime;
	}

}
