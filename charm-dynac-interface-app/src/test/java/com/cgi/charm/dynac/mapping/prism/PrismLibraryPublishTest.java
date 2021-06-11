package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismLibraryPublishTest  extends XsltTest {

	public  PrismLibraryPublishTest() {
		super("xslt/prism/transformToImagePublishCDM.xsl");
	}
	
	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}

	@Test
	public void shouldTestInventoryUpdateToPrism() throws Exception {		
		expectTransformResult(
				"messages/prism/dMSMessageInventoryUpdateMsg.xml", 
				"messages/prism/prism_library_publish.xml");
	}

}
