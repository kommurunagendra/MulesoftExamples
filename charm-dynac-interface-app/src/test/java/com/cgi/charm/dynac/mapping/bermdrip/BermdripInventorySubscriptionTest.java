package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInventorySubscriptionTest extends XsltTest {	
	public BermdripInventorySubscriptionTest() {
		super("xslt/bermdrip/bermdrip_inventory_subscription.xsl");
	}
	
	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("return_address", "http://localhost:8081/");
		transformationBuilder.withParameter("userId", "dummy");
		transformationBuilder.withParameter("password", "test");
		transformationBuilder.withParameter("organizationId", "RWS");
	}
	
	@Test
	public void shouldTestSubscription() {
		expectTransformResult2(
				"messages/bermdrip/bermdrip_inventory_subscription.xml", 
				"messages/bermdrip/dmsInventorySubscriptionMsg.xml");
	}
}
