package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.Parameter;
import com.cgi.charm.cdm.tlc.IveraObject.Parameters;
import com.cgi.charm.sc.tlc.constants.EventCategoryAndDetails;
import com.cgi.charm.sc.tlc.constants.TlcConstants;

/**
 * @author pavan.chenna
 *
 */
@Component
public class VriLbIveraObjectMapping {

	/**
	 * the IveraObject
	 * 
	 * @param payload
	 *            Payload
	 * @return Object IveraObject returns
	 */
	public IveraObject map(String payload) {
		String[] payloadSplit = payload.split(",");
		Parameter parameter = null;
		IveraObject iveraObject = new IveraObject();
		iveraObject.setName(TlcConstants.VRI_LB_NAME);
		iveraObject.setDisplayname(TlcConstants.VRI_LB_DISPALY_NAME);
		Parameters parameters = new Parameters();
		List<Parameter> parameterList = new ArrayList<>();
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_DATE);
		parameter.setValue(payloadSplit[0].split(":")[0]);
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_TIME);
		parameter.setValue(payloadSplit[0].split(":")[1]);
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_CONFIRMED);
		if (payloadSplit[1].equals("1")) {
			parameter.setValue("true");
		} else {
			parameter.setValue("false");
		}
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_CODE);
		parameter.setValue(payloadSplit[2]);
		parameterList.add(parameter);
		long eventcode = Long.parseLong(payloadSplit[2]);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_DESCRIPTION);
		if (eventcode >= 200000 && eventcode <= 299999) {
			parameter.setValue("false");
			parameterList.add(parameter);
			parameter = new Parameter();
			parameter.setKey(TlcConstants.VRI_LB_EVENT_DETAILS);
			String deviceSpecificDetails = deviceSpecificEventsMapping(payloadSplit);
			parameter.setValue(deviceSpecificDetails);
			parameterList.add(parameter);
			parameters.setParameter(parameterList);
			iveraObject.setParameters(parameters);
			return iveraObject;
		}
		TriggerEventDetails triggerEventDetails = EventCategoryAndDetails
				.getEventCategoryAndDetailsByeventCode(payloadSplit[2]).getEventDetails();
		parameter.setValue(triggerEventDetails.getDescription());
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.VRI_LB_EVENT_DETAILS);
		String eventDetails = deviceEventsMapping(triggerEventDetails, payloadSplit);
		parameter.setValue(eventDetails);
		parameterList.add(parameter);
		parameters.setParameter(parameterList);
		iveraObject.setParameters(parameters);
		return iveraObject;
	}

	private String deviceSpecificEventsMapping(String[] payloadSplit) {
		StringBuilder sb = new StringBuilder();
		int appendValue = 1;
		for (int i = 3; i < payloadSplit.length; i++) {
			sb.append("veld");
			sb.append(appendValue);
			sb.append(":");
			sb.append(payloadSplit[i]);
			sb.append(",");
			appendValue++;
		}
		return sb.toString();
	}

	private String deviceEventsMapping(TriggerEventDetails triggerEventDetails, String[] payloadSplit) {
		StringBuilder sb = new StringBuilder();
		int details = 3;
		List<String> list = triggerEventDetails.getDetails();
		for (int i = 0; i < list.size() - 1; i++) {
			if (details < payloadSplit.length) {
				sb.append(list.get(i));
				sb.append(":");
				sb.append(payloadSplit[details]);
				sb.append(",");
				details++;
			}
		}
		return sb.toString();
	}
}
