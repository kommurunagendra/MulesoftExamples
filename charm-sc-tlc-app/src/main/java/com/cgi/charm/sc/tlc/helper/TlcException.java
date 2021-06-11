package com.cgi.charm.sc.tlc.helper;

/**
 * 
 * @author chetan.singhal
 *
 */
public class TlcException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233615467573671110L;

	/**
	 * Drip Exception Parameterized Constructor
	 * 
	 * @param message
	 *            it used to store the drip exception message
	 * @param e
	 *            is used to store exception type
	 */
	public TlcException(String message, Throwable e) {
		super(message, e);
	}

	/** the TlcException
	 * @param message accepts message
	 */
	public TlcException(String message) {
		super(message);
	}

}
