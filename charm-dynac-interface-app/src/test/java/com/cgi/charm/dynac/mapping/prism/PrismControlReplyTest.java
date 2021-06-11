package com.cgi.charm.dynac.mapping.prism;

import org.custommonkey.xmlunit.Transform;
import org.junit.Ignore;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class PrismControlReplyTest extends XsltTest {
	
	public PrismControlReplyTest() {
		super("xslt/prism/transformToControlReplyNTCIP.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organization_id", "RWS");
		testTransform.setParameter("request_id", "1");
	}

	@Test
	public void shouldTestInventoryUpdateToPrism() throws Exception {		
		expectTransformResult(
				"messages/prism/prism_control_reply.xml", 
				"messages/prism/deviceControlResponseMsg.xml");
	}
}
