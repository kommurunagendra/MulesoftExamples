package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.ErrorStatus;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcErrorStatusMapper implements PersistenceMapper<ErrorStatus, TlcDeviceInfo> {

	@Override
	public ErrorStatus toCdm(TlcDeviceInfo tlcDeviceInfo) {
		ErrorStatus errorStatus = new ErrorStatus();
		if (tlcDeviceInfo.getApplication() != null) {
			errorStatus.setApplication(tlcDeviceInfo.getApplication());
		} else
			errorStatus.setApplication("1");
		if (tlcDeviceInfo.getDetection() != null) {
			errorStatus.setDetection(tlcDeviceInfo.getDetection());
		} else
			errorStatus.setDetection("1");
		if (tlcDeviceInfo.getLamps() != null) {
			errorStatus.setLamps(tlcDeviceInfo.getLamps());
		} else
			errorStatus.setLamps("1");
		if (tlcDeviceInfo.getPublicTransportation() != null) {
			errorStatus.setPublicTransportation(tlcDeviceInfo.getPublicTransportation());
		} else
			errorStatus.setPublicTransportation("1");
		return errorStatus;
	}

	@Override
	public void toDomain(ErrorStatus errorStatus, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setApplication(errorStatus.getApplication());
		tlcDeviceInfo.setDetection(errorStatus.getDetection());
		tlcDeviceInfo.setLamps(errorStatus.getLamps());
		tlcDeviceInfo.setPublicTransportation(errorStatus.getPublicTransportation());
	}

}
