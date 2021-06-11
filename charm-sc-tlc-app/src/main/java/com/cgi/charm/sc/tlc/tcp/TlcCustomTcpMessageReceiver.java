package com.cgi.charm.sc.tlc.tcp;

import java.io.IOException;
import java.net.Socket;

import javax.resource.spi.work.Work;

import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractMessageReceiver;
import org.mule.transport.tcp.TcpMessageReceiver;

/**
 * @author Naveen Chintala
 *
 */
public class TlcCustomTcpMessageReceiver extends TcpMessageReceiver {

	/** the TlcCustomTcpMessageReceiver
	 * @param connector Connector
	 * @param flowConstruct FlowConstruct
	 * @param endpoint Endpoint
	 * @throws CreateException createException
	 */
	public TlcCustomTcpMessageReceiver(Connector connector,
			FlowConstruct flowConstruct, InboundEndpoint endpoint)
			throws CreateException {
		super(connector, flowConstruct, endpoint);
	}

	@Override
	protected Work createWork(Socket socket) throws IOException {
		return new TlcTcpWorker(socket, this);
	}

	private class TlcTcpWorker extends TcpWorker {
		private String port;

		public TlcTcpWorker(Socket socket, AbstractMessageReceiver receiver)
				throws IOException {
			super(socket, receiver);
			port = String.valueOf(socket.getLocalPort());

		}

		@Override
		public void doRun() {
			TlcTcpClientRegistry.getInstance().setCurrentPort(port);
			super.doRun();
		}
	}

}
