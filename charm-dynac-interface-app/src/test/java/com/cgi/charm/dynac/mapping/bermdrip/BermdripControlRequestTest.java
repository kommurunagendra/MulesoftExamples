package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripControlRequestTest extends XsltTest {
	public BermdripControlRequestTest() {
		super("xslt/bermdrip/bermdrip_control_request.xsl");
	}
	
	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
		transformationBuilder.withParameter("organization_id", "RWS");
		transformationBuilder.withParameter("request_id", "someId");
	}

	@Test
	public void shouldSuccess() throws Exception {
		expectTransformResult2("messages/bermdrip/dMSControlRequestMsg.xml", "messages/bermdrip/bermdrip_control_request.xml");
	}
}
