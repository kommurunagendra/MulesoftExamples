package com.cgi.charm.dynac;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tmdd._3.messages.Authentication;
import org.tmdd._3.messages.CenterActiveVerificationRequest;
import org.tmdd._3.messages.CenterActiveVerificationResponse;
import org.tmdd._3.messages.ObjectFactory;
import org.tmdd._3.messages.OrganizationInformation;


/**
 * @author CHARM CGI TEAM
 */
@Component
public class CenterActiveVerificationRequestHandler {
	
	private String authUserId ;
	
	private String authUserPassword ;
	
	private String requestingOrganizationId ;
	
	private String userId ;
	
	private String password ;
	
	private String organizationId ;
	
	private String centerId;
	
	private String centerName;

	/**
	 * set center authentication user id
	 * 
	 * @param authUserId 
	 */	
	@Value("${authentication.user_id}")
	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}



	/**
	 * set center authentication password
	 * 
	 * @param authUserPassword 
	 */
	@Value("${authentication.password}")
	public void setAuthUserPassword(String authUserPassword) {
		this.authUserPassword = authUserPassword;
	}


	/**
	 * set Requesting Organization ID
	 * 
	 * @param requestingOrganizationId 
	 */
	@Value("${organization_requesting.organization_id}")
	public void setRequestingOrganizationId(String requestingOrganizationId) {
		this.requestingOrganizationId = requestingOrganizationId;
	}	

	/**
	 * set requesting user id
	 * 
	 * @param userId 
	 */
	@Value("${user_id}")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * set requesting user password
	 * 
	 * @param password 
	 */
	@Value("${password}")
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 *  
	 * @return CenterActiveVerificationRequest
	 */
	public JAXBElement<CenterActiveVerificationRequest> buildCenterActiveVerificationRequest(){
		
		CenterActiveVerificationRequest centerActiveVerificationRequest = new CenterActiveVerificationRequest();
		
		centerActiveVerificationRequest.setPassword(password);
		centerActiveVerificationRequest.setUserId(userId);
		
		Authentication authentication = new Authentication();
		authentication.setUserId(authUserId);
		authentication.setPassword(authUserPassword);
		centerActiveVerificationRequest.setAuthentication(authentication);
		
		OrganizationInformation organizationInformation = new OrganizationInformation();		
		organizationInformation.setOrganizationId(requestingOrganizationId);
		centerActiveVerificationRequest.setOrganizationRequesting(organizationInformation);
		
		ObjectFactory fact= new ObjectFactory();
		
		return fact.createCenterActiveVerificationRequestMsg(centerActiveVerificationRequest);	
		 
	}
	
	
	/**
	 *  
	 * @return CenterActiveVerificationResponse
	 */
	public JAXBElement<CenterActiveVerificationResponse> buildCenterActiveVerificationResponse(){
		
		CenterActiveVerificationResponse centerActiveVerificationResponse = new CenterActiveVerificationResponse();
		
		OrganizationInformation organizationInformation = new OrganizationInformation();		
		organizationInformation.setOrganizationId(this.organizationId);
		centerActiveVerificationResponse.setOrganizationInformation(organizationInformation);
		centerActiveVerificationResponse.setCenterId(this.centerId);
		centerActiveVerificationResponse.setCenterName(this.centerName);
		
		ObjectFactory fact= new ObjectFactory();
		
		return fact.createCenterActiveVerificationResponseMsg(centerActiveVerificationResponse);	
		 
	}

	
	/**
	 * verifies response elements such as CenterId, Organization id are matches with configured values
	 * @param  response of type CenterActiveVerificationResponse
	 * @return true/false
	 */
	public boolean verifyCenterActiveVerificationResponseFromDynac(CenterActiveVerificationResponse response){
		
		return (this.centerId.equalsIgnoreCase(response.getCenterId()) 
				&& this.organizationId.equalsIgnoreCase(response.getOrganizationInformation().getOrganizationId()));
			
	}


	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}


	/**
	 * @param organizationId the organizationId to set
	 */
	@Value("${organization_information.organization_id}")
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	/**
	 * @return the centerId
	 */
	public String getCenterId() {
		return centerId;
	}


	/**
	 * @param centerId the centerId to set
	 */
	@Value("${center_id}")
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}


	/**
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}



	/**
	 * @param centerName the centerName to set
	 */
	@Value("${center_name}")
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	

}
