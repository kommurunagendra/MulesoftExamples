package com.cgi.charm.dynac;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

/**
 * @author CHARM CGI TEAM
 */
public class DynacInterfaceJAXBUtil {
	private static final JAXBContext JAXB_CONTEXT;

	static {
		try {
			JAXB_CONTEXT = JAXBContext.newInstance("org.tmdd._3.messages:org.ntcip.c2c_message_administration:org.ntcip.c2f_object_references");
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String filename) throws JAXBException {
		Object object = JAXB_CONTEXT.createUnmarshaller().unmarshal(new File("src/test/resources/" + filename));
		
		if (object instanceof JAXBElement) {
			JAXBElement<T> result = (JAXBElement<T>) object;
			return result.getValue();
		} else {
			return (T)object;
		}

	}

	public static <T> String marshal(String ns, String elemName, T object) throws JAXBException {
		StringWriter writer = new StringWriter();

		@SuppressWarnings("unchecked")
		JAXBElement<T> elem = new JAXBElement<T>(new QName(ns, elemName), (Class<T>) object.getClass(), object);

		JAXB_CONTEXT.createMarshaller().marshal(elem, writer);

		return writer.toString();
	}
}
