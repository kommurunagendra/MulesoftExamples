package com.cgi.charm.dynac.hardshoulder;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.dialogs.MSGErrorReport;
import org.tmdd._3.messages.LinkInventoryMsg;
import org.tmdd._3.messages.LinkInventoryUpdate;
import org.tmdd._3.messages.LinkStatusMsg;
import org.tmdd._3.messages.LinkStatusUpdate;
import org.tmdd._3.messages.TrafficNetworkInformationRequest;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class HardshoulderTest extends DynacFunctionalTestCase {
	
	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/hardshoulder/hardshoulder_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/hardshoulder/trafficInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryUpdateIsReceived("messages/hardshoulder/linkInventoryUpdateMsg.xml");
		thenVerifyHardshoulderReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	private void thenVerifyHardshoulderReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/hardshoulder/hardshoulder_inventory_publish.xml");
	}

	@Test
	public void shouldStatusSubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/hardshoulder/hardshoulder_status_subscription.xml");
		thenVerifyDynacReceivedDeviceStatusSubscription();
	}
	private void thenVerifyDynacReceivedDeviceStatusSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/hardshoulder/trafficNetworkInformationStatusSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenHardshoulderStatusPublishIsReceived("messages/hardshoulder/linkStatusUpdate.xml");
		thenVerifyHardshoulderServiceReceivedStatusPublish();
	}
	private void whenHardshoulderStatusPublishIsReceived(String requestFilename) throws MSGErrorReport, JAXBException {
		C2CMessageReceipt response = tmddClient.dlLinkStatusUpdate( (LinkStatusUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);		
	}
	private void thenVerifyHardshoulderServiceReceivedStatusPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/hardshoulder/hardshoulder_status_publish.xml");

	}

	@Test
	public void shouldHardshoulderInventoryInfo() throws Exception {
		givenHardshoulderServiceReturns("messages/hardshoulder/hardshoulder_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		LinkInventoryMsg response = tmddClient.dlLinkInventoryRequest( unmarshal("messages/hardshoulder/linkInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages","linkInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:linkInventoryMsg","messages/hardshoulder/linkInventoryMsg.xml");
	}
	
	@Test
	public void shouldHardshoulderStatusInfo() throws Exception {
		givenHardshoulderServiceReturns("messages/hardshoulder/hardshoulder_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		LinkStatusMsg response = tmddClient.dlLinkStatusRequest((TrafficNetworkInformationRequest) unmarshal("messages/hardshoulder/linkStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "linkStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:linkStatusMsg", "messages/hardshoulder/linkStatusMsg.xml");
	}
	
	private void givenHardshoulderServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}

	protected void whenInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlLinkInventoryUpdate( (LinkInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
}
