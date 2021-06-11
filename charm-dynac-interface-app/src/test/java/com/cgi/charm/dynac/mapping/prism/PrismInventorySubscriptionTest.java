package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismInventorySubscriptionTest extends XsltTest {

	public PrismInventorySubscriptionTest() {
		super("xslt/prism/transformToInventorySubscriptionNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
		testTransform.setParameter("return_address", "http://localhost:41082/");
		testTransform.setParameter("password", "test");
		testTransform.setParameter("userId", "dummy");
	}
	
	@Test
	public void shouldTestSubscription() {
		expectTransformResult("messages/prism/prism_inventory_subscription.xml", "messages/prism/dMSInventorySubscription.xml");
	}
	
}
