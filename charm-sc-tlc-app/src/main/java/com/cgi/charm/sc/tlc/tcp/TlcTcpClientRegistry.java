package com.cgi.charm.sc.tlc.tcp;

/**
 * Registry for holding the port as thread local.
 * 
 * @author Naveen Chintala
 *
 */
public class TlcTcpClientRegistry {

	private final static TlcTcpClientRegistry INSTANCE = new TlcTcpClientRegistry();

	private ThreadLocal<String> ports = new ThreadLocal<>();

	public static TlcTcpClientRegistry getInstance() {
		return INSTANCE;
	}

	public void setCurrentPort(String port) {
		ports.set(port);
	}

	public String getCurrentPort() {
		return ports.get();
	}

}
