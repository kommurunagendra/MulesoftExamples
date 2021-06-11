package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismInfoReplyStatusTest extends XsltTest {

	public PrismInfoReplyStatusTest() {
		super("xslt/prism/transformToInfoReplyStatusNTCIP.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}
	
	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/prism/prism_info_reply_status.xml", "messages/prism/dMSStatusMsg.xml");
	}

}
