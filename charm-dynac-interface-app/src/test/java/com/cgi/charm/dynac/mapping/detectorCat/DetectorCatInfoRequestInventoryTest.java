package com.cgi.charm.dynac.mapping.detectorCat;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorCatInfoRequestInventoryTest extends XsltTest {
	public DetectorCatInfoRequestInventoryTest() {
		super("xslt/detector_cat/transformToInfoRequestInventoryCDM.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult("messages/detector_cat/detectorInventoryRequestMsg.xml","messages/detector_cat/detector_cat_info_request_inventory.xml");
	}
}