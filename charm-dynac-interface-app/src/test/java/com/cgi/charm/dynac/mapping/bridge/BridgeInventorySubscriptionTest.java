package com.cgi.charm.dynac.mapping.bridge;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeInventorySubscriptionTest extends XsltTest {
	public BridgeInventorySubscriptionTest() {
		super("xslt/bridge/bridge_inventory_subscription.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("return_address", "http://localhost:8081/");
		testTransform.setParameter("userId", "dummy");
		testTransform.setParameter("password", "test");
		testTransform.setParameter("organizationId", "RWS");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult("messages/bridge/bridge_inventory_subscription.xml", "messages/bridge/gateInventorySubscriptionMsg.xml");
	}
}
