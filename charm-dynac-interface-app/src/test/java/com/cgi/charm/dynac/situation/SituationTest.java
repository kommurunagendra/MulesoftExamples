package com.cgi.charm.dynac.situation;

import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.marshal;
import static com.cgi.charm.dynac.DynacInterfaceJAXBUtil.unmarshal;

import org.junit.Test;
import org.ntcip.c2c_message_administration.C2CMessageReceipt;
import org.tmdd._3.messages.AnyTypeObject;
import org.tmdd._3.messages.AnyTypeUpdate;
import org.tmdd._3.messages.DMSMessageInventoryUpdateMsg;
import org.tmdd._3.messages.DetectorStatusMsg;
import org.tmdd._3.messages.DeviceInformationRequest;

import com.cgi.charm.dynac.DynacFunctionalTestCase;

/**
 * @author CHARM CGI TEAM
 */
public class SituationTest extends DynacFunctionalTestCase {
	@Test
	public void shouldStatusSubscription() throws Exception {
		whenMessageFromCdmIsReceived("messages/situation/situation_status_subscription.xml");
		thenVerifyDynacReceivedFullEventUpdateSubscription();
	}
	private void thenVerifyDynacReceivedFullEventUpdateSubscription() throws Exception {
		verifyDynacReceivedMessage("messages/situation/fullEventUpdateSubscriptionMsg.xml");
	}
	
	@Test
	public void shouldStatusPublishNtcipToCdm() throws Exception {
		whenStatusPublishIsReceivedFromDynac("messages/situation/anyTypeStatusUpdateMsg.xml");
		thenVerifyDynacReceivedMessageReceipt();
		thenVerifyServiceReceivedStatusPublish();
	}
	private void whenStatusPublishIsReceivedFromDynac(String requestFilename) throws Exception {
		C2CMessageReceipt response = tmddClient.dlAnyTypeStatusUpdate((AnyTypeUpdate) unmarshal(requestFilename));
		dynacResponse = marshal("http://www.ntcip.org/c2c-message-administration", "c2cMessageReceipt", response);
	}	
	private void thenVerifyServiceReceivedStatusPublish() throws Exception {
		verifyCdmAsyncMessageReceived("messages/situation/situation_status_publish.xml");
	} 
	
	@Test
	public void shouldSituationStatusInfo() throws Exception {
		givenSituationServiceReturns("messages/situation/situation_info_reply.xml");
		whenStatusInfoIsReceivedFromDynac();
		thenVerifyDynacReceivedStatusInfoReply();
	}
	private void whenStatusInfoIsReceivedFromDynac() throws Exception {
		AnyTypeObject response = tmddClient.dlAnyTypeStatusRequest(unmarshal("messages/situation/anyTypeStatusRequestMsg.xml"));
		dynacResponse = marshal("http://www.tmdd.org/3/messages", "anyTypeStatusMsg", response);
	}
	private void thenVerifyDynacReceivedStatusInfoReply() throws Exception {
		verifyDynacReceivedReply("/tmdd:dMSStatusMsg", "messages/situation/anyTypeStatusMsg.xml");
	}
	private void givenSituationServiceReturns(String resourceFilename) throws Exception {
		givenCdmServiceReturns(resourceFilename);
	}
	
}
