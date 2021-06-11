package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pavan.chenna
 *
 */
public class TriggerEventDetails {
	
	private String eventCode;
	private String description;
	private List<String> details;
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getDetails() {
		if(null == details){
			details = new ArrayList<>();
		}
		return details;
	}
	/*public void setDetails(List<String> details) {
		this.details = details;
	}*/

	//REMARK: why are there no equals, hashcode and tostring methods in this class?
}
