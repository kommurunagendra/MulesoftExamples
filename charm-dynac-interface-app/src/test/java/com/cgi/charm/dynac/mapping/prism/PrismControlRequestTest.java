package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismControlRequestTest extends XsltTest {	
	
	public PrismControlRequestTest() {
		super("xslt/prism/transformToControlRequestCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestControlReqPrism() throws Exception {
		expectTransformResult(
				"messages/prism/dMSControlRequestMsg.xml", 
				"messages/prism/prism_control_request.xml");
	}

}
