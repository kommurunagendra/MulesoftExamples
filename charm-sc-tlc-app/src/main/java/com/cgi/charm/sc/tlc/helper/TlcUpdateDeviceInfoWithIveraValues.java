package com.cgi.charm.sc.tlc.helper;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.stereotype.Component;

import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.util.TlcUtil;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcUpdateDeviceInfoWithIveraValues {

	/**
	 * the TlcDeviceInfo
	 * 
	 * @param tlcDeviceInfo
	 *            DeviceInfo
	 * @param iveraObject
	 *            IveraObject
	 * @param payload
	 *            Payload
	 * @return Object returns tlcDevceInfo
	 * @throws DatatypeConfigurationException
	 *             throws Exception
	 * @throws ParseException
	 *             Exception
	 */
	public TlcDeviceInfo updateDeviceInfo(TlcDeviceInfo tlcDeviceInfo, String iveraObject, String payload)
			throws DatatypeConfigurationException, ParseException {

		switch (iveraObject) {
		case "VRIID/#1":
			tlcDeviceInfo.setIntersectionNumber(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRIID/#2":
			tlcDeviceInfo.setIntersectionDescription(splitPayload(payload));
			return tlcDeviceInfo;
		case "KTIJD":
			convertTimestamp(tlcDeviceInfo, payload);

		case "TID":
			tlcDeviceInfo.setIveraVersion(splitPayload(payload));
			return tlcDeviceInfo;
		case "YID":
			tlcDeviceInfo.setApplicationId(splitPayload(payload));
			return tlcDeviceInfo;
		case "DATACOM/#0":

			tlcDeviceInfo.setPhonenrCentral(splitPayload(payload));
			return tlcDeviceInfo;
		case "DATACOM/#1":
			tlcDeviceInfo.setIpnrCentral(splitPayload(payload));
			return tlcDeviceInfo;
		case "DATACOM/#3":
			tlcDeviceInfo.setTriggerevents(splitPayload(payload));
			return tlcDeviceInfo;
		}
		return tlcDeviceInfo;
	}

	private String splitPayload(String payload) {
		return payload.substring(payload.indexOf("=") + 1).trim();
	}

	/**
	 * the TlcDeviceInfo
	 * 
	 * @param tlcDeviceInfo
	 *            the tlcDeviceInfo
	 * @param payload
	 *            the payload
	 * @return deviceInfo
	 * @throws ParseException
	 */
	private TlcDeviceInfo convertTimestamp(TlcDeviceInfo tlcDeviceInfo, String payload) throws ParseException {

		String ktijdObjectValue = splitPayload(payload);
		ktijdObjectValue = ktijdObjectValue.replaceAll("\\s", "");
		ktijdObjectValue = ktijdObjectValue.substring(4, ktijdObjectValue.length());
		Timestamp xmlDate2 = TlcUtil.convertStringToTimeStamp(ktijdObjectValue);

		tlcDeviceInfo.setDateTime(xmlDate2);
		return tlcDeviceInfo;
	}

	/**
	 * updateDeviceInfoWithStatusAttr
	 * 
	 * @param tlcDeviceInfo
	 *            DeviceInfo
	 * @param iveraObject
	 *            IveraObject
	 * @param payload
	 *            Payload
	 * @return Object return TlcDeviceInfo
	 */
	public TlcDeviceInfo updateDeviceInfoWithStatusAttr(TlcDeviceInfo tlcDeviceInfo, String iveraObject,
			String payload) {

		switch (iveraObject) {
		case "VRISTAT/#0":
			tlcDeviceInfo.setCurrentStatusAkt(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#1":
			tlcDeviceInfo.setCurrentStatusBd(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#2":
			tlcDeviceInfo.setCurrentStatusCen(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#3":
			tlcDeviceInfo.setCurrentStatusAppl(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#4":
			tlcDeviceInfo.setCurrentStatusKlok(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#5":
			tlcDeviceInfo.setCurrentStatusProcess(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRISTAT/#6":
			tlcDeviceInfo.setCurrentStatusBewaker(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRIFSUB/#0":
			tlcDeviceInfo.setDetection(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRIFSUB/#1":
			tlcDeviceInfo.setLamps(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRIFSUB/#2":
			tlcDeviceInfo.setApplication(splitPayload(payload));
			return tlcDeviceInfo;
		case "VRIFSUB/#3":
			tlcDeviceInfo.setPublicTransportation(splitPayload(payload));
			return tlcDeviceInfo;
		case "BB0":
			tlcDeviceInfo.setBb0(splitPayload(payload));
			return tlcDeviceInfo;
		case "BB1":
			tlcDeviceInfo.setBb1(splitPayload(payload));
			return tlcDeviceInfo;
		}
		return tlcDeviceInfo;
	}
}
