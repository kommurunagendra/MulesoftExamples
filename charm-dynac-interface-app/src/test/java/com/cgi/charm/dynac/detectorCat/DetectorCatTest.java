package com.cgi.charm.dynac.detectorCat;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DetectorDataMsg;
import org.tmdd._3.messages.DetectorInventoryMsg;
import org.tmdd._3.messages.DetectorInventoryUpdate;
import org.tmdd._3.messages.DetectorStatusMsg;
import org.tmdd._3.messages.DeviceInformationRequest;
import org.tmdd._3.messages.DeviceInformationSubscription;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class DetectorCatTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_cat/detector_cat_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}

	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/detector_cat/deviceInformationSubscriptionMsg.xml");
	}

	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/detector_cat/deviceInformationSubscriptionMsg.xml");
		thenVerifyDetectorReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_cat/detector_cat_inventory_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_cat/detector_cat_status_publish.xml");
		thenVerifyDynacReceivedStatusPublish();
	}

	private void thenVerifyDynacReceivedStatusPublish() throws Exception {
		verifyDynacReceivedMessage("messages/detector_cat/detectorStatusUpdateMsg.xml");
	}
	
	@Test
	public void shouldDataPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_cat/detector_cat_data_publish.xml");
		thenVerifyDynacReceivedDataPublish();
	}

	private void thenVerifyDynacReceivedDataPublish() throws Exception {
		verifyDynacReceivedMessage("messages/detector_cat/detectorDataUpdateMsg.xml");
	}

	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryPublishIsReceived("messages/detector_cat/detectorInventoryUpdateMsg.xml");
		thenVerifyDetectorReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	
	protected void whenInventoryPublishIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDetectorInventoryUpdate( (DetectorInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_cat/detector_cat_inventory_publish.xml");
	}
	
	@Test
	public void shouldInventoryPublishCdmToNtcip() throws Exception {
		whenMessageFromCdmIsReceived("messages/detector_cat/detector_cat_inventory_publish.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/detector_cat/detectorInventoryUpdateMsgCDMToNTCIP.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenDetectorCatStatusSubscriptionIsReceivedFromDynac("messages/detector_cat/deviceInformationStatusSubscriptionMsg.xml");
		thenVerifyDetectorCatServiceReceivedStatusSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	private void whenDetectorCatStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorCatServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_cat/detector_cat_status_subscription.xml");
	}
	
	
	@Test
	public void shouldDetectorCatInventoryInfo() throws Exception {
		givenDetectorCatServiceReturns("messages/detector_cat/detector_cat_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DetectorInventoryMsg response = tmddClient.dlDetectorInventoryRequest((DeviceInformationRequest) unmarshal("messages/detector_cat/detectorInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "detectorInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorInventoryMsg", "messages/detector_cat/detectorInventoryMsg.xml");
	}
	
	@Test
	public void shouldDetectorCatStatusInfo() throws Exception {
		givenDetectorCatServiceReturns("messages/detector_cat/detector_cat_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DetectorStatusMsg response = tmddClient.dlDetectorStatusRequest((DeviceInformationRequest) unmarshal("messages/detector_cat/detectorStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "detectorStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorStatusMsg", "messages/detector_cat/detectorStatusMsg.xml");
	}
	
	@Test
	public void shouldDetectorCatDataInfo() throws Exception {
		givenDetectorCatServiceReturns("messages/detector_cat/detector_cat_info_reply_data.xml");
		whenDataInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedDataInfoReply();
	}
	private void whenDataInfoIsReceivedFromDynac() throws Exception {
		DetectorDataMsg response = tmddClient.dlDetectorDataRequest( unmarshal("messages/detector_cat/detectorDataRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","detectorDataMsg", response);
	}
	private void thenVerifyDynacReceivedDataInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorDataMsg", "messages/detector_cat/detectorDataMsg.xml");
	}
	
	private void givenDetectorCatServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
	
	@Test
	public void shouldDataSubscription() throws Exception {
		whenDetectorCatDataSubscriptionIsReceivedFromDynac("messages/detector_cat/deviceInformationDataSubscriptionMsg.xml");
		thenVerifyDetectorCatServiceReceivedDataSubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	private void whenDetectorCatDataSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorCatServiceReceivedDataSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/detector_cat/detector_cat_data_subscription.xml");
	}
}
