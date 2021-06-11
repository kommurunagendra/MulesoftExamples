/*
 *  Copyright (c) 2017 Pavani Rudravaram.All Rights Reserved.
 *  
 *  TlcOtmcDeviceStatusAttrMapper.java
 */
package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.DeployedBy;
import com.cgi.charm.cdm.tlc.Availability;
import com.cgi.charm.cdm.tlc.DeviceState;
import com.cgi.charm.cdm.tlc.OtmcDeviceStatus;
import com.cgi.charm.cdm.tlc.OtmcDeviceStatus.Parameters;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

@Component
public class TlcOtmcDeviceStatusAttrMapper implements
PersistenceMapper<OtmcDeviceStatus, TlcDeviceInfo> {

	@Override
	public OtmcDeviceStatus toCdm(TlcDeviceInfo deviceInfo) {
		 OtmcDeviceStatus otmcDeviceStatus = new OtmcDeviceStatus();
	        if(null != deviceInfo.getAvailability()) {
	            otmcDeviceStatus.setAvailability(Availability.fromValue(deviceInfo.getAvailability()));
	        }
	        if(null != deviceInfo.getDeviceState()) {
	            otmcDeviceStatus.setDeviceState(DeviceState.fromValue(deviceInfo.getDeviceState()));
	        }
	        if(null != deviceInfo.getDeployedByList()) {
	            otmcDeviceStatus.setDeployedBy(deviceInfo.getDeployedByList());
	        }
	        Parameters parameters = new OtmcDeviceParametersMapper().toCdm(deviceInfo);
	        if(null != parameters) {
	            otmcDeviceStatus.setParameters(parameters);
	        }else {
	            otmcDeviceStatus.setParameters(new Parameters());
	        }
	        return otmcDeviceStatus;
	}
	
	 @Override
	    public void toDomain(OtmcDeviceStatus otmcDeviceStatus, TlcDeviceInfo deviceInfo) {       
	    	deviceInfo.setAvailability(otmcDeviceStatus.getAvailability().value());
	        deviceInfo.setDeviceState(otmcDeviceStatus.getDeviceState().value());  
	        List<DeployedBy> deployedBy = new ArrayList<>();
	        if(otmcDeviceStatus != null)
	        deployedBy.addAll(otmcDeviceStatus.getDeployedBy());
	        deviceInfo.setDeployedByList(deployedBy);
	        if(otmcDeviceStatus != null)
	        new OtmcDeviceParametersMapper().toDomain(otmcDeviceStatus.getParameters(), deviceInfo);
	    }
}
