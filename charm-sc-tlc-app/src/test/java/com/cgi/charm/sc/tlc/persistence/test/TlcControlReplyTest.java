/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cgi.charm.cdm.tlc.UuidType;
import com.cgi.charm.sc.tlc.handler.TlcControlReplyHandler;
import com.cgi.charm.sc.tlc.handler.TlcControlReplyHandlerEmptyDatastore;
import com.cgi.charm.sc.tlc.helper.TlcDeviceInfoWrapper;

public class TlcControlReplyTest extends BaseTlcPersistenceTest{

	//@Test
	public void controlReplyTest(){
		TlcDeviceInfoWrapper tlcDeviceInfoWrapper=new TlcDeviceInfoWrapper();
		tlcDeviceInfoWrapper.setDeviceInfoList(listOfMockTlcDeviceInfos());
		tlcDeviceInfoWrapper.setMessageId(new UuidType("msgId"));
		TlcControlReplyHandler tlcControlReplyHandler=new TlcControlReplyHandler();
		tlcControlReplyHandler.setVersion("1.0.0");
		assertTrue(null!=tlcControlReplyHandler.handle(tlcDeviceInfoWrapper));
	}
	
	//@Test
	public void emptyControlReplyTest(){
		TlcControlReplyHandlerEmptyDatastore tlcControlReplyHandlerEmptyDatastore=new TlcControlReplyHandlerEmptyDatastore();
		tlcControlReplyHandlerEmptyDatastore.setVersion("1.0.0");
		assertTrue(null!=tlcControlReplyHandlerEmptyDatastore.handle(new UuidType("msgId")));
	}
}
*/