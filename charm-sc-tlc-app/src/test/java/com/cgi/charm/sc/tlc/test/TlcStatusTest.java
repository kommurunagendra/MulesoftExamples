package com.cgi.charm.sc.tlc.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mule.DefaultMuleMessage;

import com.cgi.charm.sc.tlc.helper.VriLbIveraObjectMapping;

public class TlcStatusTest extends BaseTlcTest {

	@Test
	public void shouldTestTlcStatus() throws Exception {
		whenTlcInventoryPublishIsReceived();
		tlcStatusSubscription();
		Thread.sleep(1000);
		//tlcStatusPublish();
		tlcStatusSubscriptionFalse();
	}

	@Test
	public void shouldTestTlcStatusAllTlcs() throws Exception {
		whenTlcInventoryPublishIsReceived();
		tlcStatusSubscriptionAllTlcs();
		Thread.sleep(1000);
		//tlcStatusPublish();
		tlcStatusSubscriptionAllTlcs();
		// need device status data
	}

	

	@Test
	public void shouldTestTriggerEvent1() throws Exception {
		whenTlcInventoryPublishIsReceived();
		tlcStatusSubscriptionAllTlcs();
		client.send("vm://processInboundRequests", new DefaultMuleMessage("5000_@1#:T=200132", muleContext));
	}

	@Test
	public void vriLbIveraObjectTest() {
		VriLbIveraObjectMapping vriLbIveraObjectMapping = new VriLbIveraObjectMapping();
		List<String> vriObjectList = getVriObjectList();
		for (String vriObject : vriObjectList) {
			assertTrue(null != vriLbIveraObjectMapping.map(vriObject));
		}
	}

	private List<String> getVriObjectList() {
		List<String> vriObjectList = new ArrayList<>();
		vriObjectList.add("19970117:150023,0,1020,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,1010,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,1030,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2000,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2001,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2002,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2003,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2004,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2005,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2500,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2501,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2502,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2503,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2504,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2505,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2506,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2510,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2511,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2512,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2513,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2600,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2601,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2700,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2701,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,2702,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3000,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3001,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3002,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3003,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3004,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3005,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3006,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3007,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3008,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3009,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,3010,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4000,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4001,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4002,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4003,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4004,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4005,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4006,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4007,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4008,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4009,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4010,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4011,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4012,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4013,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4014,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4015,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4016,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4022,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,4023,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6000,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6001,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6002,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6003,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6004,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6005,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,6006,SG02,R,1,1,2,0");
		vriObjectList.add("19970117:150023,0,200000,SG02,R,1,1,2,0");
		return vriObjectList;
	}
}
