package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiControlRequestTest extends XsltTest {	
	
	public MsiControlRequestTest() {
		super("xslt/msi/transformToControlReqCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}

	@Test
	public void shouldTestControlReqMsi() throws Exception {
		expectTransformResult(
				"messages/msi/mocked_dms_control_reqest.xml", 
				"messages/msi/mocked_msi_control_request.xml");
	}

}
