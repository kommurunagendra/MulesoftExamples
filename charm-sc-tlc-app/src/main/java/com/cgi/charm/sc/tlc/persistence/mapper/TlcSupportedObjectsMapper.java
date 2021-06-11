package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.SupportedObjects;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcSupportedObjectsMapper implements
		PersistenceMapper<SupportedObjects, TlcDeviceInfo> {


	@Override
	public SupportedObjects toCdm(TlcDeviceInfo tlcDeviceInfo) {
		SupportedObjects supportedObjects = new SupportedObjects();
		if(tlcDeviceInfo.getBb0()!=null){
		supportedObjects.setBb0(tlcDeviceInfo.getBb0());}
		else return null;
		if(tlcDeviceInfo.getBb1()!=null){
		supportedObjects.setBb1(tlcDeviceInfo.getBb1());}
		else return null;
		return supportedObjects;
	}
	
	@Override
	public void toDomain(SupportedObjects supportedObjects,TlcDeviceInfo tlcDeviceInfo)
	{
		tlcDeviceInfo.setBb0(supportedObjects.getBb0());
		tlcDeviceInfo.setBb1(supportedObjects.getBb1());
	}

}
