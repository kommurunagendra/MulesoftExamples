package com.cgi.charm.sc.tlc.test;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathsEqual;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.functional.FunctionalTestNotificationListener;
import org.mule.tck.junit4.FunctionalTestCase;

import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;



public class BaseTlcTest extends FunctionalTestCase {

	public MuleClient client;
	public MuleMessage responseReceived;
	public TlcQueryService tlcQueryService;
	public TlcCommandService commandService;
	public FunctionalTestComponent cdmInventorySubscriptionMock;
	public FunctionalTestComponent cdmStatusSubscriptionMock;
	public CountDownLatch inventorySubscriptionReceived = new CountDownLatch(1);
	public CountDownLatch statusSubscriptionReceived = new CountDownLatch(1);

	@Override
	public String[] getConfigFiles() {
		return new String[] { "src/main/app/charm-sc-tlc-app-config.xml",
				"src/main/app/charm-sc-tlc-app-main.xml",
				"src/main/app/charm-sc-tlc-control.xml",
				"src/main/app/charm-sc-tlc-info.xml",
				"src/main/app/charm-sc-tlc-inventory.xml",
				"src/main/app/charm-sc-tlc-roadside.xml",
				"src/main/app/charm-sc-tlc-status.xml",
				"src/main/app/charm-sc-tlc-util.xml",
				"src/test/resources/mock/tlc-cdm-mock.xml",
				"src/test/resources/mock/tlc-roadside-mock.xml",
				"src/main/app/msgtrace.xml" };
	}

	@Before
	public void setUpForTestCase() throws Exception {
		setupXmlUnit();
		client = muleContext.getClient();
		
		tlcQueryService = muleContext.getRegistry().lookupObject(TlcQueryService.class);
		commandService = muleContext.getRegistry().lookupObject(TlcCommandService.class);
		
		registerListeners();
	}

	public void setupXmlUnit() throws Exception {
		Map<String, String> prefixMap = new HashMap<>();
		prefixMap.put("tlc", "http://tlc.cdm.charm.cgi.com");
		XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(prefixMap));
	}

	public void registerListeners() throws Exception {
		cdmInventorySubscriptionMock = getFunctionalTestComponent("tlc_inventory_subscription");
		cdmStatusSubscriptionMock = getFunctionalTestComponent("tlc_status_publish");
		muleContext.registerListener(new FunctionalTestNotificationListener() {
			@Override
			public void onNotification(ServerNotification notification) {
				inventorySubscriptionReceived.countDown();
			}
		}, "tlc_inventory_subscription");
		muleContext.registerListener(new FunctionalTestNotificationListener() {
			
			@Override
			public void onNotification(ServerNotification notification) {
				statusSubscriptionReceived.countDown();
			}
		}, "tlc_status_publish");

	}

	protected void verifyInventorySubscription() throws Exception {
		String response = (String) cdmInventorySubscriptionMock
				.getReceivedMessage(1);
		verifyResponse("/tlc_inventory_subscription/subscription_config",
				"messages/tlc_inventory_subscription.xml", response);

	}

	protected void whenTlcInventoryPublishIsReceived() throws Exception {
		messageToCdm("messages/tlc_inventory_publish.xml");
	}

	public void tlcStatusSubscription() throws Exception {
		givenStatusSubscriptionRequested();
	}

	public void givenStatusSubscriptionRequested() throws Exception {
		messageToCdm("messages/tlc_status_subscription.xml");
	}
	
	public void tlcStatusSubscriptionFalse() throws Exception {
		givenStatusSubscriptionFalseRequested();
	}

	public void givenStatusSubscriptionFalseRequested() throws Exception {
		messageToCdm("messages/tlc_status_subscription_false.xml");
	}

	public void tlcStatusSubscriptionAllTlcs() throws Exception {
		givenStatusSubscriptionRequestedAllTlcs();
	}

	public void givenStatusSubscriptionRequestedAllTlcs() throws Exception {
		messageToCdm("messages/tlc_status_subscription_all_tlcs.xml");
	}

	public void tlcStatusPublish() throws Exception {
		statusSubscriptionReceived.await();
		thenVerifyTlcStatusPublish();
	}

	private void thenVerifyTlcStatusPublish() throws Exception {

		String response = (String) cdmStatusSubscriptionMock
				.getReceivedMessage(1);
		verifyResponse("/tlc:tlc_status_publish/tlc:tlc_publish",
				"messages/tlc_status_publish_empty.xml", response);
	}

	protected void messageToCdm(String resourceFilename) throws MuleException,
			IOException {
		responseReceived = client.send("tlc_inbound_cdm_async",
				new DefaultMuleMessage(resourceContent(resourceFilename),
						muleContext));
	}

	protected void messageFromCdm(String resourceFilename)
			throws MuleException, IOException {
		responseReceived = client.send("tlc_inbound_cdm_sync",
				new DefaultMuleMessage(resourceContent(resourceFilename),
						muleContext));
	}

	public void thenVerifyControlReplyResponse() throws Exception {
		String response = responseReceived.getPayloadAsString();
		/*verifyResponse("/tlc:tlc_control_reply/tlc:tlc_control_mutations",
				"messages/tlc_control_reply.xml", response);*/
	}

	public void givenInventorySubscription() throws Exception {
		messageToCdm("messages/tlc_inventory_subscription.xml");
		assertEquals(1, tlcQueryService.getAllSubscriptions().size());
	}

	protected void verifyResponse(String xpath,
			String expectedResourceFilename, String actual) throws Exception {
		String expected = resourceContent(expectedResourceFilename);
		assertXpathsEqual(xpath, expected, xpath, actual);
	}

	protected String resourceContent(String resourceFilename)
			throws IOException {
		return FileUtils.readFileToString(new File("src/test/resources/"
				+ resourceFilename));
	}

	public void whenTlcInventoryRemoveIsReceived() throws Exception {
		messageToCdm("messages/tlc_inventory_publish_remove.xml");
		verifyTlcInventoryRemove();
	}

	public void verifyTlcInventoryRemove() {
		assertEquals(0, tlcQueryService.getAllTlcs().size());
	}

}
