package com.cgi.charm.sc.tlc.helper;

import java.util.List;

import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;

/**
 * @author CHARM CGI
 *
 */
public class TlcDeviceInfoWrapper {

	private List<TlcDeviceInfo> deviceInfoList;
	private Uuid messageId;

	public List<TlcDeviceInfo> getDeviceInfoList() {
		return deviceInfoList;
	}

	public void setDeviceInfoList(List<TlcDeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	public Uuid getMessageId() {
		return messageId;
	}

	public void setMessageId(Uuid messageId) {
		this.messageId = messageId;
	}

	//REMARK: why are there no equals, hashcode and tostring methods in this class?
}
