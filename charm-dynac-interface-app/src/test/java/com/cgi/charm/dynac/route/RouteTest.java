package com.cgi.charm.dynac.route;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.LinkInventoryMsg;
import org.tmdd._3.messages.LinkInventoryUpdate;
import org.tmdd._3.messages.LinkStatusMsg;
import org.tmdd._3.messages.TrafficNetworkInformationSubscription;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class RouteTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/route/route_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/route/trafficNetworkInformationSubscriptionMsg.xml");
	}

	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryPublishIsReceived("messages/route/linkInventoryUpdateMsg.xml");
		thenVerifyRouteReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryPublishIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlLinkInventoryUpdate((LinkInventoryUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/route/route_inventory_publish.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenRouteStatusSubscriptionIsReceivedFromDynac("messages/route/trafficNetworkInformationStatusSubscriptionMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyRouteServiceReceivedStatusSubscription();
	}
	private void whenRouteStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlTrafficNetworkInformationSubscription((TrafficNetworkInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/route/route_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/route/route_status_publish.xml");
		thenVerifyDynacReceivedStatusPublish();
	}

	private void thenVerifyDynacReceivedStatusPublish() throws Exception {
		verifyDynacReceivedMessage("messages/route/linkStatusUpdateMsg.xml");
	}
		
	@Test
	public void shouldRouteInventoryInfo() throws Exception {
		givenRouteServiceReturns("messages/route/route_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		LinkInventoryMsg response = tmddClient.dlLinkInventoryRequest(unmarshal("messages/route/linkInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "linkInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:linkInventoryMsg", "messages/route/linkInventoryMsg.xml");
	}
	
	@Test
	public void shouldRouteStatusInfo() throws Exception {
		givenRouteServiceReturns("messages/route/route_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		LinkStatusMsg response = tmddClient.dlLinkStatusRequest(unmarshal("messages/route/linkStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "linkStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:linkStatusMsg", "messages/route/linkStatusMsg.xml");
	}
	
	
	private void givenRouteServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
