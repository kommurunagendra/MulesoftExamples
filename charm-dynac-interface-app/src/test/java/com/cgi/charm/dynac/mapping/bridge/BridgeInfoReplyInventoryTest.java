package com.cgi.charm.dynac.mapping.bridge;

import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeInfoReplyInventoryTest extends XsltTest {

	public BridgeInfoReplyInventoryTest() {
		super("xslt/bridge/bridge_info_reply_inventory.xsl");
	}

	@Test
	public void shouldTestInventoryReply() {
		expectTransformResult("messages/bridge/mocked_bridge_info_success_reply_inventory.xml", "messages/bridge/mocked_gate_inventory_success_response.xml");
	}
}
