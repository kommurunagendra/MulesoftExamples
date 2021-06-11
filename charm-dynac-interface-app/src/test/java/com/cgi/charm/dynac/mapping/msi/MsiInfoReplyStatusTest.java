package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiInfoReplyStatusTest extends XsltTest {

	public MsiInfoReplyStatusTest() {
		super("xslt/msi/transformToInfoReplyStatusNTCIP.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}

	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/msi/mocked_msi_status_reply.xml", "messages/msi/mocked_dms_status_msg.xml");
	}

}
