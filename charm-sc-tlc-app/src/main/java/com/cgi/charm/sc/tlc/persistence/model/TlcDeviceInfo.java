package com.cgi.charm.sc.tlc.persistence.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.DeployedBy;
import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.TlcType;
import com.cgi.charm.common.persistence.InfoImpl;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcDeviceInfo extends InfoImpl {

	private static final long serialVersionUID = -3005585093993177625L;
	// Inventory fields
	private String tlcId;

	// Inventory Attr fileds
	private String name;
	private String description;
	private boolean scanEnabled;
	private int scanInterval;

	// Generic Attr fields
	private String tlcType;
	private String intersectionNumber;
	private String intersectionDescription;
	private Timestamp dateTime;
	private String iveraVersion;
	private String applicationId;
	// Communication fields
	private String phonenrCentral;
	private String ipnrCentral;
	private String portnr;
	private String triggerevents;
	private String ipnrVri;
	private String pinCode;
	
	private String owner;
	
	// Location fields
	private String positionBps;
	private double latitude;
	private double longitude;

	// Status fields
	// Status Info fields
	private String currentStatusAkt;
	private String currentStatusBd;
	private String currentStatusCen;
	private String currentStatusAppl;
	private String currentStatusKlok;
	private String currentStatusProcess;
	private String currentStatusBewaker;
	// Error status fields
	private String detection;
	private String lamps;
	private String application;
	private String publicTransportation;
	// Ivera status fields
	// Supported objects fields
	private String bb0;
	private String bb1;
	private List<IveraObject> iveraObject;
	private Set<TlcSubscriptionInfo> tlcSubscriptionInfo;
	// control fields
	private Timestamp controlTimeStamp;

	private String portnumberClient;
	private int sequenceNumber;
	private boolean login;
	private boolean processed;
	private String portnumberServerAsb;
	
	//Otmc device status
		private String availability;
		private String deviceState;
		private List<DeployedBy> deployedByList;
		private String availabilityExplanation;
		private String stateSourceDescription;
		private String stateExplanation;
		private byte[] encodedParameters;
	
	private boolean deviceDown;
	private String commState;

	
	public String getCommState() {
		return commState;
	}

	public void setCommState(String commState) {
		this.commState = commState;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public List<DeployedBy> getDeployedByList() {
		return deployedByList;
	}

	public void setDeployedByList(List<DeployedBy> deployedByList) {
		this.deployedByList = deployedByList;
	}

	public String getAvailabilityExplanation() {
		return availabilityExplanation;
	}

	public void setAvailabilityExplanation(String availabilityExplanation) {
		this.availabilityExplanation = availabilityExplanation;
	}

	public String getStateSourceDescription() {
		return stateSourceDescription;
	}

	public void setStateSourceDescription(String stateSourceDescription) {
		this.stateSourceDescription = stateSourceDescription;
	}

	public String getStateExplanation() {
		return stateExplanation;
	}

	public void setStateExplanation(String stateExplanation) {
		this.stateExplanation = stateExplanation;
	}

	public byte[] getEncodedParameters() {
		return encodedParameters;
	}

	public void setEncodedParameters(byte[] encodedParameters) {
		this.encodedParameters = encodedParameters;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the controlTimeStamp
	 */
	public Timestamp getControlTimeStamp() {
		return controlTimeStamp;
	}

	/**
	 * @param controlTimeStamp
	 *            the controlTimeStamp to set
	 */
	public void setControlTimeStamp(Timestamp controlTimeStamp) {
		this.controlTimeStamp = controlTimeStamp;
	}

	/**
	 * @return the tlcId
	 */
	public String getTlcId() {
		return tlcId;
	}

	/**
	 * @return the tlcSubscriptionInfo
	 */
	public Set<TlcSubscriptionInfo> getTlcSubscriptionInfo() {
		return tlcSubscriptionInfo;
	}

	/**
	 * @param tlcSubscriptionInfo
	 *            the tlcSubscriptionInfo to set
	 */
	public void setTlcSubscriptionInfo(Set<TlcSubscriptionInfo> tlcSubscriptionInfo) {
		this.tlcSubscriptionInfo = tlcSubscriptionInfo;
	}

	/**
	 * @param tlcId
	 *            the tlcId to set
	 */
	public void setTlcId(String tlcId) {
		this.tlcId = tlcId;
	}

	/**
	 * @return the tlcType
	 */
	public String getTlcType() {
		return tlcType;
	}

	/**
	 * @param tlcType
	 *            the tlcType to set
	 */
	public void setTlcType(String tlcType) {
		this.tlcType = tlcType;
	}

	/**
	 * @return the intersectionNumber
	 */
	public String getIntersectionNumber() {
		return intersectionNumber;
	}

	/**
	 * @param intersectionNumber
	 *            the intersectionNumber to set
	 */
	public void setIntersectionNumber(String intersectionNumber) {
		this.intersectionNumber = intersectionNumber;
	}

	/**
	 * @return the intersectionDescription
	 */
	public String getIntersectionDescription() {
		return intersectionDescription;
	}

	/**
	 * @param intersectionDescription
	 *            the intersectionDescription to set
	 */
	public void setIntersectionDescription(String intersectionDescription) {
		this.intersectionDescription = intersectionDescription;
	}

	/**
	 * @return the dateTime
	 */
	public Timestamp getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the iveraVersion
	 */
	public String getIveraVersion() {
		return iveraVersion;
	}

	/**
	 * @param iveraVersion
	 *            the iveraVersion to set
	 */
	public void setIveraVersion(String iveraVersion) {
		this.iveraVersion = iveraVersion;
	}

	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the phonenrCentral
	 */
	public String getPhonenrCentral() {
		return phonenrCentral;
	}

	/**
	 * @param phonenrCentral
	 *            the phonenrCentral to set
	 */
	public void setPhonenrCentral(String phonenrCentral) {
		this.phonenrCentral = phonenrCentral;
	}

	/**
	 * @return the ipnrCentral
	 */
	public String getIpnrCentral() {
		return ipnrCentral;
	}

	/**
	 * @param ipnrCentral
	 *            the ipnrCentral to set
	 */
	public void setIpnrCentral(String ipnrCentral) {
		this.ipnrCentral = ipnrCentral;
	}

	/**
	 * @return the portnr
	 */
	public String getPortnr() {
		return portnr;
	}

	/**
	 * @param portnr
	 *            the portnr to set
	 */
	public void setPortnr(String portnr) {
		this.portnr = portnr;
	}

	/**
	 * @return the triggerevents
	 */
	public String getTriggerevents() {
		return triggerevents;
	}

	/**
	 * @param triggerevents
	 *            the triggerevents to set
	 */
	public void setTriggerevents(String triggerevents) {
		this.triggerevents = triggerevents;
	}

	/**
	 * @return the ipnrVri
	 */
	public String getIpnrVri() {
		return ipnrVri;
	}

	/**
	 * @param ipnrVri
	 *            the ipnrVri to set
	 */
	public void setIpnrVri(String ipnrVri) {
		this.ipnrVri = ipnrVri;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the currentStatusAkt
	 */
	public String getCurrentStatusAkt() {
		return currentStatusAkt;
	}

	/**
	 * @param currentStatusAkt
	 *            the currentStatusAkt to set
	 */
	public void setCurrentStatusAkt(String currentStatusAkt) {
		this.currentStatusAkt = currentStatusAkt;
	}

	/**
	 * @return the currentStatusBd
	 */
	public String getCurrentStatusBd() {
		return currentStatusBd;
	}

	/**
	 * @param currentStatusBd
	 *            the currentStatusBd to set
	 */
	public void setCurrentStatusBd(String currentStatusBd) {
		this.currentStatusBd = currentStatusBd;
	}

	/**
	 * @return the currentStatusCen
	 */
	public String getCurrentStatusCen() {
		return currentStatusCen;
	}

	/**
	 * @param currentStatusCen
	 *            the currentStatusCen to set
	 */
	public void setCurrentStatusCen(String currentStatusCen) {
		this.currentStatusCen = currentStatusCen;
	}

	/**
	 * @return the currentStatusAppl
	 */
	public String getCurrentStatusAppl() {
		return currentStatusAppl;
	}

	/**
	 * @param currentStatusAppl
	 *            the currentStatusAppl to set
	 */
	public void setCurrentStatusAppl(String currentStatusAppl) {
		this.currentStatusAppl = currentStatusAppl;
	}

	/**
	 * @return the currentStatusKlok
	 */
	public String getCurrentStatusKlok() {
		return currentStatusKlok;
	}

	/**
	 * @param currentStatusKlok
	 *            the currentStatusKlok to set
	 */
	public void setCurrentStatusKlok(String currentStatusKlok) {
		this.currentStatusKlok = currentStatusKlok;
	}

	/**
	 * @return the currentStatusProcess
	 */
	public String getCurrentStatusProcess() {
		return currentStatusProcess;
	}

	/**
	 * @param currentStatusProcess
	 *            the currentStatusProcess to set
	 */
	public void setCurrentStatusProcess(String currentStatusProcess) {
		this.currentStatusProcess = currentStatusProcess;
	}

	/**
	 * @return the currentStatusBewaker
	 */
	public String getCurrentStatusBewaker() {
		return currentStatusBewaker;
	}

	/**
	 * @param currentStatusBewaker
	 *            the currentStatusBewaker to set
	 */
	public void setCurrentStatusBewaker(String currentStatusBewaker) {
		this.currentStatusBewaker = currentStatusBewaker;
	}

	/**
	 * @return the detection
	 */
	public String getDetection() {
		return detection;
	}

	/**
	 * @param detection
	 *            the detection to set
	 */
	public void setDetection(String detection) {
		this.detection = detection;
	}

	/**
	 * @return the lamps
	 */
	public String getLamps() {
		return lamps;
	}

	/**
	 * @param lamps
	 *            the lamps to set
	 */
	public void setLamps(String lamps) {
		this.lamps = lamps;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the publicTransportation
	 */
	public String getPublicTransportation() {
		return publicTransportation;
	}

	/**
	 * @param publicTransportation
	 *            the publicTransportation to set
	 */
	public void setPublicTransportation(String publicTransportation) {
		this.publicTransportation = publicTransportation;
	}

	/**
	 * @return the bb0
	 */
	public String getBb0() {
		return bb0;
	}

	/**
	 * @param bb0
	 *            the bb0 to set
	 */
	public void setBb0(String bb0) {
		this.bb0 = bb0;
	}

	/**
	 * @return the bb1
	 */
	public String getBb1() {
		return bb1;
	}

	/**
	 * @param bb1
	 *            the bb1 to set
	 */
	public void setBb1(String bb1) {
		this.bb1 = bb1;
	}

	/**
	 * @return the iveraObject
	 */
	public List<IveraObject> getIveraObject() {
		if (iveraObject == null) {
			iveraObject = new ArrayList<>();
		}
		return iveraObject;
	}

	/**
	 * @param iveraObject
	 *            the iveraObject to set
	 */
	public void setIveraObject(List<IveraObject> iveraObject) {
		this.iveraObject = iveraObject;
	}

	public String getPortnumberClient() {
		return portnumberClient;
	}

	public void setPortnumberClient(String portnumberClient) {
		this.portnumberClient = portnumberClient;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getPortnumberServerAsb() {
		return portnumberServerAsb;
	}

	public void setPortnumberServerAsb(String portnumberServerAsb) {
		this.portnumberServerAsb = portnumberServerAsb;
	}

	/**
	 * @return the positionBps
	 */
	public String getPositionBps() {
		return positionBps;
	}

	/**
	 * @param positionBps
	 *            the positionBps to set
	 */
	public void setPositionBps(String positionBps) {
		this.positionBps = positionBps;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the scanEnabled
	 */
	public boolean isScanEnabled() {
		return scanEnabled;
	}

	/**
	 * @param scanEnabled
	 *            the scanEnabled to set
	 */
	public void setScanEnabled(boolean scanEnabled) {
		this.scanEnabled = scanEnabled;
	}

	/**
	 * @return the scanInterval
	 */
	public int getScanInterval() {
		return scanInterval;
	}

	/**
	 * @param scanInterval
	 *            the scanInterval to set
	 */
	public void setScanInterval(int scanInterval) {
		this.scanInterval = scanInterval;
	}

	public boolean isDeviceDown() {
		return deviceDown;
	}

	/**
	 * 
	 * @param deviceDown if device is down
	 */
	public void setDeviceDown(boolean deviceDown) {
		this.deviceDown = deviceDown;
	}

}
