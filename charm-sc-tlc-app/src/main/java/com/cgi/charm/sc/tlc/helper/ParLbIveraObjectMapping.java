package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.Parameter;
import com.cgi.charm.cdm.tlc.IveraObject.Parameters;
import com.cgi.charm.sc.tlc.constants.TlcConstants;

/**
 * @author pavan.chenna
 *
 */
@Component
public class ParLbIveraObjectMapping {
	
	
	/** map the IveraObject
	 * @param payload to map the actual payload with the cdm
	 * @return Object returns IveraObject
	 */
	public IveraObject map(String payload) {
		String[] payloadSplit = payload.split(",");
		Parameter parameter = null;
		IveraObject iveraObject = new IveraObject();
		iveraObject.setName(TlcConstants.PAR_LB_NAME);
		iveraObject.setDisplayname(TlcConstants.PAR_LB_DISPALY_NAME);
		Parameters parameters = new Parameters();
		List<Parameter> parameterList = new ArrayList<>();
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_DATE);
		parameter.setValue(payloadSplit[0].split(":")[0]);
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_TIME);
		parameter.setValue(payloadSplit[0].split(":")[1]);
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_CONFIRMED);
		if (payloadSplit[1].equals("1")) {
			parameter.setValue("true");
		} else {
			parameter.setValue("false");
		}
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_OBJECT);
		parameter.setValue(payloadSplit[2].split("/")[0]);
		parameterList.add(parameter);
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_ELEMENT);
		parameter.setValue(payloadSplit[2].split("/")[1].split("=")[0]);
		parameterList.add(parameter);
		if(payloadSplit.length>3){
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_ELEMENT_VALUE);
		StringBuilder sb=new StringBuilder();
		for(int i=3;i<=payloadSplit.length-1;i++){
			sb.append(payloadSplit[i]);
			sb.append(",");
		}
		parameter.setValue(sb.toString());
		parameterList.add(parameter);
		}
		parameter = new Parameter();
		parameter.setKey(TlcConstants.PAR_LB_EVENT_ELEMENT_NEW_VALUE);
		parameter.setValue(payloadSplit[2].split("/")[1].split("=")[1]);
		parameterList.add(parameter);
		parameters.setParameter(parameterList);
		iveraObject.setParameters(parameters);
		return iveraObject;
	}
}
