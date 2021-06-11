package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiStatusSubscriptionTest extends XsltTest {
	public MsiStatusSubscriptionTest() {
		super("xslt/msi/transformToStatusSubscriptionCDM.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestSubscription() {
		expectTransformResult(
				"messages/msi/mocked_dms_device_Information_Subscription.xml",
				"messages/msi/mocked_msi_status_subscription.xml");
	}

}
