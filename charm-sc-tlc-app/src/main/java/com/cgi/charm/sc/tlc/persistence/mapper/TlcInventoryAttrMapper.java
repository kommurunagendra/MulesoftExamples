package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcInventoryAttr;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;
/**
 * @author CGI CHARM TEAM
 *
 */
@Component
public class TlcInventoryAttrMapper implements PersistenceMapper<TlcInventoryAttr, TlcDeviceInfo>{

	@Autowired
	TlcGenericAttrMapper tlcGenericAttrMapper;
	@Autowired
	TlcCommunicationMapper tlcCommunicationMapper;
	@Autowired
	TlcLocationMapper tlcLocationMapper;
	
	/* (non-Javadoc)
	 * @see com.cgi.charm.util.persistence.mappers.PersistenceMapper#toCdm(java.lang.Object)
	 */
	@Override
	public TlcInventoryAttr toCdm(TlcDeviceInfo tlcDeviceInfo) {
		TlcInventoryAttr tlcInventoryAttr = new TlcInventoryAttr();
		tlcInventoryAttr.setName(tlcDeviceInfo.getName());
		tlcInventoryAttr.setDescription(tlcDeviceInfo.getDescription());
		tlcInventoryAttr.setScanEnabled(tlcDeviceInfo.isScanEnabled());
		tlcInventoryAttr.setScanInterval(tlcDeviceInfo.getScanInterval());
		tlcInventoryAttr.setOwner(tlcDeviceInfo.getOwner());
		tlcInventoryAttr.setTlcGenericAttr(tlcGenericAttrMapper.toCdm(tlcDeviceInfo));
		tlcInventoryAttr.setTlcCommunication(tlcCommunicationMapper.toCdm(tlcDeviceInfo));
		tlcInventoryAttr.setTlcLocationAttr(tlcLocationMapper.toCdm(tlcDeviceInfo));
		return tlcInventoryAttr;
	}

	/* (non-Javadoc)
	 * @see com.cgi.charm.util.persistence.mappers.PersistenceMapper#toDomain(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void toDomain(TlcInventoryAttr tlcInventoryAttr, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setName(tlcInventoryAttr.getName());
		tlcDeviceInfo.setDescription(tlcInventoryAttr.getDescription());
		tlcDeviceInfo.setScanEnabled(tlcInventoryAttr.isScanEnabled());
		tlcDeviceInfo.setScanInterval(tlcInventoryAttr.getScanInterval());
		tlcDeviceInfo.setOwner(tlcInventoryAttr.getOwner());
		tlcGenericAttrMapper.toDomain(tlcInventoryAttr.getTlcGenericAttr(), tlcDeviceInfo);
		tlcCommunicationMapper.toDomain(tlcInventoryAttr.getTlcCommunication(), tlcDeviceInfo);
		tlcLocationMapper.toDomain(tlcInventoryAttr.getTlcLocationAttr(), tlcDeviceInfo);
	}

	
}
