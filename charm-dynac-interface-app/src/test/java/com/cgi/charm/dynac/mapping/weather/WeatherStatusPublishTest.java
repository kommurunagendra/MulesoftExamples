package com.cgi.charm.dynac.mapping.weather;

import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

import com.cgi.charm.dynac.mapping.XsltTest;

public class WeatherStatusPublishTest extends XsltTest {

	public WeatherStatusPublishTest() {
		super("xslt/weather/weather_status_publish.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		testTransform.setParameter("organizationId", "RWS");
	}

	@Test
	public void shouldTestPublish() {
		expectTransformResult("messages/weather/weather_status_publish.xml","messages/weather/eSSObservationReportUpdateMsg.xml");
	}
	
}
