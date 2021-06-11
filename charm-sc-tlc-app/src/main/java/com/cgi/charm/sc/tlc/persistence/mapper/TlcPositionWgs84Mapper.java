package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.PositionWgs84;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author CGI CHARM TEAM
 *
 */
@Component
public class TlcPositionWgs84Mapper implements PersistenceMapper<PositionWgs84, TlcDeviceInfo> {

	/* (non-Javadoc)
	 * @see com.cgi.charm.util.persistence.mappers.PersistenceMapper#toCdm(java.lang.Object)
	 */
	@Override
	public PositionWgs84 toCdm(TlcDeviceInfo tlcDeviceInfo) {
		PositionWgs84 positionWgs84 = new PositionWgs84();
		positionWgs84.setLatitude(tlcDeviceInfo.getLatitude());
		positionWgs84.setLongitude(tlcDeviceInfo.getLongitude());
		return positionWgs84;
	}

	/* (non-Javadoc)
	 * @see com.cgi.charm.util.persistence.mappers.PersistenceMapper#toDomain(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void toDomain(PositionWgs84 positionWgs84, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setLatitude(positionWgs84.getLatitude());
		tlcDeviceInfo.setLongitude(positionWgs84.getLongitude());
	}

}
