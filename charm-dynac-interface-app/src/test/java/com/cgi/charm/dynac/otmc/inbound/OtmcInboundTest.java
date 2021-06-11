package com.cgi.charm.dynac.otmc.inbound;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.AnyTypeObject;
import org.tmdd._3.messages.AnyTypeUpdate;
import org.tmdd._3.messages.DeviceInformationSubscription;
import org.tmdd._3.messages.OrganizationInformationMsg;
import org.tmdd._3.messages.OrganizationInformationSubscriptionMsg;
import org.tmdd._3.messages.OrganizationInformationUpdateMsg;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class OtmcInboundTest extends DynacFunctionalTestCase {

	@Test
	public void shouldOrganizationSubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/inbound/otmc_organisation_subscription.xml");
		thenVerifyDynacReceivedDeviceOrgSubscription();
	}
	private void thenVerifyDynacReceivedDeviceOrgSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/inbound/organizationInformationSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldOrganizationPublish() throws Exception {
		whenOrganizationInformationUpdateMsgIsReceived("messages/otmc/inbound/organizationInformationUpdateMsg.xml");
		thenVerifyReceivedOtmcOrganisationPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenOrganizationInformationUpdateMsgIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationUpdate((OrganizationInformationUpdateMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyReceivedOtmcOrganisationPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/inbound/otmc_organisation_publish.xml");
	}
	
	@Test
	public void shouldInventorySubscriptionNtcipToCdm() throws Exception {
		whenDeviceInformationSubscriptionIsReceived("messages/otmc/inbound/deviceInformationSubscription.xml");
		thenVerifyOtmcReceivedInventorySubscription();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenDeviceInformationSubscriptionIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlDeviceInformationSubscription((DeviceInformationSubscription) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}
	private void thenVerifyOtmcReceivedInventorySubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/inbound/otmc_inventory_subscription.xml");
	}

	@Test
	public void shouldInventoryPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/inbound/otmc_inventory_publish.xml");
		thenVerifyDynacReceivedInventoryUpdate();
	}
	private void thenVerifyDynacReceivedInventoryUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/inbound/anyTypeInventoryUpdateMsg.xml");
	}
	
	@Test
	public void shouldInventoryPublishNtcipToCdm() throws Exception {
		whenAnyTypeInventoryUpdateIsReceived("messages/otmc/inbound/anyTypeInventoryUpdateMsg_NTCIP_CDM.xml");
		thenVerifyOtmcReceivedInventoryPublish();
		thenVerifyDynacReceivedMessageReceipt();
	}
	protected void whenAnyTypeInventoryUpdateIsReceived(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlAnyTypeInventoryUpdate((AnyTypeUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration","c2cMessageReceipt", response);
	}
	private void thenVerifyOtmcReceivedInventoryPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/inbound/otmc_inventory_publish_NTCIP_CDM.xml");
	}
	
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenRouteStatusSubscriptionIsReceivedFromDynac("messages/otmc/inbound/organizationInformationSubscriptionMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyRouteServiceReceivedStatusSubscription();
	}
	private void whenRouteStatusSubscriptionIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlOrganizationInformationSubscription((OrganizationInformationSubscriptionMsg) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}
	private void thenVerifyRouteServiceReceivedStatusSubscription() throws Exception {
		verifyCdmAsyncMessageReceived("messages/otmc/inbound/otmc_status_subscription.xml");
	}

	@Test
	public void shouldStatusPublish() throws Exception {
		whenMessageFromCdmIsReceived("messages/otmc/inbound/otmc_status_publish.xml");
		thenVerifyDynacReceivedStatusUpdate();
	}
	private void thenVerifyDynacReceivedStatusUpdate() throws Exception {
		verifyDynacReceivedMessage("messages/otmc/inbound/centerActiveUpdateMsg.xml");
	}

	@Test
	public void shouldOtmcInboundInventoryInfo() throws Exception {
		givenOtmcInboundServiceReturns("messages/otmc/inbound/otmc_info_reply_inventory.xml");
		whenInventoryInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedInventoryInfoReply();
	}
	private void whenInventoryInfoIsReceivedFromDynac() throws Exception {
		AnyTypeObject response = tmddClient.dlAnyTypeInventoryRequest(unmarshal("messages/otmc/inbound/anyTypeInventoryRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "anyTypeInventoryMsg", response);
	}
	private void thenVerifyDynacReceivedInventoryInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:anyTypeInventoryMsg", "messages/otmc/inbound/anyTypeInventoryMsg.xml");
	}
	
	@Test
	public void shouldOtmcInboundStatusInfo() throws Exception {
		givenOtmcInboundServiceReturns("messages/otmc/inbound/otmc_info_reply_status.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		AnyTypeObject response = tmddClient.dlAnyTypeStatusRequest(unmarshal("messages/otmc/inbound/anyTypeStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "anyTypeStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:anyTypeStatusMsg", "messages/otmc/inbound/anyTypeStatusMsg.xml");
	}
	
	@Test
	public void shouldOtmcInboundOrganizationInfo() throws Exception {
		givenOtmcInboundServiceReturns("messages/otmc/inbound/otmc_info_reply_organization.xml");
		whenOrganizationIsReceivedFromDynac();
		thenVerifyDynacReceivedOrganizationReply();
	}
	private void whenOrganizationIsReceivedFromDynac() throws Exception {
		OrganizationInformationMsg response = tmddClient.dlOrganizationInformationRequest(unmarshal("messages/otmc/inbound/organizationInformationRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "organizationInformationMsg", response);
	}
	private void thenVerifyDynacReceivedOrganizationReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:organizationInformationMsg", "messages/otmc/inbound/organizationInformationMsg.xml");
	}
	
	private void givenOtmcInboundServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
}
