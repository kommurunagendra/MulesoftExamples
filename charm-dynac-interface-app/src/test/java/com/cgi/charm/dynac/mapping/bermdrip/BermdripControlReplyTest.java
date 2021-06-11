package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripControlReplyTest extends XsltTest {
	public BermdripControlReplyTest() {
		super("xslt/bermdrip/bermdrip_control_reply.xsl");
	}
	
	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("organization_id", "RWS");
		transformationBuilder.withParameter("request_id", "someId");
	}

	@Test
	public void shouldSuccess() throws Exception {
		expectTransformResult2("messages/bermdrip/mocked_bermdrip_control_success_reply.xml", "messages/bermdrip/mocked_device_control_success_response.xml");
	}
}
