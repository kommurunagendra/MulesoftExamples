package com.cgi.charm.dynac.nina;

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
public class NinaTest extends DynacFunctionalTestCase {

	@Test
	public void shouldInventorySubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/nina/nina_inventory_subscription.xml");
		thenVerifyDynacReceivedDeviceSubscription();
	}
	private void thenVerifyDynacReceivedDeviceSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/nina/organizationInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldInventoryPublish() throws Exception {
		whenInventoryPublishIsReceived("messages/nina/organizationInformationUpdateMsg.xml");
		thenVerifyRouteReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenInventoryPublishIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationUpdate((OrganizationInformationUpdateMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/nina/nina_inventory_publish.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenRouteStatusSubscriptionIsReceivedFromDynac("messages/nina/organizationInformationSubscriptionMsgStatus.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyRouteServiceReceivedStatusSubscription();
	}
	private void whenRouteStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationSubscription((OrganizationInformationSubscriptionMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyRouteServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/nina/nina_status_subscription.xml");
	}
	
	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/nina/nina_status_publish.xml");
		thenVerifyDynacReceivedStatusPublish();
	}
	private void thenVerifyDynacReceivedStatusPublish() throws Exception {
		verifyDynacReceivedMessage("messages/nina/centerActiveUpdateMsg.xml");
	}
	
	@Test
	public void shouldNinaInventoryInfo() throws Exception {
		givenNinaServiceReturns("messages/nina/nina_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		OrganizationInformationMsg response = tmddClient.dlOrganizationInformationRequest(unmarshal("messages/nina/organizationInformationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "organizationInformationMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:organizationInformationMsg", "messages/nina/organizationInformationMsg.xml");
	}

	@Test
	public void shouldNinaStatusInfo() throws Exception {
		givenNinaServiceReturns("messages/nina/nina_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		CenterActiveVerificationResponse response = tmddClient.dlCenterActiveVerificationRequest(unmarshal("messages/nina/centerActiveVerificationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "centerActiveVerificationResponseMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:centerActiveVerificationResponseMsg", "messages/nina/centerActiveVerificationResponseMsg.xml");
	}
	
	private void givenNinaServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
	
}
