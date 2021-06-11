package com.cgi.charm.dynac;

import org.springframework.stereotype.Component;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class ConnectionStatusService {
	
	/**
	 * connection status constants
	 */
	public enum ConnectionStatus {	
		/**
		 * CONNECTED - constant for status connected
		 */
		CONNECTED,
		/**
		 * RECONNECTED - constant for status reconnected
		 */		
		RECONNECTED,
		/**
		 * DISCONNECTED - constant for status disconnected
		 */			
		DISCONNECTED,
		/**
		 * NEW_DISCONNECTED - constant for status new_disconnected
		 */	
		NEW_DISCONNECTED
	}
	
	private ConnectionStatus lastState = ConnectionStatus.DISCONNECTED;
	
	/**
	 * Indicate that a successful keepalive event has occurred.  
	 * 
	 * @return the new status of the connection
	 */
	public ConnectionStatus successfulKeepalive() {
		lastState = lastState == ConnectionStatus.NEW_DISCONNECTED || lastState == ConnectionStatus.DISCONNECTED ? 
				ConnectionStatus.RECONNECTED : 
					ConnectionStatus.CONNECTED;
		
		return lastState;
	}
	
	/**
	 * Indicate that a failed keepalive event has occurred.
	 * 
	 * @return the new status of the connection
	 */
	public ConnectionStatus failedKeepalive() {
		lastState = lastState == ConnectionStatus.CONNECTED ? ConnectionStatus.NEW_DISCONNECTED : ConnectionStatus.DISCONNECTED;
		
		return lastState;
	}
	
	/**
	 * The last known state of the connection
	 * 
	 * @return the latest state
	 */
	public ConnectionStatus getLastState() {
		return lastState;
	}
}
