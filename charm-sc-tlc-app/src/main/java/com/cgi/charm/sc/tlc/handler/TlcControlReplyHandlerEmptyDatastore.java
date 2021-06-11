package com.cgi.charm.sc.tlc.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.MetaReply;
import com.cgi.charm.cdm.tlc.TlcControlReply;
import com.cgi.charm.cdm.tlc.Uuid;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcControlReplyHandlerEmptyDatastore {

	private String version;

	/** the TlcControlReply
	 * @param messageId MessageId to prepare ControlReply
	 * @return TlcControlReply return EmptyControlReply
	 */
	public TlcControlReply handle(Uuid messageId) {

		TlcControlReply tlcControlReply = new TlcControlReply();
		MetaReply metareply = new MetaReply();
		metareply.setMessageCreateDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		metareply.setMessageId(messageId);
		metareply.setRequestId(messageId);
		tlcControlReply.setMetaReply(metareply);
		tlcControlReply.setVersion(version);
		return tlcControlReply;
	}

	@Value("${tlc.version}")
	public void setVersion(String version) {
		this.version = version;
	}
}
