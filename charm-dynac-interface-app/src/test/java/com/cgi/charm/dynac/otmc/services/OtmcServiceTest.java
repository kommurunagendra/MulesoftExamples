package com.cgi.charm.dynac.otmc.services;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLUnit.buildTestDocument;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.AnyTypeObject;
import org.tmdd._3.messages.AnyTypeUpdate;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.GenericDeviceControlRequest;
import org.w3c.dom.Document;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class OtmcServiceTest extends DynacFunctionalTestCase {
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/services/otmc_services_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/services/deviceInformationSubscriptionMsg.xml");
	}

	@Test
	public void shouldInventoryPublishNtcipToCdm() throws Exception {
		whenAnyTypeInventoryUpdateIsReceived("messages/otmc/services/anyTypeInventoryUpdateMsg.xml");
		thenVerifyOtmcReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}

	protected void whenAnyTypeInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlAnyTypeInventoryUpdate((AnyTypeUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyOtmcReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/services/otmc_services_inventory_publish.xml");
	}

	@Test
	public void shouldStatusSubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/services/otmc_services_status_subscription_NTCIP_CDM.xml");
		thenVerifyDynacReceivedStatusSubscription();
	}

	private void thenVerifyDynacReceivedStatusSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/services/deviceInformationSubscriptionMsgStatus.xml");
	}

	@Test
	public void shouldStatusSubscriptionNtcipToCdm() throws Exception {
		whenStatusSubscriptionIsReceivedFromDynac("messages/otmc/services/deviceInformationSubscription_NTCIP_CDM.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyRouteServiceReceivedStatusSubscription();
	}

	private void whenStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient
				.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyRouteServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/services/otmc_services_status_subscription_NTCIP_CDM.xml");
	}

	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/services/otmc_services_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}

	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/services/anyTypeStatusUpdateMsg.xml");
	}

	@Test
	public void shouldStatusPublishNtcipToCdm() throws Exception {
		whenStatusPublishIsReceivedFromDynac("messages/otmc/services/anyTypeStatusUpdateMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyServiceReceivedStatusPublish();
	}

	private void whenStatusPublishIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlAnyTypeStatusUpdate((AnyTypeUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyServiceReceivedStatusPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/services/otmc_services_status_publish_NTCIP_CDM.xml");
	}

	@Test
	public void shouldOtmcServiceInventoryInfo() throws Exception {
		givenOtmcServiceReturns("messages/otmc/services/otmc_services_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}

	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		AnyTypeObject response = tmddClient
				.dlAnyTypeInventoryRequest(unmarshal("messages/otmc/services/anyTypeInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "anyTypeInventoryMsg", response);
	}

	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:anyTypeInventoryMsg", "messages/otmc/services/anyTypeInventoryMsg.xml");
	}

	@Test
	public void shouldOtmcServiceStatusInfo() throws Exception {
		givenOtmcServiceReturns("messages/otmc/services/otmc_services_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}

	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		AnyTypeObject response = tmddClient
				.dlAnyTypeStatusRequest(unmarshal("messages/otmc/services/anyTypeStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "anyTypeStatusMsg", response);
	}

	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:anyTypeStatusMsg", "messages/otmc/services/anyTypeStatusMsg.xml");
	}

	@Test
	public void shouldControlMessage() throws Exception {
		givenOtmcServiceReturns("messages/otmc/services/otmc_services_control_reply.xml");
		whenControlRequestIsReceived("messages/otmc/services/genericDeviceControlRequestMsg.xml");
		thenVerifyBridgeServiceReceivedControlRequest();
		thenVerifyDynacReceivedControlResponse();
	}

	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient
				.dlGenericDeviceControlRequest((GenericDeviceControlRequest) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "deviceControlResponseMsg", response);
	}

	protected void thenVerifyBridgeServiceReceivedControlRequest() throws Exception {
		verifyControlMessageReceived("otmc_services_control_request",
				"messages/otmc/services/otmc_services_control_request.xml");
	}

	protected void thenVerifyDynacReceivedControlResponse() throws Exception {
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("otmc service", "/tmdd:deviceControlResponseMsg/device-type", testDocument);
	}

	private void givenOtmcServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
