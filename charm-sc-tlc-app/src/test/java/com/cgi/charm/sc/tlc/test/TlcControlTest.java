package com.cgi.charm.sc.tlc.test;

import org.junit.Test;

public class TlcControlTest extends BaseTlcTest {


	@Test
	public void shouldTestTlcControlRequest() throws Exception {

		whenTlcInventoryPublishIsReceived();

		whenTlcControlRequestReceived();

		thenVerifyControlReplyResponse();

	}

	public void whenTlcControlRequestReceived() throws Exception {
		messageFromCdm("messages/tlc_control_request.xml");
	}
	
	@Test
	public void shouldTestTlcControlRequestWithEmptyDatastore() throws Exception {

		whenTlcControlRequestReceived();

		thenVerifyControlReplyResponse();

	}
}
