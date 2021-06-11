package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcLocationAttr;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcLocationMapper implements PersistenceMapper<TlcLocationAttr, TlcDeviceInfo> {
     
	@Autowired
	TlcPositionWgs84Mapper tlcPositionWgs84Mapper;
	
	@Override
	public TlcLocationAttr toCdm(TlcDeviceInfo tlcDeviceInfo) {
		TlcLocationAttr tlcLocationAttr = new TlcLocationAttr();
		tlcLocationAttr.setPositionBps(tlcDeviceInfo.getPositionBps());
		tlcLocationAttr.setPositionWgs84(tlcPositionWgs84Mapper.toCdm(tlcDeviceInfo)); 
		return tlcLocationAttr;
	}


	@Override
	public void toDomain(TlcLocationAttr tlcLocationAttr, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setPositionBps(tlcLocationAttr.getPositionBps());
		tlcPositionWgs84Mapper.toDomain(tlcLocationAttr.getPositionWgs84(), tlcDeviceInfo);
	}

	
}
