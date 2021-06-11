/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.TlcInventoryPublish;
import com.cgi.charm.cdm.tlc.UuidType;
import com.cgi.charm.sc.tlc.constants.TlcAppConfig;
import com.cgi.charm.sc.tlc.handler.TlcInventoryPublishHandler;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcCommunicationMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcGenericAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcInventoryMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcLocationMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcPositionWgs84Mapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

public class TlcInventoryPublishTest extends BaseTlcPersistenceTest{
 
	
	TlcAppConfig tlcAppConfig=new TlcAppConfig();
	
	 //@Test
	 public void inventoryPublishFullSyncTest() throws JAXBException{
		 TlcInventoryPublishHandler inventoryPublishHandler=new TlcInventoryPublishHandler();
		 TlcInventoryPublish tlcInventoryPublish = (TlcInventoryPublish) getUnmarshalledJAXBObject("tlc_inventory_publish.xml", TLC_JAXB_CONTEXT);
		 tlcAppConfig.setSubscriptionId(new UuidType("00000000-0000-0000-0000-000000000000"));
		 
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcAppConfig", tlcAppConfig,TlcAppConfig.class);
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcCommandServiceImpl", tlcCommandServiceImpl,TlcCommandService.class);
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcInventoryMapper", tlcInventoryMapper,TlcInventoryMapper.class);
		 
		 ReflectionTestUtils.setField(tlcLocationMapper, "tlcPositionWgs84Mapper", tlcPositionWgs84Mapper,TlcPositionWgs84Mapper.class);
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcLocationMapper", tlcLocationMapper,TlcLocationMapper.class);
		 
		 ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		 ReflectionTestUtils.setField(tlcCommandServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcGenericAttrMapper", tlcGenericAttrMapper,TlcGenericAttrMapper.class);
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcCommunicationMapper", tlcCommunicationMapper,TlcCommunicationMapper.class);
		 ReflectionTestUtils.setField(tlcInventoryMapper, "tlcInventoryAttrMapper", tlcInventoryAttrMapper,TlcInventoryAttrMapper.class);

		 List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		 hibernateTemplate.find("from TlcDeviceInfo");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList).anyTimes();
		 hibernateTemplate.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId","id1");
		 EasyMock.expectLastCall().andReturn(new ArrayList<>()).anyTimes();
		 hibernateTemplate.deleteAll(tlcDeviceInfoList);
		 EasyMock.expectLastCall();
		 hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList.get(0));
		 hibernateTemplate.saveOrUpdate(tlcDeviceInfoList.get(0));
		 EasyMock.replay(hibernateTemplate);
		 assertTrue(null!=inventoryPublishHandler.handle(tlcInventoryPublish));
		 EasyMock.verify();
	 }
	 
	 //@Test
	 public void inventoryPublishRemoveTest() throws JAXBException{
		 TlcInventoryPublishHandler inventoryPublishHandler=new TlcInventoryPublishHandler();
		 TlcInventoryPublish tlcInventoryPublish = (TlcInventoryPublish) getUnmarshalledJAXBObject(
					"tlc_inventory_publish_remove.xml", TLC_JAXB_CONTEXT);
		 System.err.println(tlcInventoryPublish.getInventory().getTlcInventory().get(0).getTlcInventoryAttr());
		 tlcAppConfig.setSubscriptionId(new UuidType("00000000-0000-0000-0000-000000000000"));
		 
		 TlcInventoryMapper tlcInventoryMapper = new TlcInventoryMapper();
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcInventoryMapper", tlcInventoryMapper,TlcInventoryMapper.class);		
		 
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcAppConfig", tlcAppConfig,TlcAppConfig.class);
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcQueryServiceImpl", tlcQueryServiceImpl,TlcQueryService.class);
		 ReflectionTestUtils.setField(inventoryPublishHandler, "tlcCommandServiceImpl", tlcCommandServiceImpl,TlcCommandService.class);
		 getReflectionObjects();
		 List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		 hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		 EasyMock.expectLastCall().andReturn(tlcDeviceInfoList.get(0)).anyTimes();
		 hibernateTemplate
			.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
					"id1");
		 EasyMock.expectLastCall().andReturn(new ArrayList<>());
		 hibernateTemplate.delete(tlcDeviceInfoList.get(0));
		 EasyMock.expectLastCall();
		 EasyMock.replay(hibernateTemplate);
		 assertTrue(null!=inventoryPublishHandler.handle(tlcInventoryPublish));
		 EasyMock.verify();
	 }
 }
*/