package com.cgi.charm.dynac.mapping.weather;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

public class WeatherInventorySubscriptionTest extends XsltTest {
	public WeatherInventorySubscriptionTest() {
		super("xslt/weather/weather_inventory_subscription.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("return_address", "http://localhost:41082/");
		testTransform.setParameter("userId", "dummy");
		testTransform.setParameter("password", "test");
		testTransform.setParameter("organizationId", "RWS");
	}

	@Test
	public void shouldTestSubscription() {
		expectTransformResult("messages/weather/weather_inventory_subscription.xml",
				"messages/weather/deviceInformationSubscriptionMsg.xml");
	}
}
