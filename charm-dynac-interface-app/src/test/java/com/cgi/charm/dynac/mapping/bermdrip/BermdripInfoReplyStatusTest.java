package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInfoReplyStatusTest extends XsltTest {

	public BermdripInfoReplyStatusTest() {
		super("xslt/bermdrip/bermdrip_info_reply_status.xsl");
	}

	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/bermdrip/mocked_bermdrip_info_success_reply.xml", "messages/bermdrip/mocked_dMS_success_status_response.xml");
	}
}
