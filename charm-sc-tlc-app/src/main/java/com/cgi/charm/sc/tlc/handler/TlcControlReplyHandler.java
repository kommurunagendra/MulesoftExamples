package com.cgi.charm.sc.tlc.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Id;
import com.cgi.charm.cdm.tlc.MetaReply;
import com.cgi.charm.cdm.tlc.TlcControl;
import com.cgi.charm.cdm.tlc.TlcControlAttr;
import com.cgi.charm.cdm.tlc.TlcControlMutations;
import com.cgi.charm.cdm.tlc.TlcControlReply;
import com.cgi.charm.sc.tlc.helper.TlcDeviceInfoWrapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.util.TlcUtil;

/**
 * @author CHARM CGI Creates Tlc Control Reply
 */
@Component
public class TlcControlReplyHandler {

	private String version;

	/**
	 * Fetch The Tlc Detials From The Wrapper Object To Create Control Reply
	 * 
	 * @param wrapper
	 *            WrapperObject
	 * @return TlcControlReply returns TlcControlReply
	 */
	public TlcControlReply handle(TlcDeviceInfoWrapper wrapper) {
		TlcControlReply tlcControlReply = new TlcControlReply();
		List<TlcDeviceInfo> tlcDeviceInfoList = wrapper.getDeviceInfoList();
		if (tlcDeviceInfoList.isEmpty()) {
			return tlcControlReply;
		}
		MetaReply metareply = new MetaReply();
		metareply.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		metareply.setMessageId(wrapper.getMessageId());
		metareply.setRequestId(wrapper.getMessageId());
		List<TlcControlMutations> tlcControlMutationsList = new ArrayList<TlcControlMutations>();
		for (TlcDeviceInfo tlcDeviceInfo : tlcDeviceInfoList) {
			TlcControlMutations tlcControlMutations = new TlcControlMutations();
			TlcControl tlcControl = new TlcControl();
			TlcControlAttr tlcControlAttr = new TlcControlAttr();
			tlcControl.setTlcId(new Id(tlcDeviceInfo.getTlcId()));
			tlcControlAttr.setControlTimestamp(TlcUtil.toInstant(tlcDeviceInfo.getControlTimeStamp()));
			tlcControlAttr.setIveraObject(tlcDeviceInfo.getIveraObject());
			tlcControl.setTlcControlAttr(tlcControlAttr);
			tlcControlMutations.setTlcControl(tlcControl);
			tlcControlMutations.setProcessed(tlcDeviceInfo.isProcessed());
			tlcControlMutationsList.add(tlcControlMutations);
		}
		tlcControlReply.setMetaReply(metareply);;
		tlcControlReply.setTlcControlMutations(tlcControlMutationsList);
		tlcControlReply.setVersion(version);
		return tlcControlReply;
	}

	@Value("${tlc.version}")
	public void setVersion(String version) {
		this.version = version;
	}
}
