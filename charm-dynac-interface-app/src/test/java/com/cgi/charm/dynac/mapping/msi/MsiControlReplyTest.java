package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiControlReplyTest extends XsltTest {
	
	public MsiControlReplyTest() {
		super("xslt/msi/transformToControlResponseNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
		testTransform.setParameter("request_id", "0");
	}

	@Test
	public void shouldTestControlResponse() throws Exception {		
		expectTransformResult(
				"messages/msi/mocked_msi_control_reply.xml", 
				"messages/msi/mocked_dms_control_response.xml");
	}
}
