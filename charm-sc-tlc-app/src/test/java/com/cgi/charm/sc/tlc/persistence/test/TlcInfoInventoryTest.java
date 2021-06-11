/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.SubscriptionSpec;
import com.cgi.charm.cdm.tlc.TlcInfoRequest;
import com.cgi.charm.cdm.tlc.UuidType;
import com.cgi.charm.sc.tlc.handler.TlcInfoRequestHandler;
import com.cgi.charm.sc.tlc.helper.TlcDeviceInfoList;
import com.cgi.charm.sc.tlc.mapper.TlcInfoReplyMapper;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcCommunicationMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcErrorStatusMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcGenericAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcIveraStatusMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusInfoMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSubscriptionConfigMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSubscriptionRefMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSupportedObjectsMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

public class TlcInfoInventoryTest extends BaseTlcPersistenceTest{
 
	 //@Test
	 public void infoReqInventoryTest() throws JAXBException{
		TlcInfoRequest tlcInfoRequest= (TlcInfoRequest) getUnmarshalledJAXBObject(
					"tlc_info_request_inventory.xml", TLC_JAXB_CONTEXT);
		 TlcInfoRequestHandler tlcInfoRequestHandler=new TlcInfoRequestHandler();
		 ReflectionTestUtils.setField(tlcInfoRequestHandler, "tlcInfoReplyMapper", tlcInfoReplyMapper,TlcInfoReplyMapper.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcCommunicationMapper", tlcCommunicationMapper,TlcCommunicationMapper.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcGenericAttrMapper", tlcGenericAttrMapper,TlcGenericAttrMapper.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcSubscriptionConfigMapper", tlcSubscriptionConfigMapper,TlcSubscriptionConfigMapper.class);
		 ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 ReflectionTestUtils.setField(tlcSubscriptionConfigMapper, "tlcSubscriptionRefMapper", tlcSubscriptionRefMapper,TlcSubscriptionRefMapper.class);
		 ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		 List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		 hibernateTemplate.find("from TlcDeviceInfo");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList).anyTimes();
		 hibernateTemplate.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
					"id1");
		 List<TlcSubscriptionInfo> list = new ArrayList<>();
		 list.add(getSubscriptionInfoObj());
		 EasyMock.expectLastCall().andReturn(list).anyTimes();
		 hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList.get(0));
		 hibernateTemplate.find("from TlcSubscriptionInfo");
		 EasyMock.expectLastCall().andReturn(list);
		 hibernateTemplate.get(TlcSubscriptionInfo.class, "00000000-0000-0000-0000-000000000000");
		 EasyMock.expectLastCall().andReturn(getSubscriptionInfoObj());
		 hibernateTemplate
			.find("from TlcDeviceInfo tdi ,TlcDeviceSubscriptions ts where ts.subscriptionId = ? AND ts.tlcId=tdi.tlcId",
					"00000000-0000-0000-0000-000000000000");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList);
		 EasyMock.replay(hibernateTemplate);
		 List<SubscriptionSpec> subscriptionSpecList=new ArrayList<>();
		 SubscriptionSpec subscriptionSpec = new SubscriptionSpec();
		 subscriptionSpec.setSubscriptionId(new UuidType("00000000-0000-0000-0000-000000000000"));
		 subscriptionSpecList.add(subscriptionSpec);
		 tlcInfoRequest.setSubscriptionSpec(subscriptionSpecList);
		 assertTrue(null!=tlcInfoRequestHandler.handle(tlcInfoRequest));
	 }
	 
	 
	 //@Test
	 public void infoReqStatusTest() throws JAXBException{
		 TlcInfoRequest tlcInfoRequest= (TlcInfoRequest) getUnmarshalledJAXBObject(
					"tlc_info_request_status.xml", TLC_JAXB_CONTEXT);
		 TlcInfoRequestHandler tlcInfoRequestHandler=new TlcInfoRequestHandler();
		 ReflectionTestUtils.setField(tlcInfoRequestHandler, "tlcInfoReplyMapper", tlcInfoReplyMapper,TlcInfoReplyMapper.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcStatusAttrMapper", tlcStatusAttrMapper,TlcStatusAttrMapper.class);
		 ReflectionTestUtils.setField(tlcStatusAttrMapper, "tlcStatusInfoMapper", tlcStatusInfoMapper,TlcStatusInfoMapper.class);
		 ReflectionTestUtils.setField(tlcStatusAttrMapper, "tlcErrorStatusMapper", tlcErrorStatusMapper,TlcErrorStatusMapper.class);
		 ReflectionTestUtils.setField(tlcStatusAttrMapper, "tlcIveraStatusMapper", tlcIveraStatusMapper,TlcIveraStatusMapper.class);
		 ReflectionTestUtils.setField(tlcIveraStatusMapper, "tlcSupportedObjectsMapper", tlcSupportedObjectsMapper,TlcSupportedObjectsMapper.class);
		 ReflectionTestUtils.setField(tlcInfoReplyMapper, "tlcSubscriptionConfigMapper", tlcSubscriptionConfigMapper,TlcSubscriptionConfigMapper.class);
		 ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 ReflectionTestUtils.setField(tlcSubscriptionConfigMapper, "tlcSubscriptionRefMapper", tlcSubscriptionRefMapper,TlcSubscriptionRefMapper.class);
		 ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		 List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		 hibernateTemplate.find("from TlcDeviceInfo");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList).anyTimes();
		 hibernateTemplate
			.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
					"id1");
		 List<TlcSubscriptionInfo> list = new ArrayList<>();
		 list.add(getSubscriptionInfoObj());
		 EasyMock.expectLastCall().andReturn(list).anyTimes();
		 hibernateTemplate.find("from TlcSubscriptionInfo");
		 EasyMock.expectLastCall().andReturn(list);
		 EasyMock.replay(hibernateTemplate);
		 assertTrue(null!=tlcInfoRequestHandler.handle(tlcInfoRequest));
	 }
	 
	 //@Test
	 public void getTlcDeviceInfoList() throws JAXBException{
		 TlcInfoRequest tlcInfoRequest= (TlcInfoRequest) getUnmarshalledJAXBObject(
				 "tlc_info_request_inventory.xml", TLC_JAXB_CONTEXT);
		 TlcDeviceInfoList tlcDeviceInfoList=new TlcDeviceInfoList();
		 ReflectionTestUtils.setField(tlcDeviceInfoList, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		 ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		 hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		 EasyMock.expectLastCall().andReturn(listOfMockTlcDeviceInfos().get(0));
		 List<TlcSubscriptionInfo> list = new ArrayList<>();
		 list.add(getSubscriptionInfoObj());
		 hibernateTemplate
			.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
					"id1");
		 EasyMock.expectLastCall().andReturn(list);
		 EasyMock.replay(hibernateTemplate);
		 assertTrue(null!=tlcDeviceInfoList.handle(tlcInfoRequest));
	 }
}
*/