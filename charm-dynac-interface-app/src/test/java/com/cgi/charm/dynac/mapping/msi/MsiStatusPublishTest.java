package com.cgi.charm.dynac.mapping.msi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class MsiStatusPublishTest extends XsltTest {

	public MsiStatusPublishTest() {
		super("xslt/msi/transformToStatusPublishNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}

	@Test
	public void shouldTestStatusPUblish() {
		expectTransformResult("messages/msi/mocked_msi_status_publish.xml", "messages/msi/mocked_dms_status_update_msg.xml");
	}
}
