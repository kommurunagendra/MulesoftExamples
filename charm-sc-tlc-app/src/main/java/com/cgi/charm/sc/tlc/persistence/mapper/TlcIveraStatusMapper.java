package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.IveraStatus;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcIveraStatusMapper implements PersistenceMapper<IveraStatus, TlcDeviceInfo> {
	@Autowired
	TlcSupportedObjectsMapper tlcSupportedObjectsMapper;

	@Override
	public IveraStatus toCdm(TlcDeviceInfo tlcDeviceInfo) {
		IveraStatus iveraStatus = new IveraStatus();
		iveraStatus.setSupportedObjects(tlcSupportedObjectsMapper
				.toCdm(tlcDeviceInfo));
		iveraStatus.setIveraObject(tlcDeviceInfo.getIveraObject());
		if(iveraStatus.getSupportedObjects()!=null ){
		return iveraStatus;}
		else return null;
	}
	
	@Override
	public void toDomain(IveraStatus iveraStatus,TlcDeviceInfo tlcDeviceInfo)
	{
		tlcSupportedObjectsMapper.toDomain(iveraStatus.getSupportedObjects(),tlcDeviceInfo);
		tlcDeviceInfo.setIveraObject(iveraStatus.getIveraObject());
	}

}
