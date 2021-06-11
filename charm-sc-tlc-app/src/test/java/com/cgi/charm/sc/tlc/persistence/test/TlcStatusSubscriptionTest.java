/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.TlcStatusSubscription;
import com.cgi.charm.sc.tlc.handler.TlcStatusSubscriptionInbundHandler;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusSubscriptionMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSubscriptionAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSubscriptionRefMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

//@RunWith(PowerMockRunner.class)
public class TlcStatusSubscriptionTest extends BaseTlcPersistenceTest {

	//@Test
	//@PrepareForTest({ TlcStatusSubscriptionMapper.class })
	public void getSubscriptionInfoTest() throws Exception {
		TlcStatusSubscription tlcStatusSubscription = (TlcStatusSubscription) getUnmarshalledJAXBObject(
				"tlc_status_subscription_all_tlcs.xml", TLC_JAXB_CONTEXT);
		TlcStatusSubscriptionInbundHandler tlcStatusSubscriptionInbundHandler = new TlcStatusSubscriptionInbundHandler();
		TlcSubscriptionInfo tlcSubscriptionInfo=new TlcSubscriptionInfo();
		PowerMock.expectNew(TlcSubscriptionInfo.class)
				.andReturn(tlcSubscriptionInfo).anyTimes();
		PowerMock.expectLastCall();
		PowerMock.replayAll();
		ReflectionTestUtils.setField(tlcStatusSubscriptionInbundHandler,
				"tlcQueryServiceImpl", tlcQueryServiceImpl,
				TlcQueryService.class);
		ReflectionTestUtils.setField(tlcStatusSubscriptionInbundHandler,
				"tlcStatusSubscriptionMapper", tlcStatusSubscriptionMapper,
				TlcStatusSubscriptionMapper.class);
		ReflectionTestUtils.setField(tlcStatusSubscriptionMapper,
				"tlcSubscriptionAttrMapper", tlcSubscriptionAttrMapper,
				TlcSubscriptionAttrMapper.class);
		ReflectionTestUtils.setField(tlcStatusSubscriptionMapper,
				"tlcSubscriptionRefMapper", tlcSubscriptionRefMapper,
				TlcSubscriptionRefMapper.class);
		ReflectionTestUtils.setField(tlcStatusSubscriptionInbundHandler,
				"tlcCommandServiceImpl", tlcCommandServiceImpl,
				TlcCommandService.class);
		getReflectionObjects();
		List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		hibernateTemplate.get(TlcSubscriptionInfo.class,
				"00000000-0000-0000-0000-000000000000");
		EasyMock.expectLastCall().andReturn(getSubscriptionInfoObj());
		hibernateTemplate
				.find("from TlcDeviceInfo tdi ,TlcDeviceSubscriptions ts where ts.subscriptionId = ? AND ts.tlcId=tdi.tlcId",
						"00000000-0000-0000-0000-000000000000");
		EasyMock.expectLastCall().andReturn(tlcDeviceInfoList);
		hibernateTemplate.saveOrUpdate(getSubscriptionInfoObj());
		EasyMock.expectLastCall();
		hibernateTemplate.saveOrUpdate(getSubscriptionInfoObj().getTlcDeviceSubscriptions().get(0));
		EasyMock.expectLastCall();
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=tlcStatusSubscriptionInbundHandler.handle(tlcStatusSubscription));
		EasyMock.verify();
	}

}
*/