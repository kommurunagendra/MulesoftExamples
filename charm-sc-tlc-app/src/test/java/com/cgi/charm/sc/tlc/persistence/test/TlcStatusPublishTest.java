/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.IdType;
import com.cgi.charm.cdm.tlc.SubscriptionTlc;
import com.cgi.charm.cdm.tlc.TlcSpec;
import com.cgi.charm.cdm.tlc.TlcStatusSubscription;
import com.cgi.charm.sc.tlc.handler.TlcMulStatusPublishHandler;
import com.cgi.charm.sc.tlc.handler.TlcStatusPublishHandler;
import com.cgi.charm.sc.tlc.helper.VriLbIveraObjectMapping;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcErrorStatusMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcIveraStatusMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSpecMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusAttrMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusInfoMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusSubscriptionMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSupportedObjectsMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

public class TlcStatusPublishTest extends BaseTlcPersistenceTest {

	//@Test
	public void statusPublishTest() {
		TlcStatusPublishHandler tlcStatusPublishHandler = new TlcStatusPublishHandler();
		tlcStatusPublishHandler.setVersion("1.0.0");
		ReflectionTestUtils.setField(tlcStatusPublishHandler,
				"tlcQueryServiceImpl", tlcQueryServiceImpl,
				TlcQueryService.class);
		ReflectionTestUtils.setField(tlcStatusPublishHandler,
				"tlcStatusAttrMapper", tlcStatusAttrMapper,
				TlcStatusAttrMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcStatusInfoMapper", tlcStatusInfoMapper,
				TlcStatusInfoMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcErrorStatusMapper", tlcErrorStatusMapper,
				TlcErrorStatusMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcIveraStatusMapper", tlcIveraStatusMapper,
				TlcIveraStatusMapper.class);
		ReflectionTestUtils.setField(tlcIveraStatusMapper,
				"tlcSupportedObjectsMapper", tlcSupportedObjectsMapper,
				TlcSupportedObjectsMapper.class);
		getReflectionObjects();
		List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		hibernateTemplate.find("from TlcDeviceInfo");
		EasyMock.expectLastCall().andReturn(tlcDeviceInfoList);
		hibernateTemplate
				.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
						"id1");
		List<TlcSubscriptionInfo> list = new ArrayList<>();
		list.add(getSubscriptionInfoObj());
		EasyMock.expectLastCall().andReturn(list).anyTimes();
		hibernateTemplate.get(TlcDeviceInfo.class, "id1");
		EasyMock.expectLastCall().andReturn(tlcDeviceInfoList.get(0));
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=tlcStatusPublishHandler.handle(getSubscriptionInfoObj()));
	}

	//@Test
	public void multipleStatusPublishTest() {
		TlcMulStatusPublishHandler tlcMulStatusPublishHandler = new TlcMulStatusPublishHandler();
		tlcMulStatusPublishHandler.setVersion("1.0.0");
		List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		ReflectionTestUtils.setField(tlcMulStatusPublishHandler,
				"tlcQueryServiceImpl", tlcQueryServiceImpl,
				TlcQueryService.class);
		ReflectionTestUtils.setField(tlcQueryServiceImpl,
				"tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,
				TlcPersistenceDAO.class);
		ReflectionTestUtils
				.setField(tlcPersistenceDAOImpl, "hibernateTemplate",
						hibernateTemplate, HibernateTemplate.class);
		ReflectionTestUtils.setField(tlcMulStatusPublishHandler,
				"tlcStatusAttrMapper", tlcStatusAttrMapper,
				TlcStatusAttrMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcStatusInfoMapper", tlcStatusInfoMapper,
				TlcStatusInfoMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcErrorStatusMapper", tlcErrorStatusMapper,
				TlcErrorStatusMapper.class);
		ReflectionTestUtils.setField(tlcStatusAttrMapper,
				"tlcIveraStatusMapper", tlcIveraStatusMapper,
				TlcIveraStatusMapper.class);
		ReflectionTestUtils.setField(tlcIveraStatusMapper,
				"tlcSupportedObjectsMapper", tlcSupportedObjectsMapper,
				TlcSupportedObjectsMapper.class);
		TlcSpec tlcSpec = new TlcSpec();
		tlcSpec.setTlcId(new IdType("id1"));
		List<TlcSpec> tlcSpecList = new ArrayList<>();
		tlcSpecList.add(tlcSpec);
		TlcSubscriptionInfo subcriptionInfo = getSubscriptionInfoObj();
		subcriptionInfo.setTlcSpec(tlcSpecList);
		List<TlcSubscriptionInfo> list = new ArrayList<>();
		list.add(subcriptionInfo);
		hibernateTemplate.find("from TlcSubscriptionInfo");
		EasyMock.expectLastCall().andReturn(list);
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=tlcMulStatusPublishHandler.handle(tlcDeviceInfoList.get(0)));
	}
	
	
	
	@Test
	public void tlcSpecMapperToCdmTest(){
		TlcSpecMapper tlcSpecMapper=new TlcSpecMapper();
		List<TlcDeviceInfo> tlcDeviceInfoList = listOfMockTlcDeviceInfos();
		assertTrue(null!=tlcSpecMapper.toCdm(tlcDeviceInfoList));
	}
	
	
	//@Test
	public void vriLbIveraObjectTest(){
		VriLbIveraObjectMapping vriLbIveraObjectMapping=new VriLbIveraObjectMapping();
		List<String> vriObjectList = getVriObjectList();
		for(String vriObject:vriObjectList){
			assertTrue(null!=vriLbIveraObjectMapping.map(vriObject));
		}
	}
	
	//@Test
	public void tlcStatuSubscriptionMapperTest() throws JAXBException{
		TlcStatusSubscription tlcStatusSubscription = (TlcStatusSubscription) getUnmarshalledJAXBObject(
				"tlc_status_subscription_all_tlcs.xml", TLC_JAXB_CONTEXT);
	TlcStatusSubscriptionMapper tlcStatusSubscriptionMapper=new TlcStatusSubscriptionMapper();
	ReflectionTestUtils.setField(tlcStatusSubscriptionMapper, "tlcSpecMapper", tlcSpecMapper,TlcSpecMapper.class);
	SubscriptionTlc subscriptionTlc = tlcStatusSubscription.getSubscriptionTlc();
	subscriptionTlc.setAllTlcs(false);
	TlcSpec tlcSpec=new TlcSpec();
	tlcSpec.setTlcId(new IdType("id1"));
	List<TlcSpec> tlcSpecList=new ArrayList<>();
	tlcSpecList.add(tlcSpec);
	subscriptionTlc.setTlcSpec(tlcSpecList);
	assertTrue(null!=tlcStatusSubscriptionMapper.toCdm(subscriptionTlc));
	}
	
	
}
*/