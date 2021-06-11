package com.cgi.charm.dynac.mapping.weather;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

public class WeatherInventoryPublishTest extends XsltTest {
	public WeatherInventoryPublishTest() {
		super("xslt/weather/weather_inventory_publish.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("current_time_in_millis", "1445503691000");
		testTransform.setParameter("message_uuid", "00767d19-ca89-4451-af9c-537d25f6a369");
	}

	@Test
	public void shouldInventory() {
		expectTransformResult("messages/weather/eSSInventoryUpdateMsg.xml", "messages/weather/weather_inventory_publish.xml");
	}
}
