package com.cgi.charm.dynac.msi;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLUnit.buildTestDocument;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DMSInventoryMsg;
import org.tmdd._3.messages.DMSInventoryUpdate;
import org.tmdd._3.messages.DMSMessageInventoryUpdateMsg;
import org.tmdd._3.messages.DMSStatusMsg;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.w3c.dom.Document;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class MsiTest extends DynacFunctionalTestCase {
	
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/msi/mocked_msi_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/msi/mocked_dms_inventory_subscription.xml");
	}
	
	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/msi/mocked_dms_inventory_subscription.xml");
		thenVerifyMsiReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventorySubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyMsiReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/msi/mocked_msi_inventory_subscription.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/msi/mocked_dms_inventory_update.xml");
		thenVerifyMsiReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventoryUpdate((DMSInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyMsiReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/msi/mocked_msi_inventory_publish.xml");
	}

	@Test
	public void shouldImageLibraryPublish() throws Exception {
		whenLibraryUpdateIsReceived("messages/msi/mocked_dms_message_inventory_update.xml");
		thenVerifyMsiReceivedLibraryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenLibraryUpdateIsReceived(String requestFilename)	throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSMessageInventoryUpdate((DMSMessageInventoryUpdateMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyMsiReceivedLibraryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/msi/mocked_msi_image_publish.xml");
	}

	@Test
	public void shouldInventoryPublishVariant() throws Exception {
		whenMessageFromCdmIsReceived("messages/msi/mocked_msi_inventory_publish_variant.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/msi/mocked_dms_inventory_update_variant.xml");
	}

	@Test
	public void shouldStatusSubscription() throws Exception {
		whenMsiStatusSubscriptionIsReceivedFromDynac("messages/msi/mocked_dms_device_Information_Subscription.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyMsiServiceReceivedStatusSubscription();
	}
	private void whenMsiStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyMsiServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/msi/mocked_msi_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/msi/mocked_msi_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}
	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/msi/mocked_dms_status_update_msg.xml");
	}
	
	
	@Test
	public void shouldMsiInventoryInfo() throws Exception {
		givenMsiServiceReturns("messages/msi/mocked_msi_info_reply.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DMSInventoryMsg response = tmddClient.dlDMSInventoryRequest((DeviceInformationRequest) unmarshal("messages/msi/mocked_dms_inventory_request_message.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","dMSInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSInventoryMsg","messages/msi/mocked_dms_inventory_msg.xml");
	}

	@Test
	public void shouldMsiStatusInfo() throws Exception {
		givenMsiServiceReturns("messages/msi/mocked_msi_status_reply.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DMSStatusMsg response = tmddClient.dlDMSStatusRequest((DeviceInformationRequest) unmarshal("messages/msi/mocked_dms_status_request_message.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/msi/mocked_dms_status_msg.xml");
	}
	
	@Test
	public void shouldControlMessage() throws Exception {
		givenMsiServiceReturns("messages/msi/mocked_msi_control_reply.xml");
		whenControlRequestIsReceived("messages/msi/mocked_dms_control_reqest.xml");
		thenVerifyMsiServiceReceivedControlRequest();
		thenVerifyDynacReceivedMsiControlResponse();
	}
	protected void whenControlRequestIsReceived(String requestFilename)	throws Exception {
		DeviceControlResponse response = tmddClient.dlDMSControlRequest(unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","deviceControlResponseMsg", response);
	}
	protected void thenVerifyMsiServiceReceivedControlRequest()	throws Exception {
		verifyCdmMessageReceived("/msi:msi_control_request/msi:control","messages/msi/mocked_msi_control_request.xml");
	}
	protected void thenVerifyDynacReceivedMsiControlResponse() throws Exception {
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("requested changes completed", "/tmdd:deviceControlResponseMsg/request-status", testDocument);
	}
	
	private void givenMsiServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}

	
}
