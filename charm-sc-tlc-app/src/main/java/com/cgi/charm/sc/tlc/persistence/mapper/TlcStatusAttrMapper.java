package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcStatusAttr;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcStatusAttrMapper implements
		PersistenceMapper<TlcStatusAttr, TlcDeviceInfo> {
	@Autowired
	TlcStatusInfoMapper tlcStatusInfoMapper;
	@Autowired
	TlcErrorStatusMapper tlcErrorStatusMapper;
	@Autowired
	TlcIveraStatusMapper tlcIveraStatusMapper;
	@Autowired
	TlcOtmcDeviceStatusAttrMapper tlcOtmcDeviceStatusAttrMapper;

	
	@Override
	public TlcStatusAttr toCdm(TlcDeviceInfo tlcDeviceInfo) {
		TlcStatusAttr tlcStatusAttr = new TlcStatusAttr();
		tlcStatusAttr.setStatusInfo(tlcStatusInfoMapper.toCdm(tlcDeviceInfo));
		tlcStatusAttr.setErrorStatus(tlcErrorStatusMapper.toCdm(tlcDeviceInfo));
		tlcStatusAttr.setIveraStatus(tlcIveraStatusMapper.toCdm(tlcDeviceInfo));
		tlcStatusAttr.setOtmcDeviceStatus(tlcOtmcDeviceStatusAttrMapper.toCdm(tlcDeviceInfo));
		return tlcStatusAttr;
	}
	
	@Override
	public void toDomain(TlcStatusAttr tlcStatusAttr,TlcDeviceInfo tlcDeviceInfo) {
		tlcStatusInfoMapper.toDomain(tlcStatusAttr.getStatusInfo(), tlcDeviceInfo);
		tlcErrorStatusMapper.toDomain(tlcStatusAttr.getErrorStatus(), tlcDeviceInfo);
		tlcIveraStatusMapper.toDomain(tlcStatusAttr.getIveraStatus(), tlcDeviceInfo);
		tlcOtmcDeviceStatusAttrMapper.toDomain(tlcStatusAttr.getOtmcDeviceStatus(), tlcDeviceInfo);
	}
}
