package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInfoRequestInventoryTest extends XsltTest {

	public BermdripInfoRequestInventoryTest() {
		super("xslt/bermdrip/bermdrip_info_request_inventory.xsl");
	}

	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
	}

	@Test
	public void shouldTestInventoryRequest() {
		expectTransformResult2("messages/bermdrip/dMSInventoryRequestMsg.xml", "messages/bermdrip/bermdrip_info_request_inventory.xml");
	}
}
