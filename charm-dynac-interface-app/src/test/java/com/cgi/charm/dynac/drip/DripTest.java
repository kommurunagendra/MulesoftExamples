package com.cgi.charm.dynac.drip;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Ignore;
import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DMSControlRequestCharm;
import org.tmdd._3.messages.DMSInventoryMsg;
import org.tmdd._3.messages.DMSInventoryUpdate;
import org.tmdd._3.messages.DMSStatusMsg;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class DripTest extends DynacFunctionalTestCase {

	@Test
	public void shouldDripInventorySubscription() throws Exception {
		givenDynacReturns("messages/drip/mocked_c2cMessageReceipt_success_response.xml");
		whenMessageFromCdmIsReceived("messages/drip/drip_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/drip/dmsInventorySubscriptionMsg.xml");
	}

	@Test
	public void shouldDripInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/drip/dMSInventoryUpdateMsg.xml");
		thenVerifyDripServiceReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}

	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventoryUpdate((DMSInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyDripServiceReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/drip/drip_inventory_publish.xml");
	}

	@Test
	public void shouldDripStatusSubscription() throws Exception {
		whenDripStatusSubscriptionIsReceivedFromDynac("messages/drip/deviceInformationSubscriptionMsg.xml");
		thenVerifyDripServiceReceivedStatusSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}

	private void whenDripStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient
				.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyDripServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/drip/drip_status_subscription.xml");
	}

	@Test
	public void shouldDripStatusPublish() throws Exception {
		whenDripStatusPublishIsReceived();
		thenVerifyDynacReceivedStatusUpdate();
	}

	private void whenDripStatusPublishIsReceived() throws Exception {
		whenMessageFromCdmIsReceived("messages/drip/drip_status_publish.xml");
	}

	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/drip/dMSStatusUpdateMsg.xml");
	}

	@Test
	public void shouldDripInventoryInfo() throws Exception {
		givenDripServiceReturns("messages/drip/mocked_drip_info_success_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DMSInventoryMsg response = tmddClient.dlDMSInventoryRequest(
				(DeviceInformationRequest) unmarshal("messages/drip/dMSInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSInventoryMsg", "messages/drip/mocked_dMS_inventory_success_response.xml");
	}
	
	@Test
	public void shouldDripStatusInfo() throws Exception {
		givenDripServiceReturns("messages/drip/mocked_drip_info_success_reply.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DMSStatusMsg response = tmddClient
				.dlDMSStatusRequest((DeviceInformationRequest) unmarshal("messages/drip/dMSStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/drip/mocked_dMS_success_status_response.xml");
	}

//	TODO: Fails on new date for control_timestamp. field should be ignored from comparison
	@Ignore
	@Test
	public void shouldDripControl() throws Exception {
		givenDripServiceReturns("messages/drip/mocked_drip_control_success_reply.xml");
		whenControlRequestIsReceived("messages/drip/dMSControlRequestMsg.xml");
		thenVerifyDynacReceivedDeviceResponseMessage();
		thenVerifyDripServiceReceivedControlRequest();
	}

	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient
				.dlDMSControlRequest((DMSControlRequestCharm) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "deviceControlResponseMsg", response);
	}
	private void thenVerifyDynacReceivedDeviceResponseMessage() throws Exception {
		verifyDynacReceivedReply("/tmdd:deviceControlResponseMsg",
				"messages/drip/mocked_device_control_success_response.xml");
	}
	private void thenVerifyDripServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/drp:drip_control_request/drp:drip_control",
				"messages/drip/drip_control_request.xml");
	}
	private void givenDripServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}	
}
