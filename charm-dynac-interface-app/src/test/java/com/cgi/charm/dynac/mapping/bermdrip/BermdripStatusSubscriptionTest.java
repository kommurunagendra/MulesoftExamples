package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripStatusSubscriptionTest extends XsltTest {
	public BermdripStatusSubscriptionTest() {
		super("xslt/bermdrip/bermdrip_status_subscription.xsl");
	}
	
	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
		transformationBuilder.withParameter("return_address", "http://localhost:41082/");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult2(
				"messages/bermdrip/deviceInformationSubscriptionMsg_status.xml",
				"messages/bermdrip/bermdrip_status_subscription.xml");
	}
}
