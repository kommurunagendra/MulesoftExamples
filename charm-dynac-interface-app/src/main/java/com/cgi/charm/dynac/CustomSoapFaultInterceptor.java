package com.cgi.charm.dynac;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * This custom soap fault interceptor
 * At present , cxf compoent in Mule returning component exception messages as fault
 *  when any error thrown in mule components and not allowig customize error/fault message. 
 * 
 * this cxf fault interceptor to retrun custom error message as fault.
 */
/**
 * @author CHARM CGI TEAM
 */
public class CustomSoapFaultInterceptor extends AbstractSoapInterceptor {

	/**
	  * constructor
	  * 
	  */
	public CustomSoapFaultInterceptor() {
		super(Phase.MARSHAL);
	}

	/**
	 * handle soap message to add custom fault message
	 * 
	 * @see
	 * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
	 * .Message)
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		//MSGErrorReport report = new MSGErrorReport();
		//message.setContent(Exception.class, report);
		
		Fault fault =  (Fault) message.getContent(Exception.class);
		fault.setMessage("Internal error occurred while processing the request.");

	}

}