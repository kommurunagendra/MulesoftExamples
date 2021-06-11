/*package com.cgi.charm.sc.tlc.persistence.test;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cgi.charm.sc.tlc.persistence.mapper.*;
import org.easymock.EasyMock;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.sc.tlc.mapper.TlcInfoReplyMapper;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAOImpl;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcSupportedObjectsMapper;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcLocationMapper;import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceSubscriptions;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandServiceImpl;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryServiceImpl;

public class BaseTlcPersistenceTest {

	static String TLC_JAXB_CONTEXT = "com.cgi.charm.cdm.tlc";


	TlcQueryServiceImpl tlcQueryServiceImpl=new TlcQueryServiceImpl();
	TlcPersistenceDAOImpl tlcPersistenceDAOImpl=new TlcPersistenceDAOImpl();
	TlcCommandServiceImpl tlcCommandServiceImpl=new TlcCommandServiceImpl();
	TlcGenericAttrMapper tlcGenericAttrMapper=new TlcGenericAttrMapper();
	TlcPositionWgs84Mapper tlcPositionWgs84Mapper = new TlcPositionWgs84Mapper();
	TlcInventoryAttrMapper tlcInventoryAttrMapper=new TlcInventoryAttrMapper();
	TlcCommunicationMapper tlcCommunicationMapper=new TlcCommunicationMapper();
	TlcLocationMapper tlcLocationMapper = new TlcLocationMapper();
	TlcStatusSubscriptionMapper tlcStatusSubscriptionMapper=new TlcStatusSubscriptionMapper();
	TlcSubscriptionAttrMapper tlcSubscriptionAttrMapper=new TlcSubscriptionAttrMapper();
	TlcSubscriptionRefMapper tlcSubscriptionRefMapper=new TlcSubscriptionRefMapper();
	TlcInventoryMapper tlcInventoryMapper=new TlcInventoryMapper();
	TlcStatusAttrMapper tlcStatusAttrMapper=new TlcStatusAttrMapper();
	TlcStatusInfoMapper tlcStatusInfoMapper=new TlcStatusInfoMapper();
	TlcErrorStatusMapper tlcErrorStatusMapper=new TlcErrorStatusMapper();
	TlcIveraStatusMapper tlcIveraStatusMapper=new TlcIveraStatusMapper();
	TlcSupportedObjectsMapper tlcSupportedObjectsMapper=new TlcSupportedObjectsMapper();
	TlcInfoReplyMapper tlcInfoReplyMapper=new TlcInfoReplyMapper();
	TlcSubscriptionConfigMapper tlcSubscriptionConfigMapper=new TlcSubscriptionConfigMapper();
	TlcSpecMapper tlcSpecMapper=new TlcSpecMapper();

	HibernateTemplate hibernateTemplate = EasyMock
			.createMock(HibernateTemplate.class);
      private TlcDeviceInfo mockTlcDeviceInfo(){
    	  TlcDeviceInfo tlcDeviceInfo=new TlcDeviceInfo();
    	  tlcDeviceInfo.setTlcId("id1");
    	  tlcDeviceInfo.setTlcType("TDI");
    	  tlcDeviceInfo.setIntersectionNumber("IntersectionNumber");
    	  tlcDeviceInfo.setIntersectionDescription("IntersectionDescription");
    	  tlcDeviceInfo.setDateTime(Timestamp.valueOf("2001-12-31 12:00:00"));
    	  tlcDeviceInfo.setIveraVersion("IveraVersion");
    	  tlcDeviceInfo.setControlTimeStamp(Timestamp.valueOf("2001-12-31 12:00:00"));
    	  tlcDeviceInfo.setApplicationId("ApplicationId");
    	  tlcDeviceInfo.setPhonenrCentral("PhonenrCentral");
    	  tlcDeviceInfo.setIpnrCentral("IpnrCentral");
    	  tlcDeviceInfo.setPortnumberServerAsb("9192");
    	  tlcDeviceInfo.setPortnumberClient("5000");
    	  tlcDeviceInfo.setTriggerevents("Triggerevents");
    	  tlcDeviceInfo.setIpnrVri("localhost");
    	  tlcDeviceInfo.setLatitude(0.0);
    	  tlcDeviceInfo.setLongitude(0.0);
    	  tlcDeviceInfo.setCurrentStatusAkt("currentStatusAkt");
    	  return tlcDeviceInfo;
      }

      public TlcSubscriptionInfo getSubscriptionInfoObj(){
    	  TlcSubscriptionInfo tlcSubscriptionInfo=new TlcSubscriptionInfo();
    	  tlcSubscriptionInfo.setAllTlcs(false);
    	  tlcSubscriptionInfo.setId(2);
    	  tlcSubscriptionInfo.setSubscriptionFrequency(20);
    	  tlcSubscriptionInfo.setSubscriptionName("subscriptionName");
    	  tlcSubscriptionInfo.setSubscriptionId("00000000-0000-0000-0000-000000000000");
    	  tlcSubscriptionInfo.setProperties(getPropertyInfoList());
    	  TlcDeviceSubscriptions tlcDeviceSubscriptions=new TlcDeviceSubscriptions();
    	  tlcDeviceSubscriptions.setId(2);
    	  tlcDeviceSubscriptions.setSubscriptionId("00000000-0000-0000-0000-000000000000");
    	  tlcDeviceSubscriptions.setTlcId("id1");
    	  List<TlcDeviceSubscriptions> tlcDeviceSubscriptionsList=new ArrayList<>();
    	  tlcDeviceSubscriptionsList.add(tlcDeviceSubscriptions);
    	  tlcSubscriptionInfo.setTlcDeviceSubscriptions(tlcDeviceSubscriptionsList);
		return tlcSubscriptionInfo;
      }

      private Set<TlcPropertyInfo> getPropertyInfoList(){
    	  Set<TlcPropertyInfo> tlcPropertyInfoSet=new HashSet<>();
    	  TlcPropertyInfo tlcPropertyInfo=new TlcPropertyInfo();
    	  tlcPropertyInfo.setKey("key");
    	  tlcPropertyInfo.setValue("value");
    	  tlcPropertyInfoSet.add(tlcPropertyInfo);
		return tlcPropertyInfoSet;
      }

      public List<TlcDeviceInfo> listOfMockTlcDeviceInfos(){
    	  List<TlcDeviceInfo> tlcDeviceInfoList=new ArrayList<>();
    	   tlcDeviceInfoList.add(mockTlcDeviceInfo());
    	   return tlcDeviceInfoList;
      }

      protected Object getUnmarshalledJAXBObject(String xmlFileName,
  			String context) throws JAXBException {
  		File file = new File("src/test/resources/messages/" + xmlFileName);
  		JAXBContext jaxbContext = JAXBContext.newInstance(context);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		return jaxbUnmarshaller.unmarshal(file);
  	}

    protected void getReflectionObjects(){
    	 TlcLocationMapper tlcLocationMapper = new TlcLocationMapper();
		 ReflectionTestUtils.setField(tlcLocationMapper, "tlcPositionWgs84Mapper", tlcPositionWgs84Mapper,TlcPositionWgs84Mapper.class);

		 TlcInventoryMapper tlcInventoryMapper = new TlcInventoryMapper();
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcLocationMapper", tlcLocationMapper,TlcLocationMapper.class);

		 TlcInventoryAttrMapper tlcInventoryAttrMapper=new TlcInventoryAttrMapper();
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcGenericAttrMapper", tlcGenericAttrMapper,TlcGenericAttrMapper.class);
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcCommunicationMapper", tlcCommunicationMapper,TlcCommunicationMapper.class);
		 ReflectionTestUtils.setField(tlcInventoryAttrMapper, "tlcLocationMapper", tlcLocationMapper,TlcLocationMapper.class);

    	 ReflectionTestUtils.setField(tlcQueryServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
		 ReflectionTestUtils.setField(tlcPersistenceDAOImpl, "hibernateTemplate", hibernateTemplate,HibernateTemplate.class);
		 ReflectionTestUtils.setField(tlcCommandServiceImpl, "tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,TlcPersistenceDAO.class);
      }

    protected List<String> getVriObjectList(){
    	List<String> vriObjectList=new ArrayList<>();
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
*/