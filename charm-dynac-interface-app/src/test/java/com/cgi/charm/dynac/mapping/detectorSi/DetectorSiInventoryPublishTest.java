package com.cgi.charm.dynac.mapping.detectorSi;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorSiInventoryPublishTest extends XsltTest {
	public DetectorSiInventoryPublishTest() {
		super("xslt/detector/transformToInventoryPublishCDM.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "17bbc210-0ccd-11e8-8ca7-ecf4bb2e8e1a");
	}

	@Test
	public void shouldInventory() {
		expectTransformResult("messages/detector_si/detectorInventoryUpdateMsg.xml", "messages/detector_si/detector_inventory_publish.xml");
	}
}
