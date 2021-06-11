package com.cgi.charm.dynac.multi;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * @author CHARM CGI TEAM
 */
@XmlRegistry
public class ObjectFactory {
	/**
	 * @return Creates MultiMessages JAXB Object
	 */
	public MultiMessages createMultiMessages() {
		return new MultiMessages();
	}
}
