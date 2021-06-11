package com.cgi.charm.dynac.prism;

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
public class PrismTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/prism/prism_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/prism/dMSInventorySubscription.xml");
	}
	
	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/prism/dMSInventorySubscription.xml");
		thenVerifyPrismReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename)	throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventorySubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	protected void thenVerifyPrismReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/prism/prism_inventory_subscription.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/prism/dMSInventoryUpdateMsg.xml");
		thenVerifyPrismReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryUpdateIsReceived(String requestFilename)	throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventoryUpdate((DMSInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	protected void thenVerifyPrismReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/prism/prism_inventory_publish.xml");
	}
	
	@Test
	public void shouldLibraryPublish() throws Exception {
		whenMessageInventoryUpdateIsReceived("messages/prism/dMSMessageInventoryUpdateMsg.xml");
		thenVerifyPrismReceivedLibraryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenMessageInventoryUpdateIsReceived(String requestFilename)throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSMessageInventoryUpdate((DMSMessageInventoryUpdateMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	protected void thenVerifyPrismReceivedLibraryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/prism/prism_library_publish.xml");
	}
	
	@Test
	public void shouldInventoryPublishNinaVariant() throws Exception {
		whenMessageFromCdmIsReceived("messages/prism/prism_inventory_publish_nina_variant.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/prism/dMSInventoryUpdateMsg_ninaVariant.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenPrismStatusSubscriptionIsReceivedFromDynac("messages/prism/deviceInformationSubscriptionMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyPrismServiceReceivedStatusSubscription();
	}
	private void whenPrismStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyPrismServiceReceivedStatusSubscription()throws Exception {
		verifyCdmAsyncMessageReceived("messages/prism/prism_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/prism/prism_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}
	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/prism/dMSStatusPublish.xml");
	}

	@Test
	public void shouldStatusPublishNinaVariant() throws Exception {
		whenMessageFromCdmIsReceived("messages/prism/prism_status_publish_nina_variant.xml");
		thenVerifyDynacReceivedStatusUpdateMessage();
	}
	private void thenVerifyDynacReceivedStatusUpdateMessage() throws Exception {
		verifyDynacReceivedMessage("messages/prism/dMSStatusUpdateMsg.xml");
	}
	
	@Test
	public void shouldPrismInventoryInfo() throws Exception {
		givenPrismServiceReturns("messages/prism/prism_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DMSInventoryMsg response = tmddClient.dlDMSInventoryRequest((DeviceInformationRequest) unmarshal("messages/prism/dMSInventoryRequestMsgInventory.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","dMSInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSInventoryMsg",
				"messages/prism/dMSInventoryMsg.xml");
	}
	
	@Test
	public void shouldPrismStatusInfo() throws Exception {
		givenPrismServiceReturns("messages/prism/prism_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DMSStatusMsg response = tmddClient.dlDMSStatusRequest((DeviceInformationRequest) unmarshal("messages/prism/dMSInventoryRequestMsgStatus.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/prism/dMSStatusMsg.xml");
	}
	
	@Test
	public void shouldControlMessage() throws Exception {
		givenPrismServiceReturns("messages/prism/prism_control_reply.xml");
		whenControlRequestIsReceived("messages/prism/dMSControlRequestMsg.xml");
		thenVerifyPrismServiceReceivedControlRequest();
		thenVerifyDynacReceivedPrismControlResponse();
	}
	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient.dlDMSControlRequest(unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","deviceControlResponseMsg", response);
	}
	protected void thenVerifyPrismServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/prism:prism_control_request/prism:control","messages/prism/prism_control_request.xml");
	}
	protected void thenVerifyDynacReceivedPrismControlResponse() throws Exception {
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("requested changes completed", "/tmdd:deviceControlResponseMsg/request-status", testDocument);
	}
	
	private void givenPrismServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
