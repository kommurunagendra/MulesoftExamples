package com.cgi.charm.dynac.mapping.drip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class DripControlReplyTest extends XsltTest {
	public DripControlReplyTest() {
		super("xslt/drip/drip_control_reply.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
		testTransform.setParameter("request_id", "someId");
	}
	
	@Test
	public void shouldSuccess() throws Exception {
		expectTransformResult("messages/drip/mocked_drip_control_success_reply.xml", "messages/drip/mocked_device_control_success_response.xml");
	}
}
