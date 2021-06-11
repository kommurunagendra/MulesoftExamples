package com.cgi.charm.dynac.detectorSi;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLUnit.buildTestDocument;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DetectorDataMsg;
import org.tmdd._3.messages.DetectorInventoryMsg;
import org.tmdd._3.messages.DetectorInventoryUpdate;
import org.tmdd._3.messages.DetectorStatusMsg;
import org.tmdd._3.messages.DeviceControlResponse;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.w3c.dom.Document;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorSiTest extends DynacFunctionalTestCase {
	
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_si/detector_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/detector_si/deviceInformationSubscriptionMsg.xml");
	}

	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/detector_si/deviceInformationSubscriptionMsg.xml");
		thenVerifyDetectorReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_si/detector_inventory_subscription.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/detector_si/detectorInventoryUpdateMsg.xml");
		thenVerifyDetectorReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDetectorInventoryUpdate( (DetectorInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_si/detector_inventory_publish.xml");
	}

	@Test
	public void shouldInventoryPublishVariant() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_si/detector_inventory_publish_nina_variant.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/detector_si/detectorInventoryUpdateMsg_nina_variant.xml");
	}
	
	@Test
	public void shouldDetectorStatusInfo() throws Exception {
		givenDetectorServiceReturns("messages/detector_si/detector_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DetectorStatusMsg response = tmddClient.dlDetectorStatusRequest((DeviceInformationRequest) unmarshal("messages/detector_si/detectorStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "detectorStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/detector_si/detectorStatusMsg.xml");
	}
	
	@Test
	public void shouldDetectorInventoryInfo() throws Exception {
		givenDetectorServiceReturns("messages/detector_si/detector_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DetectorInventoryMsg response = tmddClient.dlDetectorInventoryRequest( unmarshal("messages/detector_si/detectorInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","detectorInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorInventoryMsg","messages/detector_si/detectorInventoryMsg.xml");
	}
	
	@Test
	public void shouldDetectorDataInfo() throws Exception {
		givenDetectorServiceReturns("messages/detector_si/detector_info_reply_data.xml");
		whenDataInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedDataInfoReply();
	}
	private void whenDataInfoIsReceivedFromDynac() throws Exception {
		DetectorDataMsg response = tmddClient.dlDetectorDataRequest( unmarshal("messages/detector_si/detectorDataRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","detectorDataMsg", response);
	}
	private void thenVerifyDynacReceivedDataInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorDataMsg","messages/detector_si/detectorDataMsg.xml");
	}
	private void givenDetectorServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenDetectorStatusSubscriptionIsReceivedFromDynac("messages/detector_si/deviceInformationStatusSubscriptionMsg.xml");
		thenVerifyDetectorServiceReceivedStatusSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	private void whenDetectorStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_si/detector_status_subscription.xml");
	}
	
	@Test
	public void shouldDataSubscription() throws Exception {
		whenDetectorDataSubscriptionIsReceivedFromDynac("messages/detector_si/deviceInformationDataSubscriptionMsg.xml");
		thenVerifyDetectorServiceReceivedDataSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	private void whenDetectorDataSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorServiceReceivedDataSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_si/detector_data_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_si/detector_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}
	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/detector_si/detectorStatusUpdateMsg.xml");
	}
	
	@Test
	public void shouldDataPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_si/detector_data_publish.xml");
		thenVerifyDynacReceivedDataUpdate();
	}
	private void thenVerifyDynacReceivedDataUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/detector_si/detectorDataUpdateMsg.xml");
	}
	
	@Test
	public void shouldControlMessage() throws Exception {
		givenDetectorServiceReturns("messages/detector_si/detector_control_reply.xml");
		whenControlRequestIsReceived("messages/detector_si/detectorControlRequestMsg.xml");
		thenVerifyDetectorServiceReceivedControlRequest();
		thenVerifyDynacReceivedDetectorControlResponse();
	}
	protected void whenControlRequestIsReceived(String requestFilename)	throws Exception {
		DeviceControlResponse response = tmddClient.dlDetectorControlRequest(unmarshal(requestFilename));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","deviceControlResponseMsg", response);
	}
	protected void thenVerifyDetectorServiceReceivedControlRequest() throws Exception {
		verifyCdmMessageReceived("/detector:detector_control_request/detector:control","messages/detector_si/detector_control_request.xml");
	}	
	protected void thenVerifyDynacReceivedDetectorControlResponse() throws Exception {
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("requested changes completed", "/tmdd:deviceControlResponseMsg/request-status", testDocument);
	}
}
