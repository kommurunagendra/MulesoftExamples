package com.cgi.charm.sc.tlc.helper;

import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.transport.tcp.TcpMessageDispatcherFactory;
import org.springframework.stereotype.Component;

/**
 * Custom drip dispatcher factory
 * 
 * @author chetan.singhal
 *
 */
@Component
public class TlcTcpDispatcherFactory extends TcpMessageDispatcherFactory {

	/**
	 * Create
	 * 
	 * @return MessageDispatcher
	 * @param endpoint
	 *            is a OutboundEndpoint
	 * @throws MuleException
	 *             throws the mule exception while dispatching the message
	 */
	@Override
	public MessageDispatcher create(OutboundEndpoint endpoint)
			throws MuleException {

		return new TlcMessageDispatcher(endpoint);
	}

}
