package com.cgi.charm.sc.tlc.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TlcInventoryTest extends BaseTlcTest {

	//@Test
	public void shouldVerifyTlcInventorySubscription() throws Exception {
		inventorySubscriptionReceived.await();
		verifyInventorySubscription();
	}

	@Test
	public void shouldTestTlcInventoryPublish() throws Exception {
		whenTlcInventoryPublishIsReceived();
		thenVerifyTlcInventory();
	}


	private void thenVerifyTlcInventory() throws Exception {
		//assertEquals(1, tlcQueryService.getAllTlcs().size());
	}
}
