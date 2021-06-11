package com.cgi.charm.dynac.mapping.bermdrip;

import org.junit.Test;
import org.xmlunit.builder.Input;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripInfoRequestStatusTest extends XsltTest {

	public BermdripInfoRequestStatusTest() {
		super("xslt/bermdrip/bermdrip_info_request_status.xsl");
	}

	@Override
	protected void configParameters(Input.TransformationBuilder transformationBuilder) {
		transformationBuilder.withParameter("current_time_in_millis", "1445503691000");
	}

	@Test
	public void shouldTestStatusRequest() {
		expectTransformResult2("messages/bermdrip/dMSStatusRequestMsg.xml", "messages/bermdrip/bermdrip_info_request_status.xml");
	}
}
