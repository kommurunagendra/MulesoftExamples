/*
 *  Copyright (c) 2017 Pavani Rudravaram.All Rights Reserved.
 *  
 *  OtmcDeviceParametersMapper.java
 */
package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.OtmcDeviceStatus.Parameters;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

@Component
public class OtmcDeviceParametersMapper implements PersistenceMapper<Parameters, TlcDeviceInfo> {

	 @Override
	    public Parameters toCdm(TlcDeviceInfo deviceInfo)  {
	        Parameters parameters = null;
	            parameters = new Parameters();
	            if (null != deviceInfo.getAvailabilityExplanation()) {
	                parameters.setAvailabilityExplanation(deviceInfo.getAvailabilityExplanation());
	            }
	            if (null != deviceInfo.getStateSourceDescription()) {
	                parameters.setStateSourceDescription(deviceInfo.getStateSourceDescription());
	            }
	            if (null != deviceInfo.getStateExplanation()) {
	                parameters.setStateExplanation(deviceInfo.getStateExplanation());
	            }
	            if (null != deviceInfo.getEncodedParameters()) {
	                parameters.setEncodedParameters(deviceInfo.getEncodedParameters());
	            }
	        return parameters;
	    }
	 
	  @Override
	    public void toDomain(Parameters parameters, TlcDeviceInfo deviceInfo) {
	        deviceInfo.setAvailabilityExplanation(parameters.getAvailabilityExplanation());
	        deviceInfo.setStateSourceDescription(parameters.getStateSourceDescription());
	        deviceInfo.setStateExplanation(parameters.getStateExplanation());
	        deviceInfo.setEncodedParameters(parameters.getEncodedParameters());
	    }
}
