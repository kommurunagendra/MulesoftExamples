package com.cgi.charm.dynac.bermdrip;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;

import org.junit.Test;
import org.mule.api.MuleMessage;
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
public class BermdripTest extends DynacFunctionalTestCase {
	private MuleMessage dynacRawResponse;


	@Test
	public void shouldBermdripControl() throws Exception {
		givenBermdripServiceReturns("messages/bermdrip/mocked_bermdrip_control_success_reply.xml");		

		whenControlRequestIsReceived("messages/bermdrip/dMSControlRequestMsg.xml");
		thenVerifyDripServiceReceivedControlRequest();
		thenVerifyDynacReceivedDeviceResponseMessage();
		
	}
	
	@Test
	public void shouldBermdripStatusPublish() throws Exception {
		givenDynacReturnsMessageReceiptOk();
		
		whenDripStatusPublishIsReceived();
		
		thenVerifyDynacReceivedStatusUpdate();
	}
	
	@Test
	public void shouldBermdripInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/bermdrip/dMSInventoryUpdateMsg.xml");
		
		thenVerifyBermdripServiceReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();

	}

	@Test
	public void shouldBermdripStatusSubscription() throws Exception {
		whenBermdripStatusSubscriptionIsReceivedFromDynac("messages/bermdrip/deviceInformationSubscriptionMsg_status.xml");

		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyBermdripServiceReceivedStatusSubscription();

	}
	
	@Test
	public void shouldBermdripInventorySubscription() throws Exception {
		givenDynacReturns("messages/bermdrip/mocked_c2cMessageReceipt_success_response.xml");
		whenMessageFromCdmIsReceived("messages/bermdrip/bermdrip_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}

	@Test
	public void shouldBermdripStatusInfo() throws Exception {
		givenBermdripServiceReturns("messages/bermdrip/mocked_bermdrip_info_success_reply.xml");

		whenStatusInfoIsReceivedFromDynac();

		thenVerifyDynacReceivedStatusInfoReply();
	}

	@Test
	public void shouldBermdripInventoryInfo() throws Exception {
		givenBermdripServiceReturns("messages/bermdrip/mocked_bermdrip_info_success_reply_inventory.xml");

		whenInventoryInfoIsReceivedFromDynac();

		thenVerifyDynacReceivedInventoryInfoReply();
	}

	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DMSStatusMsg response = tmddClient.dlDMSStatusRequest((DeviceInformationRequest) unmarshal("messages/bermdrip/dMSStatusRequestMsg.xml"));
		
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSStatusMsg", response);
	}
	
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSInventoryMsg", "messages/bermdrip/mocked_dMS_inventory_success_response.xml");
	}

	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/bermdrip/mocked_dMS_success_status_response.xml");
	}
		
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DMSInventoryMsg response = tmddClient.dlDMSInventoryRequest((DeviceInformationRequest) unmarshal("messages/bermdrip/dMSInventoryRequestMsg.xml"));
		
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "dMSInventoryMsg", response);
	}

	private void thenVerifyDynacReceivedDeviceResponseMessage() throws Exception {
		verifyDynacReceivedReply("/tmdd:deviceControlResponseMsg", "messages/bermdrip/mocked_device_control_success_response.xml");
	}

	private void givenBermdripServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}

	private void givenDynacReturnsMessageReceiptOk() throws Exception {
		givenDynacReturns("messages/bermdrip/mocked_c2cMessageReceipt_success_response.xml");
	}

	private void whenDripStatusPublishIsReceived() throws Exception {
		whenMessageFromCdmIsReceived("messages/bermdrip/mocked_bermdrip_status_success_publish.xml");
	}

	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/bermdrip/dMSStatusUpdateMsg.xml");
	}

	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDMSInventoryUpdate((DMSInventoryUpdate) unmarshal(requestFilename));
		
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}
	
	private void whenBermdripStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}

	private void thenVerifyBermdripServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/bermdrip/bermdrip_status_subscription.xml");
	}

	private void thenVerifyBermdripServiceReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/bermdrip/bermdrip_inventory_publish.xml");
	}

	private void thenVerifyDripServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/bdrp:bermdrip_control_request/bdrp:bermdrip_control/bdrp:bermdrip_spec", "messages/bermdrip/bermdrip_control_request.xml");
	}

	protected void whenControlRequestIsReceived(String requestFilename) throws Exception {
		DeviceControlResponse response = tmddClient.dlDMSControlRequest((DMSControlRequestCharm) unmarshal(requestFilename));
		
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "deviceControlResponseMsg", response);
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/bermdrip/dmsInventorySubscriptionMsg.xml");
	}
	
	protected void thenVerifyDynacReceivedSoapFault(String faultstring) throws Exception {
		Object status = dynacRawResponse.getInboundProperty("http.status");
		String payload = dynacRawResponse.getPayloadAsString();
		
		assertThat(status).isEqualTo(500);

		payload = payload.replaceFirst("<soap:Fault>", "<soap:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");

		assertXpathEvaluatesTo(faultstring, "/soap:Fault/faultstring", payload);
	}
}
