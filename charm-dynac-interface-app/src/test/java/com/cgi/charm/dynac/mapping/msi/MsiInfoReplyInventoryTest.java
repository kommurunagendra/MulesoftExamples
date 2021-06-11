package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiInfoReplyInventoryTest extends XsltTest {

	public MsiInfoReplyInventoryTest() {
		super("xslt/msi/transformToInfoReplyInventoryNTCIP.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}

	@Test
	public void shouldTestInventoryReply() {
		expectTransformResult("messages/msi/mocked_msi_info_reply.xml", "messages/msi/mocked_dms_inventory_msg.xml");
	}

}
