package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismInfoReplyInventoryTest extends XsltTest {

	public PrismInfoReplyInventoryTest() {
		super("xslt/prism/transformToInfoReplyInventoryNTCIP.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}
	
	@Test
	public void shouldTestInventoryReply() {
		expectTransformResult("messages/prism/prism_info_reply_inventory.xml", "messages/prism/dMSInventoryMsg.xml");
	}

}
