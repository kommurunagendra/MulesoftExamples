package com.cgi.charm.dynac.mapping.bridge;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeControlReplyTest extends XsltTest {	
	public BridgeControlReplyTest() {
		super("xslt/bridge/bridge_control_reply.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("request_id", "123456");
		testTransform.setParameter("organization_id", "RWS");
	}
	
	@Test
	public void shouldTestInventoryUpdateToBridge() throws Exception {		
		expectTransformResult(
				"messages/bridge/mocked_bridge_control_success_reply.xml", 
				"messages/bridge/gateControlResponseMsg.xml");
	}

	
}
