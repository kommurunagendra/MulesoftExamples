package com.cgi.charm.sc.tlc.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transformer.TransformerException;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.NullPayload;
import org.mule.transport.tcp.TcpInputStream;

/**
 * Send transformed Mule events over TCP.
 */
public class TlcMessageDispatcher extends AbstractMessageDispatcher {

	/**
	 * log
	 */
	private static final Logger logger = Logger.getLogger(TlcMessageDispatcher.class);

	private static final Throwable e = null;

	private final TlcTcpConnector connector;

	/**
	 * the TlcMessageDispatcher
	 * 
	 * @param endpoint
	 *            is used to pass endpoint to the DripMessageDispatcher
	 */
	public TlcMessageDispatcher(OutboundEndpoint endpoint) {
		super(endpoint);
		this.connector = (TlcTcpConnector) endpoint.getConnector();
	}

	@Override
	protected synchronized void doDispatch(MuleEvent event) throws IOException, TransformerException, TlcException {
		Socket socket = connector.getSocket(endpoint);
		try {
			dispatchToSocket(socket, event);
		} finally {
			connector.releaseSocket(socket, endpoint);
		}
	}

	private void doDispatchToSocket(Socket socket, MuleEvent event)
			throws IOException, TransformerException, TlcException {
		try {
			dispatchToSocket(socket, event);
		} catch (Exception e) {
			// connector.releaseSocket(socket, endpoint);
			if (socket != null) {
				socket.close();
			}
		}
	}

	@Override
	protected synchronized MuleMessage doSend(MuleEvent event) throws IOException, MuleException, TlcException {
		Socket socket = connector.getSocket(endpoint);
		try {
			if (event.getMessage().getPayloadAsString().equals("remove")) {
				socket.close();
				connector.doDisconnect();
				return new DefaultMuleMessage(NullPayload.getInstance(), getEndpoint().getMuleContext());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		// REMARK why continue processing if an exception has occurs, why is the
		// expection in this case not blocking?
		doDispatchToSocket(socket, event);
		try {
			// REMARK using deprecated code is NOT accepted. Please refactor so
			// the deprecated code is no longer needed.
			if (returnResponse(event)) {
				return sendReceiveFromSocket(socket, event);
			} else {
				return new DefaultMuleMessage(NullPayload.getInstance(), getEndpoint().getMuleContext());
			}
		} finally {
			// REMARK using deprecated code is NOT accepted. Please refactor so
			// the deprecated code is no longer needed.
			// Furthermore, the same method is called twice, once in the try
			// block
			// and then in the finally block, so this will ALWAYS be exected
			// twice
			// with possibly undesired consequences.
			if (!returnResponse(event)) {
				connector.releaseSocket(socket, endpoint);
			}
		}

	}

	private MuleMessage sendReceiveFromSocket(Socket socket, MuleEvent event)
			throws IOException, MuleException, TlcException {
		try {
			Object result = receiveFromSocket(socket, event.getTimeout(), endpoint);
			if (result == null) {
				return new DefaultMuleMessage(NullPayload.getInstance(), getEndpoint().getMuleContext());
			}

			if (result instanceof MuleMessage) {
				return (MuleMessage) result;
			}

			return createMuleMessage(result, endpoint.getEncoding());

		} catch (SocketTimeoutException e) {
			// REMARK why don't you expect a response here?
			// And why is the exception then logged as an error? This will lead
			// to pollution of the log file since not all errors are erros.

			// we don't necessarily expect to receive a response here
			logger.error("Socket timed out normally while doing a synchronous receive on endpointUri: "
					+ endpoint.getEndpointURI(), e);
			return new DefaultMuleMessage(NullPayload.getInstance(), getEndpoint().getMuleContext());
		}
	}

	// Socket management (get and release) is handled outside this method
	private void dispatchToSocket(Socket socket, MuleEvent event) throws IOException, TransformerException {
		Object payload = event.getMessage().getPayload();
			write(socket, payload);
		}
		

	private void write(Socket socket, Object data) throws IOException, TransformerException {
		BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
		connector.getTcpProtocol().write(bos, data);
		bos.flush();
	}

	protected static Object receiveFromSocket(final Socket socket, int timeout, final ImmutableEndpoint endpoint)
			throws IOException, MuleException, TlcException {
		final TlcTcpConnector connector = (TlcTcpConnector) endpoint.getConnector();
		DataInputStream underlyingIs;
	if(socket!=null){
		underlyingIs = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		TcpInputStream tis = new TcpInputStream(underlyingIs) {
			// REMARK this construction will almost certainly lead to resource
			// leakage since the stream is not properly closed. Please include
			// a call to super.close() to esnure proper closing of the stream.
			@Override
			public void close() throws IOException {
				connector.releaseSocket(socket, endpoint);
			}

		};

		int soTimeout = endpoint.getResponseTimeout() != 0 ? endpoint.getResponseTimeout() : timeout;
		if (soTimeout >= 0) {
			socket.setSoTimeout(soTimeout);
		}

		try {
			return connector.getTcpProtocol().read(tis);
		} finally {
			if (!tis.isStreaming()) {
				tis.close();
			}
		}
	
	}
return null;
	}

	@Override
	protected synchronized void doDispose() {
		try {
			doDisconnect();
		} catch (Exception e) {
			logger.error("Failed to shutdown the dispatcher.", e);
		}
	}

}
