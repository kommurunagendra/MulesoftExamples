package com.cgi.charm.dynac.mapping.bridge;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeInventoryPublishTest extends XsltTest {	
	public BridgeInventoryPublishTest() {
		super("xslt/bridge/bridge_inventory_publish.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestInventoryUpdateToBridge() throws Exception {		
		expectTransformResult(
				"messages/bridge/gateInventoryUpdateMsg.xml", 
				"messages/bridge/bridge_inventory_publish.xml");
	}

	
}
