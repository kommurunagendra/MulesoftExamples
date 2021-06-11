package com.cgi.charm.dynac.mapping.detectorCat;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorCatInventoryPublishTest extends XsltTest {
	public DetectorCatInventoryPublishTest() {
		super("xslt/detector_cat/transformToInventoryPublishCDM.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}

	@Test
	public void shouldInventory() {
		expectTransformResult("messages/detector_cat/detectorInventoryUpdateMsg.xml", "messages/detector_cat/detector_cat_inventory_publish.xml");
	}
}
