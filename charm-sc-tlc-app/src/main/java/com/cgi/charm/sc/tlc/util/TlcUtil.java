package com.cgi.charm.sc.tlc.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.cgi.charm.cdm.tlc.Meta;
import com.cgi.charm.cdm.tlc.Uuid;
import com.cgi.charm.cdm.tlc.ErrorStatus;
import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.IveraStatus;
import com.cgi.charm.cdm.tlc.StatusInfo;
import com.cgi.charm.cdm.tlc.SupportedObjects;
import com.cgi.charm.cdm.tlc.TlcStatusAttr;

/**
 * @author CHARM CGI TEAM
 */
public final class TlcUtil {
	/**
	 * convert the String to Timestamp
	 * 
	 * @param time
	 *            Time
	 * @return timestamp Timestamp
	 * @throws ParseException
	 *             Exception
	 * 
	 */
	public static Timestamp convertStringToTimeStamp(String time) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh:mm:ss");
		Date parsedDate = dateFormat.parse(time);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return timestamp;
	}
	
	public static Meta commonConfigMeta() {
		Meta meta = new Meta();
		meta.setMessageId(new Uuid(UUID.randomUUID().toString()));
		meta.setMessageCreateDate(Instant.now());
		return meta;
	}

	/**
	 * getPrimitiveBooleanValue
	 * 
	 * @param booleanWrapper
	 *            Boolean Wrapper
	 * @return boolean returns boolean value
	 */
	public static boolean getPrimitiveBooleanValue(Boolean booleanWrapper) {
		return (null == booleanWrapper ? false : booleanWrapper);
	}

	/**
	 * the convertTimeStampToGeorgianClaender
	 * 
	 * @param lastUpdate
	 *            LastUpdate
	 * @return XMLGregorianCalendar Calendar
	 * @throws DatatypeConfigurationException
	 *             Exception
	 */
	public static XMLGregorianCalendar convertTimeStampToGeorgianClaender(Timestamp lastUpdate)
			throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(lastUpdate);

		XMLGregorianCalendar xmlLastUpdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return xmlLastUpdate;
	}

	/**
	 * the Instant
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Instant
	 */
	public static Instant toInstant(Timestamp timestamp) {
		if (timestamp != null) {
			return timestamp.toInstant();
		}
		return null;
	}

	/*
	 * public static XMLGregorianCalendar getGregorianCalendarDate(String date)
	 * throws ParseException, DatatypeConfigurationException { Date dob = null;
	 * DateFormat df = new SimpleDateFormat("yyyyMMddhh:mm:ss"); dob =
	 * df.parse(date); GregorianCalendar cal = new GregorianCalendar();
	 * cal.setTime(dob); //REMARK First of all, why not use the Java 8 datetime
	 * api? // Second of all, do NOT use deprecated code, the methods in
	 * java.util.Date // have been deprecated since Java 1.1, we are now on Java
	 * 8... // Third of all, the call cal.getTimeZone().LONG will always return
	 * 1, since // TimeZone.LONG is a constant field set to 1, perhaps you meant
	 * to achieve // something else? XMLGregorianCalendar xmlDate2 =
	 * DatatypeFactory .newInstance()
	 * .newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)
	 * + 1, cal.get(Calendar.DAY_OF_MONTH), dob.getHours(), dob.getMinutes(),
	 * dob.getSeconds(), DatatypeConstants.FIELD_UNDEFINED,
	 * cal.getTimeZone().LONG).normalize();
	 * 
	 * return xmlDate2;
	 * 
	 * }
	 */
	public static TlcStatusAttr getDummyTlcStatusAttr() {
		TlcStatusAttr tlcStatusAttr = new TlcStatusAttr();
		tlcStatusAttr.setStatusInfo(new StatusInfo("VRISTAT#0", "VRISTAT#1", "VRISTAT#2", "VRISTAT#3", "VRISTAT#4",
				"VRISTAT#5", "VRISTAT#6"));
		tlcStatusAttr.setErrorStatus(new ErrorStatus("VRIFSUB#0", "VRIFSUB#1", "VRIFSUB#2", "VRIFSUB#3"));
		SupportedObjects supportedObjects = new SupportedObjects();
		supportedObjects.setBb0("BB0");
		supportedObjects.setBb1("BB1");
		List<IveraObject> iveraObjects = new ArrayList<>();
		IveraObject IveraObject = new IveraObject();
		IveraObject.setName("iveraName");
		IveraObject.setDisplayname("iveraDisplayname");
		iveraObjects.add(IveraObject);
		tlcStatusAttr.setIveraStatus(new IveraStatus(supportedObjects, iveraObjects));
		return tlcStatusAttr;

	}
}
