package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.StatusInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcStatusInfoMapper implements PersistenceMapper<StatusInfo, TlcDeviceInfo> {

	@Override
	public StatusInfo toCdm(TlcDeviceInfo tlcDeviceInfo) {
		StatusInfo statusInfo = new StatusInfo();
		if (tlcDeviceInfo.getCurrentStatusAkt() != null) {
			statusInfo.setCurrentStatusAkt(tlcDeviceInfo.getCurrentStatusAkt());
		} else
			statusInfo.setCurrentStatusAkt("1");
		if (tlcDeviceInfo.getCurrentStatusAppl() != null) {
			statusInfo.setCurrentStatusAppl(tlcDeviceInfo.getCurrentStatusAppl());
		} else
			statusInfo.setCurrentStatusAppl("1");
		if (tlcDeviceInfo.getCurrentStatusBd() != null) {
			statusInfo.setCurrentStatusBd(tlcDeviceInfo.getCurrentStatusBd());
		} else
			statusInfo.setCurrentStatusBd("1");
		if (tlcDeviceInfo.getCurrentStatusBewaker() != null) {
			statusInfo.setCurrentStatusBewaker(tlcDeviceInfo.getCurrentStatusBewaker());
		} else
			statusInfo.setCurrentStatusBewaker("1");
		if (tlcDeviceInfo.getCurrentStatusCen() != null) {
			statusInfo.setCurrentStatusCen(tlcDeviceInfo.getCurrentStatusCen());
		} else
			statusInfo.setCurrentStatusCen("1");
		if (tlcDeviceInfo.getCurrentStatusKlok() != null) {
			statusInfo.setCurrentStatusKlok(tlcDeviceInfo.getCurrentStatusKlok());
		} else
			statusInfo.setCurrentStatusKlok("1");
		if (tlcDeviceInfo.getCurrentStatusProcess() != null) {
			statusInfo.setCurrentStatusProcess(tlcDeviceInfo.getCurrentStatusProcess());
		} else
			statusInfo.setCurrentStatusProcess("1");
		return statusInfo;
	}

	@Override
	public void toDomain(StatusInfo statusInfo, TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setCurrentStatusAkt(statusInfo.getCurrentStatusAkt());
		tlcDeviceInfo.setCurrentStatusAppl(statusInfo.getCurrentStatusAppl());
		tlcDeviceInfo.setCurrentStatusBd(statusInfo.getCurrentStatusBd());
		tlcDeviceInfo.setCurrentStatusBewaker(statusInfo.getCurrentStatusBewaker());
		tlcDeviceInfo.setCurrentStatusCen(statusInfo.getCurrentStatusCen());
		tlcDeviceInfo.setCurrentStatusKlok(statusInfo.getCurrentStatusKlok());
		tlcDeviceInfo.setCurrentStatusProcess(statusInfo.getCurrentStatusProcess());
	}

}
