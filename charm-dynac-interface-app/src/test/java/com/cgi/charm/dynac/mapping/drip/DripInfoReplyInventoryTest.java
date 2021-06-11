package com.cgi.charm.dynac.mapping.drip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class DripInfoReplyInventoryTest extends XsltTest {

	public DripInfoReplyInventoryTest() {
		super("xslt/drip/drip_info_reply_inventory.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}
	

	@Test
	public void shouldTestInventoryReply() {
		expectTransformResult("messages/drip/mocked_drip_info_success_reply_inventory.xml", "messages/drip/mocked_dMS_inventory_success_response.xml");
	}
}
