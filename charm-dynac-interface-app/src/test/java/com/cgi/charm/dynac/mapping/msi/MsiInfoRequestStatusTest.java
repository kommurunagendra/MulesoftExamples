package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiInfoRequestStatusTest extends XsltTest {

	public MsiInfoRequestStatusTest() {
		super("xslt/msi/transformToInfoRequestStatusCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}
	
	@Test
	public void shouldTestStatusRequest() {
		expectTransformResult("messages/msi/mocked_dms_status_request_message.xml", "messages/msi/mocked_msi_status_request.xml");
	}
	

}
