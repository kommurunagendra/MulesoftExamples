package com.cgi.charm.dynac.weather;

import com.cgi.charm.dynac.DynacFunctionalTestCase;
import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.ESSInventoryUpdate;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

/**
 * @author CHARM CGI TEAM
 */
public class WeatherTest extends DynacFunctionalTestCase {
	@Test
	public void shouldWeatherInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/weather/weather_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/weather/deviceInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldWeatherInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/weather/eSSInventoryUpdateMsg.xml");
		thenVerifyWeatherServiceReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}

	private void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlESSInventoryUpdate((ESSInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyWeatherServiceReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/weather/weather_inventory_publish.xml");
	}

	@Test
	public void shouldWeatherStatusSubscription() throws Exception {
		whenWeatherStatusSubscriptionIsReceivedFromDynac("messages/weather/deviceInformationStatusSubscriptionMsg.xml");
		thenVerifyWeatherServiceReceivedStatusSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}

	private void whenWeatherStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient
				.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyWeatherServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/weather/weather_status_subscription.xml");
	}

	@Test
	public void shouldWeatherStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/weather/weather_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}

	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/weather/eSSObservationReportUpdateMsg.xml");
	}
}
