package com.cgi.charm.dynac.bridge;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.GateControlRequest;
import org.tmdd._3.messages.GateInventoryMsg;
import org.tmdd._3.messages.GateInventoryUpdate;
import org.tmdd._3.messages.GateStatusMsg;
import org.tmdd._3.messages.GateStatusUpdate;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class BridgeTest extends DynacFunctionalTestCase {
	
	private MuleMessage dynacRawResponse;

	private static final Logger LOGGER = Logger.getLogger(BridgeTest.class);
	
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/bridge/bridge_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/bridge/gateInventorySubscriptionMsg.xml");
	}
	
	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/bridge/gateInventorySubscriptionMsgNTCIPToCdm.xml");
		thenVerifyBridgeReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename)	throws Exception {
		C2CMessageReceipt response = tmddClient.dlGateInventorySubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	protected void thenVerifyBridgeReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/bridge/bridge_inventory_subscription_NTCIP_CDM.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/bridge/gateInventoryUpdateMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlGateInventoryUpdate((GateInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}
	
	@Test
	public void shouldInventoryPublishCdmToNtcip() throws Exception {
		whenMessageFromCdmIsReceived("messages/bridge/bridge_inventory_publish.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/bridge/gateInventoryUpdateMsgCdmToNtcip.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenBridgeStatusSubscriptionIsReceivedFromDynac("messages/bridge/deviceInformationSubscriptionMsg_status.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyBridgeServiceReceivedStatusSubscription();
	}
	private void whenBridgeStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}
	private void thenVerifyBridgeServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/bridge/bridge_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusSubscriptionCdmToNtcip() throws Exception {
		whenMessageFromCdmIsReceived("messages/bridge/bridge_status_subscription.xml");
		thenVerifyDynacReceivedInformationSubscription();
	}
	private void thenVerifyDynacReceivedInformationSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/bridge/deviceInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/bridge/mocked_bridge_status_success_publish.xml");
		thenVerifyDynacReceivedGateStatusUpdate();
	}
	protected void thenVerifyDynacReceivedGateStatusUpdate() throws Exception {
		String expectedResourceFilename = "messages/bridge/gateStatusUpdateMsg.xml";
		verifyDynacReceivedMessage(expectedResourceFilename);
	}
	
	@Test
	public void shouldStatusPublishNtcipToCdm() throws Exception {
		whenStatusUpdateIsReceived("messages/bridge/gateStatusUpdateMsgNtcipToCdm.xml");
		thenVerifyBridgeReceivedStatusPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenStatusUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlGateStatusUpdate((GateStatusUpdate)unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyBridgeReceivedStatusPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/bridge/bridge_status_publish.xml");
	}
	
	@Test
	public void shouldBridgeInventoryInfo() throws Exception {
		givenBridgeServiceReturns("messages/bridge/mocked_bridge_info_success_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DeviceInformationRequest req = (DeviceInformationRequest) unmarshal("messages/bridge/deviceInventoryRequestMsg_inventory.xml");
		GateInventoryMsg response = tmddClient.dlGateInventoryRequest(req);
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "gateInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:gateInventoryMsg", "messages/bridge/mocked_gate_inventory_success_response.xml");
	}

	@Test
	public void shouldBridgeStatusInfo() throws Exception {
		givenBridgeServiceReturns("messages/bridge/mocked_bridge_info_success_reply.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		GateStatusMsg response = tmddClient.dlGateStatusRequest((DeviceInformationRequest) unmarshal("messages/bridge/deviceInventoryRequestMsg_status.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "gateStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:gateStatusMsg", "messages/bridge/mocked_gate_success_status_response.xml");
	}
	
	@Test
	public void shouldControlMessage() throws Exception {
		givenBridgeServiceReturns("messages/bridge/mocked_bridge_control_success_reply.xml");
		whenControlRequestIsReceived("messages/bridge/gateControlRequestMsg.xml");
		thenVerifyBridgeServiceReceivedControlRequest();
		thenVerifyDynacReceivedControlResponse();
	}
	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient.dlGateControlRequest((GateControlRequest) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "deviceControlResponseMsg", response);
	}
	protected void thenVerifyBridgeServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/brg:bridge_control_request/brg:control","messages/bridge/bridge_control_request.xml");
	}

	
	@Test
	public void shouldInvalidXml() throws Exception {
		whenMessageIsReceivedOnNtcipEndpoint("messages/bridge/gateInventoryUpdateMsg_invalidxml.xml");
		thenVerifyDynacReceivedSoapFault("missing information prevents processing request");
	}

	public void shouldNoReplyFromBridgeService() throws Exception {
		givenBridgeServiceDoesNotReply();
		whenMessageIsReceivedOnNtcipEndpoint("messages/bridge/gateControlRequestMsg.xml");
		thenVerifyDynacReceivedSoapFault("Internal error occurred while processing the request.");
	}
	private void givenBridgeServiceDoesNotReply() {
		cdmMock.setThrowException(true);
	}
	private void whenMessageIsReceivedOnNtcipEndpoint(String requestFilename) throws Exception {
		dynacRawResponse = client.send("vm://from-ntcip", new DefaultMuleMessage(resourceContent(requestFilename), muleContext));
	}
	protected void thenVerifyDynacReceivedSoapFault(String faultstring) throws Exception {
		//Object status = dynacRawResponse.getInboundProperty("http_status");
		String payload = dynacRawResponse.getPayloadAsString();
		//assertThat(status).isEqualTo(500);
		//payload = payload.replaceFirst("<soap:Fault>", "<soap:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		assertXpathEvaluatesTo(faultstring, "/soap:Fault/faultstring", payload);
	}

	private void givenBridgeServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
	
}
