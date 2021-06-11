package com.cgi.charm.dynac.traveltime;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.DetectorInventoryMsg;
import org.tmdd._3.messages.DetectorInventoryUpdate;
import org.tmdd._3.messages.DetectorStatusMsg;
import org.tmdd._3.messages.DeviceInformationSubscription;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class TravelTimeTest extends DynacFunctionalTestCase {
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/traveltime/traveltime_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/traveltime/deviceInformationSubscriptionInventoryMsg.xml");
	}
	
	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenInventorySubscriptionIsReceived("messages/traveltime/deviceInformationSubscriptionInventoryMsg.xml");
		thenVerifyMsiReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventorySubscriptionIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyMsiReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/traveltime/traveltime_inventory_subscription.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/traveltime/traveltime_inventory_publish.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/traveltime/detectorInventoryUpdateMsg.xml");
	}
	
	@Test
	public void shouldInventoryPublishNtcipToCdm() throws Exception {
		whenInventoryUpdateIsReceived("messages/traveltime/detectorInventoryUpdateMsg.xml");
		thenVerifyDetectorReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDetectorInventoryUpdate( (DetectorInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyDetectorReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/traveltime/traveltime_inventory_publish.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenStatusSubscriptionIsReceivedFromDynac("messages/traveltime/deviceInformationSubscriptionStatusMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyPrismServiceReceivedStatusSubscription();
	}
	private void whenStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyPrismServiceReceivedStatusSubscription()throws Exception {
		verifyCdmAsyncMessageReceived("messages/traveltime/traveltime_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/traveltime/traveltime_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}
	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/traveltime/detectorStatusUpdateMsg.xml");
	}
	
	@Test
	public void shouldInventoryInfo() throws Exception {
		givenCdmServiceReturns("messages/traveltime/traveltime_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		DetectorInventoryMsg response = tmddClient.dlDetectorInventoryRequest( unmarshal("messages/traveltime/detectorInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","detectorInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorInventoryMsg","messages/traveltime/detectorInventoryMsg.xml");
	}
	
	@Test
	public void shouldStatusInfo() throws Exception {
		givenCdmServiceReturns("messages/traveltime/traveltime_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		DetectorStatusMsg response = tmddClient.dlDetectorStatusRequest( unmarshal("messages/traveltime/deviceInformationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","detectorStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:detectorStatusMsg","messages/traveltime/detectorStatusMsg.xml");
	}
}
