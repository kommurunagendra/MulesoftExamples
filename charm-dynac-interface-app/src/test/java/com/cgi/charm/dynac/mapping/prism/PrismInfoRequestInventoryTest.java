package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismInfoRequestInventoryTest extends XsltTest {

	public PrismInfoRequestInventoryTest() {
		super("xslt/prism/transformToInfoRequestInventoryCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestInventoryRequest() {
		expectTransformResult("messages/prism/dMSInventoryRequestMsgInventory.xml", "messages/prism/prism_info_request_inventory.xml");
	}
	

}
