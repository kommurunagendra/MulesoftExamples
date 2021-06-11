package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiInfoRequestInventoryTest extends XsltTest {

	public MsiInfoRequestInventoryTest() {
		super("xslt/msi/transformToInfoRequestInventoryCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestInventoryRequest() {
		expectTransformResult("messages/msi/mocked_dms_inventory_request_message.xml", "messages/msi/mocked_msi_info_request.xml");
	}
	

}
