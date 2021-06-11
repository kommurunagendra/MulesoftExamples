package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInfoReplyInventoryTest extends XsltTest {

	public BermdripInfoReplyInventoryTest() {
		super("xslt/bermdrip/bermdrip_info_reply_inventory.xsl");
	}

	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("organization_id", "RWS");
	}
	
	@Test
	public void shouldTestInventoryReply() {
		expectTransformResult2("messages/bermdrip/mocked_bermdrip_info_success_reply_inventory.xml", "messages/bermdrip/mocked_dMS_inventory_success_response.xml");
	}
}
