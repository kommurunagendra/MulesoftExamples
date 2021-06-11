package com.cgi.charm.dynac.mapping.drip;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DripInventorySubscriptionTest extends XsltTest {	
	public DripInventorySubscriptionTest() {
		super("xslt/drip/drip_inventory_subscription.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("return_address", "http://localhost:41082/");
		testTransform.setParameter("userId", "dummy");
		testTransform.setParameter("password", "test");
		testTransform.setParameter("organizationId", "RWS");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult(
				"messages/drip/drip_inventory_subscription.xml", 
				"messages/drip/dmsInventorySubscriptionMsg.xml");
	}
}
