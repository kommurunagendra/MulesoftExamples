package com.cgi.charm.dynac.detector.multi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.ntcip.c2c_message_administration.C2CMessagePublication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmdd._3.messages.DetectorStatusCharm;
import org.tmdd._3.messages.DetectorStatusUpdate;
import org.tmdd._3.messages.ObjectFactory;

import com.cgi.charm.cdm.si.detector.DetectorSiStatusPublish;


/**
 * @author CHARM CGI TEAM
 */
@Component
public class CdmDetectorToStatusPublish {
	
	@Autowired
	private DetectorStatusPublishUtil detectorStatusPublishUtil;

	/**
	 * Handles the DetectorSiStatusPublish
	 * @param detectorStatusPublish of type DetectorSiStatusPublish
	 * @return jaxb
	 */
	public JAXBElement<DetectorStatusUpdate> handle(DetectorSiStatusPublish  detectorStatusPublish){
        ObjectFactory objectFactory=new ObjectFactory();
		DetectorStatusUpdate detectorStatusUpdate=new DetectorStatusUpdate();
		C2CMessagePublication c2cMessagePublication=new C2CMessagePublication();
		c2cMessagePublication.setSubscriptionID(detectorStatusPublish.getSubscriptionId().getUuid());
		detectorStatusUpdate.setC2CMsgAdmin(c2cMessagePublication);
		List<DetectorStatusCharm>  detectorStatusCharmList= new ArrayList<DetectorStatusCharm>();
		
		detectorStatusCharmList =  detectorStatusPublishUtil.populateStatusPublish(detectorStatusPublish.getDetectorPublish());
		detectorStatusUpdate.getDetectorStatusItem().addAll(detectorStatusCharmList);
		JAXBElement<DetectorStatusUpdate> jaxb=objectFactory.createDetectorStatusUpdateMsg(detectorStatusUpdate);
		return jaxb;
	}
}
