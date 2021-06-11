package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInventoryPublishTest extends XsltTest {
	public BermdripInventoryPublishTest() {
		super("xslt/bermdrip/bermdrip_inventory_publish.xsl");
	}

	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
	}

	@Test
	public void shouldInventory() {
		expectTransformResult2("messages/bermdrip/dMSInventoryUpdateMsg.xml", "messages/bermdrip/bermdrip_inventory_publish.xml");
	}
}
