package com.cgi.charm.dynac.mapping.bermdrip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class BermdripStatusPublishTest extends XsltTest {

	public BermdripStatusPublishTest() {
		super("xslt/bermdrip/bermdrip_status_publish.xsl");
	}

	@Test
	public void shouldTestPublish() {
		expectTransformResult2("messages/bermdrip/mocked_bermdrip_status_success_publish.xml", "messages/bermdrip/dMSStatusUpdateMsg.xml");
	}
}
