/*package com.cgi.charm.sc.tlc.persistence.test;

import com.cgi.charm.sc.tlc.persistence.mapper.*;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.IveraStatus;
import com.cgi.charm.cdm.tlc.PositionWgs84;
import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SupportedObjects;
import com.cgi.charm.cdm.tlc.TlcLocationAttr;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;

public class MapperTest {
	
   //@Test
   public void tlcLocationMapperTest(){
	   TlcLocationMapper tlcLocationMapper=new TlcLocationMapper();
	   TlcPositionWgs84Mapper tlcPositionWgs84Mapper = new TlcPositionWgs84Mapper();
	   
	   TlcLocationAttr tlcLocationAttr=new TlcLocationAttr();
	   
	   PositionWgs84 positionWgs84 = new PositionWgs84();
	   positionWgs84.setLatitude(1.9);
	   positionWgs84.setLongitude(6.9);
	   tlcLocationAttr.setPositionWgs84(positionWgs84);
	   tlcLocationAttr.setPositionBps("TLC");
	   
	   TlcDeviceInfo tlcDeviceInfo=new TlcDeviceInfo(); 
	   
	   ReflectionTestUtils.setField(tlcLocationMapper, "tlcPositionWgs84Mapper", tlcPositionWgs84Mapper,TlcPositionWgs84Mapper.class);
	  
	   tlcLocationMapper.toDomain(tlcLocationAttr);
	   tlcLocationMapper.toCdm(tlcDeviceInfo, tlcLocationAttr);
	   tlcLocationMapper.toDomain(tlcLocationAttr, tlcDeviceInfo);
   }
   
  // @Test
   public void tlcSubscriptionConfigMapperTest(){
	   TlcSubscriptionConfigMapper tlcSubscriptionMapper=new TlcSubscriptionConfigMapper();
	   SubscriptionConfig subscriptionConfig=new SubscriptionConfig();
	   TlcSubscriptionInfo tlcSubscriptionInfo=new TlcSubscriptionInfo();
	   tlcSubscriptionMapper.toDomain(subscriptionConfig);
	   tlcSubscriptionMapper.toDomain(subscriptionConfig, tlcSubscriptionInfo);
	   tlcSubscriptionMapper.toDomain(subscriptionConfig, tlcSubscriptionInfo);
   }
   
   
   //@Test
   public void tlcSupportedObjectMapperTest(){
	   TlcSupportedObjectsMapper tlcSupportedObjectsMapper=new TlcSupportedObjectsMapper();
	   SupportedObjects supportedObjects=new SupportedObjects();
	   TlcDeviceInfo tlcDeviceInfo=new TlcDeviceInfo();
	   tlcSupportedObjectsMapper.toDomain(supportedObjects);
	   tlcSupportedObjectsMapper.toDomain(supportedObjects, tlcDeviceInfo);
	   tlcSupportedObjectsMapper.toCdm(tlcDeviceInfo, supportedObjects);
   }
   
   //@Test
   public void tlcIveraStatusMapperTest(){
	   TlcIveraStatusMapper tlcIveraStatusMapper=new TlcIveraStatusMapper();
	   IveraStatus iveraStatus=new IveraStatus();
	   TlcDeviceInfo tlcDeviceInfo=new TlcDeviceInfo();
	   tlcIveraStatusMapper.toDomain(iveraStatus);
	   tlcIveraStatusMapper.toDomain(iveraStatus, tlcDeviceInfo);
	   tlcIveraStatusMapper.toCdm(tlcDeviceInfo, iveraStatus);
   }
   
}
*/