package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Action;
import com.cgi.charm.cdm.tlc.Id;
import com.cgi.charm.cdm.tlc.TlcInventory;
import com.cgi.charm.cdm.tlc.TlcInventoryAttr;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcInventoryMapper implements PersistenceMapper<TlcInventory, TlcDeviceInfo> {
    @Autowired
	TlcInventoryAttrMapper tlcInventoryAttrMapper;

	@Override
	public TlcInventory toCdm(TlcDeviceInfo tlcDeviceInfo) {
		return new TlcInventory(Action.ADD,
				new Id(String.valueOf(tlcDeviceInfo.getId())),
				tlcInventoryAttrMapper.toCdm(tlcDeviceInfo));
	}	
	@Override
	public void toDomain(TlcInventory tlcInventory,
			TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setTlcId(tlcInventory.getTlcId().getId());
		tlcInventoryAttrMapper.toDomain(tlcInventory.getTlcInventoryAttr(),tlcDeviceInfo);
	}

}
