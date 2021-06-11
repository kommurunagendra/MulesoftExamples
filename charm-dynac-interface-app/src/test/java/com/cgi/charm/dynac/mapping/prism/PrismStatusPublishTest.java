package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismStatusPublishTest extends XsltTest {

	public PrismStatusPublishTest() {
		super("xslt/prism/transformToStatusPublishNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
	}

	//	TODO: Fix failing test, ignored for now #merge
//	@Ignore
	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/prism/prism_status_publish.xml", "messages/prism/dMSStatusPublish.xml");
	}
}
