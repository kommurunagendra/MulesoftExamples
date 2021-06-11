package com.cgi.charm.dynac;

import static org.assertj.core.api.Assertions.assertThat;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathsEqual;
import static org.custommonkey.xmlunit.XMLUnit.buildTestDocument;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.TransformerException;
import javax.xml.ws.BindingProvider;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.client.MuleClient;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.functional.FunctionalTestNotificationListener;
import org.mule.tck.junit4.FunctionalTestCase;
import org.tmdd._3.dialogs.TmddOCSoapHttpServicePortType;
import org.tmdd._3.dialogs.TmddOwnerCenterService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlunit.diff.Diff;
import org.xmlunit.builder.DiffBuilder;

/**
 * @author CHARM CGI TEAM
 */
public abstract class DynacFunctionalTestCase extends FunctionalTestCase {
	protected MuleClient client;
	protected String dynacResponse;
	protected FunctionalTestComponent cdmMock;
	protected FunctionalTestComponent cdmMockAsync;
	protected FunctionalTestComponent dynacMock;
	protected FunctionalTestComponent iventorySubscription;

	private CountDownLatch dynacMessageReceived = new CountDownLatch(1);
	private CountDownLatch inventorySubscriptionReceived = new CountDownLatch(1);
	private CountDownLatch cdmMessageReceived = new CountDownLatch(1);
	private CountDownLatch cdmAsyncMessageReceived = new CountDownLatch(1);

	protected static TmddOCSoapHttpServicePortType tmddClient;

	private static final Logger LOGGER = Logger.getLogger(DynacFunctionalTestCase.class);

	@Override
	protected String[] getConfigFiles() {
		return new String[] { 
				"src/main/app/charm-dynac-interface-bermdrip.xml",
				"src/main/app/charm-dynac-interface-bridge.xml",
				"src/main/app/charm-dynac-interface-config.xml",
				"src/main/app/charm-dynac-interface-detector-cat.xml", 
				"src/main/app/charm-dynac-interface-detector-si.xml",
				"src/main/app/charm-dynac-interface-drip.xml", 
				"src/main/app/charm-dynac-interface-hardshoulder.xml",
				"src/main/app/charm-dynac-interface-main.xml", 
				"src/main/app/charm-dynac-interface-msgtrace-config.xml",				
				"src/main/app/charm-dynac-interface-msi.xml", 
				"src/main/app/charm-dynac-interface-ndw-avg.xml",
				"src/main/app/charm-dynac-interface-ndw-sg.xml",
				"src/main/app/charm-dynac-interface-nina.xml", 
				"src/main/app/charm-dynac-interface-otmc-inbound.xml", 
				"src/main/app/charm-dynac-interface-otmc-outbound.xml", 
				"src/main/app/charm-dynac-interface-otmc-services.xml",
				"src/main/app/charm-dynac-interface-parking-facility.xml", 
				"src/main/app/charm-dynac-interface-prism.xml",
				"src/main/app/charm-dynac-interface-route.xml",
				"src/main/app/charm-dynac-interface-situation.xml",
				"src/main/app/charm-dynac-interface-tlc.xml",
				"src/main/app/charm-dynac-interface-traveltime.xml",
				"src/main/app/charm-dynac-interface-util.xml", 
				"src/main/app/charm-dynac-interface-weather.xml",
				"src/main/app/charm-dynac-interface-wind.xml", 
				"src/test/resources/mock/cdm-mock.xml", 
				"src/test/resources/mock/dynac-mock.xml"				
				};
	}

	/**
	 * Use the actual SOAP stub to verify the calls. This ensures the web
	 * service is entirely valid.
	 */
	@BeforeClass
	public static void setupTmddClient() {
		tmddClient = TmddOwnerCenterService
				.create(DynacFunctionalTestCase.class.getResource("/TMDD.wsdl"), TmddOwnerCenterService.SERVICE)
				.getPort(TmddOCSoapHttpServicePortType.class);

		BindingProvider bp = (BindingProvider) tmddClient;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:41082/");
	}

	@Before
	public void setupXMLUnit() throws Exception {
		Map<String, String> prefixMap = new HashMap<>();
		prefixMap.put("c2c", "http://www.ntcip.org/c2c-message-administration");
		prefixMap.put("tmdd", "http://www.tmdd.org/3/messages");
		prefixMap.put("brg", "http://bridge.cdm.charm.cgi.com");
		prefixMap.put("drp", "http://drip.cdm.charm.cgi.com");
		prefixMap.put("bdrp", "http://bermdrip.cdm.charm.cgi.com");
		prefixMap.put("prism", "http://prism.cdm.charm.cgi.com");
		prefixMap.put("msi", "http://msi.cdm.charm.cgi.com");
		prefixMap.put("detector", "http://detector.cdm.charm.cgi.com");
		prefixMap.put("hardshoulder", "http://hardshoulder.cdm.charm.cgi.com");
		prefixMap.put("tlc", "http://tlc.cdm.charm.cgi.com");
		prefixMap.put("weather", "http://weather.cdm.charm.cgi.com");
		prefixMap.put("soap", "http://schemas.xmlsoap.org/soap/envelope/");
		prefixMap.put("detector-cat", "http://detector.cat.cdm.charm.cgi.com");
		prefixMap.put("route", "http://rs.cdm.charm.cgi.com");
		prefixMap.put("nina", "http://nina.cdm.charm.cgi.com");
		prefixMap.put("traveltime", "http://traveltime.cdm.charm.cgi.com");
		prefixMap.put("ndwAvg", "http://avg.ndw.cdm.charm.cgi.com");
		prefixMap.put("ndwSg", "http://sg.ndw.cdm.charm.cgi.com");
		prefixMap.put("otmcServices", "http://services.otmc.cdm.charm.cgi.com");
		prefixMap.put("otmc", "http://otmc.cdm.charm.cgi.com");
		prefixMap.put("otmcOutbound", "http://outbound.otmc.cdm.charm.cgi.com");
		prefixMap.put("parkingFacility", "http://parkingfacility.cdm.charm.cgi.com");
		prefixMap.put("situation", "http://situation.cdm.charm.cgi.com");
		XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(prefixMap));
	}

	@Before
	public void setup() throws Exception {
		client = muleContext.getClient();
		cdmMock = getFunctionalTestComponent("cdm-mock");
		cdmMockAsync = getFunctionalTestComponent("async-cdm-mock");
		dynacMock = getFunctionalTestComponent("dynac-mock");
		iventorySubscription = getFunctionalTestComponent("iventorySubscription");
		muleContext.registerListener(new FunctionalTestNotificationListener() {
			@Override
			public void onNotification(ServerNotification notification) {
				inventorySubscriptionReceived.countDown();
			}
		}, "iventorySubscription");

		muleContext.registerListener(new FunctionalTestNotificationListener() {
			@Override
			public void onNotification(ServerNotification notification) {
				dynacMessageReceived.countDown();
			}
		}, "dynac-mock");

		muleContext.registerListener(new FunctionalTestNotificationListener() {
			@Override
			public void onNotification(ServerNotification notification) {
				cdmMessageReceived.countDown();
			}
		}, "cdm-mock");

		muleContext.registerListener(new FunctionalTestNotificationListener() {
			@Override
			public void onNotification(ServerNotification notification) {
				cdmAsyncMessageReceived.countDown();
			}
		}, "async-cdm-mock");
	}

	/**
	 * @param expectedResourceFilename
	 * @throws InterruptedException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 * @throws XpathException
	 */
	protected void verifyDynacReceivedMessage(String expectedResourceFilename)
			throws InterruptedException, TransformerException, IOException, SAXException, XpathException {
		inventorySubscriptionReceived.await();
		String actual = (String) iventorySubscription.getLastReceivedMessage();
		LOGGER.info("Actual Message : " + actual);
		String expected = resourceContent(expectedResourceFilename);
		LOGGER.info("Expected Message : " + expected);

		Diff diff = DiffBuilder.compare(expected).withTest(actual).ignoreWhitespace().build();
		Assert.assertFalse(diff.toString(), diff.hasDifferences());
	}

	protected void whenMessageFromCdmIsReceived(String resourceFilename) throws MuleException, IOException {
		client.dispatch("inbound_cdm", new DefaultMuleMessage(resourceContent(resourceFilename), muleContext));
	}

	protected void givenDynacReturns(String resourceFilename) throws IOException {
		dynacMock.setReturnData(resourceContent(resourceFilename));
	}

	protected void thenVerifyDynacReceivedControlResponse() throws Exception {
		LOGGER.info(dynacResponse);
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("request rejected invalid command parameters",
				"/tmdd:deviceControlResponseMsg/request-status", testDocument);
	}

	protected void verifyDynacReceivedReply(String xpath, String expectedResourceFilename) throws Exception {
		LOGGER.info("Dynac response :: " + indent(dynacResponse));
		String expected = indent(resourceContent(expectedResourceFilename));
		assertXpathsEqual(xpath, expected, xpath, dynacResponse);
	}

	protected void thenVerifyDynacReceivedMessageReceipt() throws Exception {
		Document testDocument = buildTestDocument(dynacResponse);
		assertXpathEvaluatesTo("processed", "/c2c:c2cMessageReceipt/informationalText", testDocument);
	}

	protected void givenCdmServiceReturns(String resourceFilename) throws Exception {
		cdmMock.setReturnData(resourceContent(resourceFilename));
	}

	protected void verifyCdmMessageReceived(String xpath, String controlMessage) throws Exception {
		cdmMessageReceived.await(20, TimeUnit.SECONDS);
		assertThat(cdmMock.getReceivedMessagesCount()).isEqualTo(1);
		String actual = (String) cdmMock.getReceivedMessage(1);
		LOGGER.info("Actual :: " + actual);
		String expected = indent(resourceContent(controlMessage));
		LOGGER.info("Expected :: " + expected);
		assertXpathsEqual(xpath, expected, xpath, actual);
	}

	protected void verifyControlMessageReceived(String xpath, String controlMessage) throws Exception {
		cdmMessageReceived.await(20, TimeUnit.SECONDS);
		assertThat(cdmMock.getReceivedMessagesCount()).isEqualTo(1);
		String actual = (String) cdmMock.getReceivedMessage(1);
		LOGGER.info("Actual :: " + actual);
		String expected = indent(resourceContent(controlMessage));
		LOGGER.info("Expected :: " + expected);
		
		final Diff diff = DiffBuilder.compare(actual).withTest(expected).withNodeFilter(node -> !node.getNodeName().equals("uuid") && !node.getNodeName().equals("message_create_date")).ignoreWhitespace().build();
		Assert.assertFalse(diff.toString(), diff.hasDifferences());
	}
	protected void verifyCdmAsyncMessageReceived(String controlMessage) throws Exception {
		cdmAsyncMessageReceived.await(10, TimeUnit.SECONDS);
		String actual = (String) cdmMockAsync.getLastReceivedMessage();
		LOGGER.info("Actual :: " + actual);
		String expected = indent(resourceContent(controlMessage));
		LOGGER.info("Expected :: " + expected);
		
		final Diff diff = DiffBuilder.compare(actual).withTest(expected).withNodeFilter(
				node -> !node.getNodeName().equals("message_id") && !node.getNodeName().equals("message_create_date") && !node.getNodeName().equals("date_time"))
				.ignoreWhitespace().build();

		Assert.assertFalse(diff.toString(), diff.hasDifferences());
	}

	protected String resourceContent(String resourceFilename) throws IOException {
		return FileUtils.readFileToString(new File("src/test/resources/" + resourceFilename));
	}

	protected String indent(String readFileToString) throws TransformerException {
		Transform testTransform2 = new Transform(readFileToString,
				new File("src/test/resources/messages/identity_transform.xsl"));
		testTransform2.setOutputProperty("indent", "no");
		return testTransform2.getResultString();
	}
}
