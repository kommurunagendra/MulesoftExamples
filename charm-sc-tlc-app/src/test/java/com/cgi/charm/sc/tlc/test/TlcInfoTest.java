package com.cgi.charm.sc.tlc.test;

import org.junit.Ignore;
import org.junit.Test;


public class TlcInfoTest extends BaseTlcTest {

	
	@Test
	public void shouldTestTlcInfoRequestStatus() throws Exception {
		whenTlcInventoryPublishIsReceived();
		givenStatusSubscriptionRequested();
		whenTlcInfoRequestStatusReceived();
		/*thenVerifyTlcInfoReplyStatusResponse();*/
	}

	public void whenTlcInfoRequestStatusReceived() throws Exception {
		messageFromCdm("messages/tlc_info_request_status.xml");
	}

	public void thenVerifyTlcInfoReplyStatusResponse() throws Exception {
		String response = responseReceived.getPayloadAsString();
		verifyResponse(
				"/tlc:tlc_info_reply/tlc:subscription_config/tlc:tlc_publish",
				"messages/tlc_info_reply_status.xml", response);
	}

	@Test
	public void shouldTestTlcInfoRequestInventory() throws Exception {
		whenTlcInventoryPublishIsReceived();
		givenStatusSubscriptionRequested();
		whenTlcInfoRequestInventoryReceived();
		thenVerifyTlcInfoReplyInventoryResponse();
	}

	public void whenTlcInfoRequestInventoryReceived() throws Exception {
		messageFromCdm("messages/tlc_info_request_inventory.xml");
	}

	public void thenVerifyTlcInfoReplyInventoryResponse() throws Exception {
		String response = responseReceived.getPayloadAsString();
//		verifyResponse(
//				"/tlc:tlc_info_reply/tlc:subscription_config/tlc:tlc_publish",
//				"messages/tlc_info_reply_inventory.xml", response);
	}
}
