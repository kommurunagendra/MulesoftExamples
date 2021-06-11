package com.cgi.charm.dynac;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEventContext;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;
import com.cgi.charm.dynac.ConnectionStatusService.ConnectionStatus;

/**
 * @author CHARM CGI TEAM
 */
public class KeepaliveTest extends FunctionalTestCase {
    private FunctionalTestComponent dynacMock;
    private ConnectionStatusService connectionStatusService;

    @Override
    protected String[] getConfigFiles() {
        return new String[]{
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

    @Override
    protected Properties getStartUpProperties() {
        Properties props = new Properties();

        props.put("keepalive_to_dynac.interval_ms", "1000");
        props.put("keepalive_to_dynac.response_timeout_ms", "1000");

        return props;
    }

    @Before
    public void setup() throws Exception {
        dynacMock = getFunctionalTestComponent("dynac-mock");

        dynacMock.setReturnData(FileUtils.readFileToString(new File(
                "src/test/resources/messages/centerActiveVerificationResponseMsg.xml")));

        connectionStatusService =
                muleContext.getRegistry().get("connectionStatusService");
    }

    @Test
    public void shouldFailingConnection() throws Exception {
        givenDynacRespondsOnceOnKeepAlive();
        whenWaitingForTimeout();
        whenWaitingForTimeout();
        thenVerifyTheConnectionStateIs(ConnectionStatus.DISCONNECTED);
    }

    @Test
    public void shouldRestoreConnectionAfterFailure() throws Exception {
        givenDynacResponds();
        whenWaitingForTimeout();
        whenWaitingForTimeout();
        thenVerifyTheConnectionStateIs(ConnectionStatus.CONNECTED);
    }

    private void givenDynacResponds() throws IOException {
        final AtomicBoolean respond = new AtomicBoolean(false);

        dynacMock.setEventCallback(new EventCallback() {
            @Override
            public void eventReceived(MuleEventContext context,
                                      Object component) throws Exception {
                if (!respond.getAndSet(true)) {
                    Thread.sleep(100000);
                }
            }
        });
    }

    private void thenVerifyTheConnectionStateIs(ConnectionStatus state) {
        assertEquals(state, connectionStatusService.getLastState());
    }

    private void givenDynacRespondsOnceOnKeepAlive() {
        dynacMock.setEventCallback(new EventCallback() {
            @Override
            public void eventReceived(MuleEventContext arg0, Object arg1)
                    throws Exception {
                dynacMock.setWaitTime(1000000);
            }
        });
    }

    private void whenWaitingForTimeout() throws InterruptedException {
        Thread.sleep(2500);
    }
}
