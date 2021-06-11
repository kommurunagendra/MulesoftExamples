package com.cgi.charm.dynac.mapping.detectorCat;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorCatInventorySubscriptionTest extends XsltTest {
	public DetectorCatInventorySubscriptionTest() {
		super("xslt/detector_cat/transformToInventorySubscriptionNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("return_address", "http://localhost:41082/");
		testTransform.setParameter("userId", "dummy");
		testTransform.setParameter("password", "test");
		testTransform.setParameter("organization_id", "RWS");
		testTransform.setParameter("device_type", "detector-cat");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult("messages/detector_cat/detector_cat_inventory_subscription.xml",
				"messages/detector_cat/deviceInformationSubscriptionMsg.xml");
	}
}
