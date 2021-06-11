/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.TlcControlRequest;
import com.cgi.charm.sc.tlc.handler.TlcControlRequestHandler;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

public class TlcControlRequestTest extends BaseTlcPersistenceTest{
	
	//@Test
	public void controlReqTest() throws JAXBException{
		TlcControlRequest tlcControlRequest=(TlcControlRequest) getUnmarshalledJAXBObject(
				"tlc_control_request.xml", TLC_JAXB_CONTEXT);
		TlcControlRequestHandler tlcControlRequestHandler=new TlcControlRequestHandler();
		ReflectionTestUtils.setField(tlcControlRequestHandler, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		ReflectionTestUtils.setField(tlcControlRequestHandler, "tlcCommandServiceImpl", tlcCommandServiceImpl,TlcCommandService.class);
		ReflectionTestUtils.setField(tlcCommandServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		EasyMock.expectLastCall().andReturn(tlcDeviceInfoList.get(0)).anyTimes();
		List<TlcSubscriptionInfo> list=new ArrayList<>();
		list.add(getSubscriptionInfoObj());
 		hibernateTemplate
		.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
				"id1");
		EasyMock.expectLastCall().andReturn(list).anyTimes();
		hibernateTemplate.saveOrUpdate(tlcDeviceInfoList.get(0));
		EasyMock.expectLastCall();
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=tlcControlRequestHandler.handle(tlcControlRequest));
	}
	
}
*/