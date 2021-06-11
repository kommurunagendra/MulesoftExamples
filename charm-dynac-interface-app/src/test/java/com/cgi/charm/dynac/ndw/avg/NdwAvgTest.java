package com.cgi.charm.dynac.ndw.avg;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.CenterActiveVerificationResponse;
import org.tmdd._3.messages.OrganizationInformationMsg;
import org.tmdd._3.messages.OrganizationInformationSubscriptionMsg;
import org.tmdd._3.messages.OrganizationInformationUpdateMsg;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class NdwAvgTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/ndw_avg/ndw_avg_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/ndw_avg/organizationInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryPublishIsReceived("messages/ndw_avg/organizationInformationUpdateMsg.xml");
		thenVerifyRouteReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryPublishIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationUpdate((OrganizationInformationUpdateMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/ndw_avg/ndw_avg_inventory_publish.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenRouteStatusSubscriptionIsReceivedFromDynac("messages/ndw_avg/organizationInformationSubscriptionMsgStatus.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyRouteServiceReceivedStatusSubscription();
	}
	private void whenRouteStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationSubscription((OrganizationInformationSubscriptionMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/ndw_avg/ndw_avg_status_subcription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/ndw_avg/ndw_avg_status_publish.xml");
		thenVerifyDynacReceivedStatusPublish();
	}
	private void thenVerifyDynacReceivedStatusPublish() throws Exception {
		verifyDynacReceivedMessage("messages/ndw_avg/centerActiveUpdateMsg.xml");
	}
	
	@Test
	public void shouldNdwAvgInventoryInfo() throws Exception {
		givenNdwAvgServiceReturns("messages/ndw_avg/ndw_avg_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		OrganizationInformationMsg response = tmddClient.dlOrganizationInformationRequest(unmarshal("messages/ndw_avg/organizationInformationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "organizationInformationMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:organizationInformationMsg", "messages/ndw_avg/organizationInformationMsg.xml");
	}
	
	@Test
	public void shouldNdwAvgStatusInfo() throws Exception {
		givenNdwAvgServiceReturns("messages/ndw_avg/ndw_avg_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		CenterActiveVerificationResponse response = tmddClient.dlCenterActiveVerificationRequest(unmarshal("messages/ndw_avg/centerActiveVerificationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "centerActiveVerificationResponseMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:centerActiveVerificationResponseMsg", "messages/ndw_avg/centerActiveVerificationResponseMsg.xml");
	}
	
	private void givenNdwAvgServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
