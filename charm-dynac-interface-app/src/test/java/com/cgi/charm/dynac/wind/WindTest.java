package com.cgi.charm.dynac.wind;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DMSInventoryUpdate;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.GenericDeviceInformationRequest;
import org.tmdd._3.messages.GenericDeviceInventoryMsg;
import org.tmdd._3.messages.GenericDeviceStatusMsg;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class WindTest extends DynacFunctionalTestCase {


	@Test
	public void shouldWindInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/wind/genericDeviceInventoryUpdate.xml");
		thenVerifyWindServiceReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}


	@Test
	public void shouldWindStatusSubscription() throws Exception {
		whenWindStatusSubscriptionIsReceivedFromDynac("messages/wind/genericDeviceStatusSubscription.xml");
		thenVerifyWindServiceReceivedStatusSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}

	
	@Test
	public void shouldWindInventorySubscription() throws Exception {
		
		whenMessageFromCdmIsReceived("messages/wind/wind_inventory_subscription.xml");

		thenVerifyDynacReceivedDeviceSubscription();
	}

	@Test
	public void shouldWindStatusInfo() throws Exception {
		givenWindServiceReturns("messages/wind/wind_info_reply_status.xml");

		whenStatusInfoIsReceivedFromDynac();

		thenVerifyDynacReceivedStatusInfoReply();
	}


	@Test
	public void shouldWindInventoryInfo() throws Exception {
		givenWindServiceReturns("messages/wind/wind_info_reply_inventory.xml");

		whenInventoryInfoIsReceivedFromDynac();

		thenVerifyDynacReceivedInventoryInfoReply();
	}
	
	@Test
	public void shouldWindStatusPublish() throws Exception {

		whenMessageFromCdmIsReceived("messages/wind/wind_status_publish.xml");

		thenVerifyDynacReceivedStatusUpdate();
	}

	private void thenVerifyWindServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/wind/wind_status_subscription.xml");
	}
	
	private void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlGenericDeviceInventoryUpdate((DMSInventoryUpdate)unmarshal(requestFilename));
		
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void whenWindStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		GenericDeviceStatusMsg response = tmddClient.dlGenericDeviceStatusRequest((GenericDeviceInformationRequest) unmarshal("messages/wind/genericDeviceStatusRequestMsg.xml"));
		
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "genericDeviceStatusMsg", response);
	}
	
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		GenericDeviceInventoryMsg response = tmddClient.dlGenericDeviceInventoryRequest((DeviceInformationRequest) unmarshal("messages/wind/genericDeviceInventoryRequestMsg.xml"));
		
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "genericDeviceInventoryMsg", response);
	}

	private void thenVerifyWindServiceReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/wind/wind_inventory_publish.xml");
	}

	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/wind/genericDeviceStatusUpdateMsg.xml");
	}

	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:genericDeviceStatusMsg", "messages/wind/genericDeviceStatusMsg.xml");
	}
	
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:genericDeviceInventoryMsg", "messages/wind/genericDeviceInventoryMsg.xml");
	}

	private void givenWindServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/wind/genericDeviceInventorySubscription.xml");
	}
}
