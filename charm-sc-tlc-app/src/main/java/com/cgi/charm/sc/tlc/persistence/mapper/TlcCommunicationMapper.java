package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcCommunication;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
//import com.cgi.charm.util.mappers.Mapper;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;
/**
 * @author shyam.avula
 *
 */
@Component
public class TlcCommunicationMapper implements
PersistenceMapper<TlcCommunication, TlcDeviceInfo> {

	@Override
	public TlcCommunication toCdm(TlcDeviceInfo tlcDeviceInfo) {
		TlcCommunication tlcCommunication = new TlcCommunication();
		tlcCommunication.setPhonenrCentral(tlcDeviceInfo.getPhonenrCentral());
		tlcCommunication.setIpnrCentral(tlcDeviceInfo.getIpnrCentral());
		tlcCommunication.setPortnumberClient(tlcDeviceInfo.getPortnumberClient());//setPortnr(tlcDeviceInfo.getPortnr());
		tlcCommunication.setPortnumberServerAsb(tlcDeviceInfo.getPortnumberServerAsb());
		tlcCommunication.setTriggerevents(tlcDeviceInfo.getTriggerevents());
		tlcCommunication.setIpnrVri(tlcDeviceInfo.getIpnrVri());
		tlcCommunication.setPinCode(tlcDeviceInfo.getPinCode());
		return tlcCommunication;
	}

	@Override
	public void toDomain(TlcCommunication tlcCommunication,
	TlcDeviceInfo tlcDeviceInfo) {
		tlcDeviceInfo.setPhonenrCentral(tlcCommunication.getPhonenrCentral());
		tlcDeviceInfo.setIpnrCentral(tlcCommunication.getIpnrCentral());
		tlcDeviceInfo.setPortnumberClient(tlcCommunication.getPortnumberClient()/*getPortnr()*/);
		tlcDeviceInfo.setPortnumberServerAsb(tlcCommunication.getPortnumberServerAsb());
		tlcDeviceInfo.setTriggerevents(tlcCommunication.getTriggerevents());
		tlcDeviceInfo.setIpnrVri(tlcCommunication.getIpnrVri());
		tlcDeviceInfo.setPinCode(tlcCommunication.getPinCode());
	}

}
