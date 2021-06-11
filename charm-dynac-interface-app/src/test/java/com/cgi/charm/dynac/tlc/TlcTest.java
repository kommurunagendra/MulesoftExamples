package com.cgi.charm.dynac.tlc;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DMSInventoryUpdate;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.GenericDeviceControlRequest;
import org.tmdd._3.messages.GenericDeviceInventoryMsg;
import org.tmdd._3.messages.GenericDeviceStatusMsg;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class TlcTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryPublishIsReceived("messages/tlc/genericDeviceInventoryUpdateMsg.xml");
		thenVerifyTlcReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryPublishIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlGenericDeviceInventoryUpdate((DMSInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyTlcReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/tlc/tlc_inventory_publish.xml");
	}
	
	@Test
	public void shouldTlcControl() throws Exception {
		givenTlcServiceReturns("messages/tlc/tlc_control_reply.xml");
		whenControlRequestIsReceived("messages/tlc/genericDeviceControlRequestMsg.xml");
		thenVerifyDynacReceivedDeviceResponseMessage();
		thenVerifyTlcServiceReceivedControlRequest();
	}
	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient.dlGenericDeviceControlRequest((GenericDeviceControlRequest) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "deviceControlResponseMsg", response);
	}
	private void thenVerifyDynacReceivedDeviceResponseMessage() throws Exception {
		verifyDynacReceivedReply("/tmdd:deviceControlResponseMsg","messages/tlc/deviceControlResponseMsg.xml");
	}
	private void thenVerifyTlcServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/tlc:tlc_control_request/tlc:tlc_control","messages/tlc/tlc_control_request.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenTlcStatusSubscriptionIsReceivedFromDynac("messages/tlc/deviceInformationStatusSubscriptionMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyTlcServiceReceivedStatusSubscription();
	}
	private void whenTlcStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyTlcServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/tlc/tlc_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/tlc/tlc_status_publish.xml");
		thenVerifyDynacReceivedStatusPublish();
	}
	private void thenVerifyDynacReceivedStatusPublish() throws Exception {
		verifyDynacReceivedMessage("messages/tlc/genericDeviceStatusUpdateMsg.xml");
	}
	
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/tlc/tlc_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/tlc/deviceInformationInventorySubscriptionMsg.xml");
	}

	@Test
	public void shouldTlcStatusInfo() throws Exception {
		givenTlcServiceReturns("messages/tlc/tlc_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		GenericDeviceStatusMsg response = tmddClient.dlGenericDeviceStatusRequest(unmarshal("messages/tlc/genericDeviceStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "genericDeviceStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:genericDeviceStatusMsg", "messages/tlc/genericDeviceStatusMsg.xml");
	}
	
	@Test
	public void shouldTlcInventoryInfo() throws Exception {
		givenTlcServiceReturns("messages/tlc/tlc_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		GenericDeviceInventoryMsg response = tmddClient.dlGenericDeviceInventoryRequest(unmarshal("messages/tlc/genericDeviceInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "genericDeviceInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:genericDeviceInventoryMsg", "messages/tlc/genericDeviceInventoryMsg.xml");
	}
	
		
	private void givenTlcServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
