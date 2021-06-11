package com.cgi.charm.sc.tlc.persistence.mapper;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcGenericAttr;
import com.cgi.charm.cdm.tlc.TlcType;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.util.TlcUtil;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcGenericAttrMapper implements PersistenceMapper<TlcGenericAttr, TlcDeviceInfo> {
	private static final Logger logger = Logger.getLogger(TlcGenericAttrMapper.class);

	@Override
	public TlcGenericAttr toCdm(TlcDeviceInfo tlcDeviceInfo) {
		TlcGenericAttr tlcGenericAttr = new TlcGenericAttr();
		tlcGenericAttr.setTlcType(TlcType.fromValue(tlcDeviceInfo.getTlcType()));
		tlcGenericAttr.setIntersectionNumber(tlcDeviceInfo.getIntersectionNumber());
		tlcGenericAttr.setIntersectionDescription(tlcDeviceInfo.getIntersectionDescription());
		Timestamp dateTime = tlcDeviceInfo.getDateTime();
		tlcGenericAttr.setIveraVersion(tlcDeviceInfo.getIveraVersion());
		tlcGenericAttr.setApplicationId(tlcDeviceInfo.getApplicationId());
		tlcGenericAttr.setDateTime(TlcUtil.toInstant(dateTime));
		return tlcGenericAttr;
	}

	@Override
	public void toDomain(TlcGenericAttr tlcGenericAttr, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setTlcType(tlcGenericAttr.getTlcType().toString());
		tlcDeviceInfo.setIntersectionNumber(tlcGenericAttr.getIntersectionNumber());
		tlcDeviceInfo.setIntersectionDescription(tlcGenericAttr.getIntersectionDescription());
		tlcDeviceInfo.setIveraVersion(tlcGenericAttr.getIveraVersion());
		tlcDeviceInfo.setApplicationId(tlcGenericAttr.getApplicationId());
		Date dateUtil = Date.from(tlcGenericAttr.getDateTime());

		java.sql.Timestamp timeStampMessageCreateDate = new java.sql.Timestamp(dateUtil.getTime());
		tlcDeviceInfo.setDateTime(timeStampMessageCreateDate);
	}

}
