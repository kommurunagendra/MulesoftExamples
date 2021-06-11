package com.cgi.charm.dynac.mapping.detectorCat;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorCatStatusSubscriptionTest extends XsltTest {
	public DetectorCatStatusSubscriptionTest() {
		super("xslt/detector_cat/transformToStatusSubscriptionCDM.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
		testTransform.setParameter("return_address", "http://localhost:41082/");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult(
				"messages/detector_cat/deviceInformationStatusSubscriptionMsg.xml",
				"messages/detector_cat/detector_cat_status_subscription.xml");
	}
}