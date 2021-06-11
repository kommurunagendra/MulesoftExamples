package com.cgi.charm.dynac;

import java.io.IOException;
import java.io.InputStream;

import org.mule.module.xml.util.LocalResourceResolverInput;
import org.mule.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Workaround for a bug in the XML Schema validator that comes with Mule.
 * Without this it cannot include schema's from the classpath.
 */

/**
 * @author CHARM CGI TEAM
 */
public class ClasspathResourceResolver implements LSResourceResolver {
	private static final Logger logger = LoggerFactory.getLogger(ClasspathResourceResolver.class);
	
	@Override
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		
		System.setProperty("jdk.xml.maxOccurLimit", "100000000");

		try {
			InputStream resource = IOUtils.getResourceAsStream(systemId, getClass());

			LocalResourceResolverInput localResourceResolverInput = new LocalResourceResolverInput();
			localResourceResolverInput.setPublicId(publicId);
			localResourceResolverInput.setSystemId(systemId);
			localResourceResolverInput.setBaseURI(baseURI);			
			localResourceResolverInput.setByteStream(resource);
			localResourceResolverInput.setSystemId(systemId);
			
			return localResourceResolverInput;
			
		} catch (IOException e) {
			logger.error("cannot resolve resource", e);
			return null;
		}
	}
}