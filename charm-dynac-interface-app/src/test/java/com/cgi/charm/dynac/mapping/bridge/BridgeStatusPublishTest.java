package com.cgi.charm.dynac.mapping.bridge;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeStatusPublishTest extends XsltTest {

	public BridgeStatusPublishTest() {
		super("xslt/bridge/bridge_status_publish.xsl");
	}
	

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organizationId", "RWS");
	}
	
	@Test
	public void shouldTestPublish() {
		expectTransformResult(
				"messages/bridge/mocked_bridge_status_success_publish.xml", 
				"messages/bridge/gateStatusUpdateMsg.xml");
	}
}
