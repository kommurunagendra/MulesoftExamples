/**
 * 
 */
package com.cgi.charm.sc.tlc.tcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.source.CompositeMessageSource;
import org.mule.api.source.MessageSource;
import org.mule.api.transport.Connector;
import org.mule.construct.Flow;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.source.StartableCompositeMessageSource;
import org.mule.transport.vm.VMConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

/**
 * @author Naveen Chintala
 *
 */
@Component
public class PrepareDynamicTCPInboundEndpoint implements Callable {

	private static final String TCP_INBOUND_FLOW_NAME = "tlcTCPInboundFlow";
	private MuleContext muleContext;
	private MuleRegistry muleRegistry;

	private Map<String, MessageSource> registeredPorts = Collections
			.synchronizedMap(new HashMap<String, MessageSource>());
	private CompositeMessageSource messageSource;
	private Connector tcpInboundConn;
	private Flow tlcTCPInboundFlow;

	
	@Autowired
	private TlcQueryService tlcQueryServiceImpl;
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		setMuleContext(eventContext.getMuleContext());
		setMuleRegistry(eventContext.getMuleContext().getRegistry());
		removeFlowAndTCPInboundEndpoints(eventContext);
		createFlowAndAddTCPInboundEndpoints();
		return eventContext.getMessage().getPayload();
	}

	@SuppressWarnings("unchecked")
	private void removeFlowAndTCPInboundEndpoints(MuleEventContext eventContext)
			throws Exception {
		Set<TlcDeviceInfo> deviceInfoList = (Set<TlcDeviceInfo>) eventContext
				.getMessage().getPayload();
		for (TlcDeviceInfo deviceInfo : deviceInfoList) {
			InboundEndpoint tcpInboundEnpoint = (InboundEndpoint) registeredPorts
					.get(deviceInfo.getPortnumberClient());
			if (tcpInboundEnpoint != null) {
				tcpInboundEnpoint.getConnector().disconnect();
				messageSource.removeSource(tcpInboundEnpoint);
				registeredPorts.remove(deviceInfo.getPortnumberClient());
			}
		}
	}

	private void createFlowAndAddTCPInboundEndpoints() throws MuleException {
		if (null == messageSource) {
			messageSource = new StartableCompositeMessageSource();
		}
		if (null == tcpInboundConn) {
			tcpInboundConn = getMuleRegistry().lookupConnector(
					"tlcReceiverConnector");
		}
		if (registeredPorts.isEmpty()) {
			for (TlcDeviceInfo deviceInfo : getDeviceList()) {
				String portNumber = deviceInfo.getPortnumberClient();
				InboundEndpoint tcpInboundEnpoint = prepareAndRegisterTCPInboundEndpoint(portNumber);
				messageSource.addSource(tcpInboundEnpoint);
				registeredPorts.put(portNumber, tcpInboundEnpoint);
			}
		} else {
			for (TlcDeviceInfo deviceInfo : getDeviceList()) {
				String portNumber = deviceInfo.getPortnumberClient();
				if (!registeredPorts.containsKey(portNumber)) {
					InboundEndpoint tcpInboundEnpoint = prepareAndRegisterTCPInboundEndpoint(portNumber);
					// REMARK: unnecessary casting to CompositeMessageSource
					messageSource.addSource(tcpInboundEnpoint);
					registeredPorts.put(portNumber, tcpInboundEnpoint);
				}
			}
		}

		if (null == tlcTCPInboundFlow) {
			tlcTCPInboundFlow = new Flow(TCP_INBOUND_FLOW_NAME,
					getMuleContext());
			tlcTCPInboundFlow.setMessageSource(messageSource);
			tlcTCPInboundFlow.setMessageProcessors(Collections
					.singletonList(createAndRegisterVMOutboundEndpoint()));
			getMuleRegistry().registerObject(TCP_INBOUND_FLOW_NAME,
					tlcTCPInboundFlow);
		}
		
	}

	private InboundEndpoint prepareAndRegisterTCPInboundEndpoint(String port)
			throws MuleException {

		EndpointBuilder tcpInboundEndpointBuilder = new EndpointURIEndpointBuilder(
				"tcp://0.0.0.0:" + port, getMuleContext());
		tcpInboundEndpointBuilder
				.setExchangePattern(MessageExchangePattern.ONE_WAY);
		tcpInboundEndpointBuilder.setConnector(tcpInboundConn);
		tcpInboundEndpointBuilder.setResponseTimeout(10000);
		getMuleRegistry().registerEndpointBuilder("tcpInbound_" + port,
				tcpInboundEndpointBuilder);
		InboundEndpoint tcpInboundEndpoint = tcpInboundEndpointBuilder
				.buildInboundEndpoint();
		getMuleRegistry().registerEndpoint(tcpInboundEndpoint);
		return tcpInboundEndpoint;
	}

	private OutboundEndpoint createAndRegisterVMOutboundEndpoint()
			throws MuleException {

		VMConnector connector = (VMConnector) getMuleRegistry()
				.lookupConnector("tlcVmConnector");
		EndpointBuilder vmOutboundEndpointBuilder = new EndpointURIEndpointBuilder(
				"vm://" + "processInboundTCPRequests", getMuleContext());
		vmOutboundEndpointBuilder
				.setExchangePattern(MessageExchangePattern.ONE_WAY);
		vmOutboundEndpointBuilder.setConnector(connector);
		vmOutboundEndpointBuilder.setResponseTimeout(10000);
		getMuleRegistry().registerEndpointBuilder(
				"vmOutboundForTCPInboundRequests", vmOutboundEndpointBuilder);
		OutboundEndpoint vmOutboundEndpoint = vmOutboundEndpointBuilder
				.buildOutboundEndpoint();
		getMuleRegistry().registerEndpoint(vmOutboundEndpoint);
		return vmOutboundEndpoint;
	}

	private List<TlcDeviceInfo> getDeviceList() {
		List<TlcDeviceInfo> deviceInfos = new ArrayList<>();
		deviceInfos.addAll(tlcQueryServiceImpl.getAllTlcs());
		return deviceInfos;
	}

	/**
	 * @return the muleContext
	 */
	public MuleContext getMuleContext() {
		return muleContext;
	}

	/**
	 * @param muleContext
	 *            the muleContext to set
	 */
	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}

	/**
	 * @return the muleRegistry
	 */
	public MuleRegistry getMuleRegistry() {
		return muleRegistry;
	}

	/**
	 * @param muleRegistry
	 *            the muleRegistry to set
	 */
	public void setMuleRegistry(MuleRegistry muleRegistry) {
		this.muleRegistry = muleRegistry;
	}

}
