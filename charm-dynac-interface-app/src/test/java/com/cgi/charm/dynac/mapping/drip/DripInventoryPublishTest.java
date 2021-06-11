package com.cgi.charm.dynac.mapping.drip;

import org.junit.Test;
import org.xmlunit.builder.Input;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DripInventoryPublishTest extends XsltTest {
	public DripInventoryPublishTest() {
		super("xslt/drip/drip_inventory_publish.xsl");
	}

	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
	}

	@Test
	public void shouldInventory() {
		expectTransformResult2("messages/drip/dMSInventoryUpdateMsg.xml", "messages/drip/drip_inventory_publish.xml");
	}
}
