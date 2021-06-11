package com.cgi.charm.dynac.mapping.bridge;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeInfoReplyStatusTest extends XsltTest {

	public BridgeInfoReplyStatusTest() {
		super("xslt/bridge/bridge_info_reply_status.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organizationId", "RWS");
	}

	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/bridge/mocked_bridge_info_success_reply.xml", "messages/bridge/mocked_gate_success_status_response.xml");
	}
}
