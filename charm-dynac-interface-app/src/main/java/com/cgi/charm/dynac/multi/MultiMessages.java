package com.cgi.charm.dynac.multi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author CHARM CGI TEAM
 */
@XmlRootElement(name = "multi-messages")
public class MultiMessages {
	/**
	 * Result of a single transformation of a CDM message to a Multi message. 
	 * 
	 * @author bob
	 *
	 */
	public static class MultiMessage {
		@XmlElement(required = true)
		private String id;
		
		@XmlElement(required = true)
		private String multi;

		/**
		 * Empty constructor for JAXB
		 */
		public MultiMessage() {
			// EMPTY constructor for JAXB
		}

		/**
		 * Constructor 
		 * 
		 * @param id the index
		 * @param multi the Multi string 
		 */
		public MultiMessage(String id, String multi) {
			this.id = id;
			this.multi = multi;
		}

		public String getId() {
			return id;
		}

		public String getMulti() {
			return multi;
		}
	}

	@XmlElement(name = "multi-message")
	private List<MultiMessage> multiMessage;

	/**
	 * Empty Constructor
	 */
	public MultiMessages() {
	}

	/**
	 * Constructor
	 * 
	 * @param multiMessage List of MultiMessage objects.
	 */
	public MultiMessages(List<MultiMessage> multiMessage) {
		this.multiMessage = multiMessage;
	}

	public List<MultiMessage> getMultiMessage() {
		return multiMessage;
	}
}
