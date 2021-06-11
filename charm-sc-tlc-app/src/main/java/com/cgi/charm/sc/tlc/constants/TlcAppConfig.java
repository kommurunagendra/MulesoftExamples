package com.cgi.charm.sc.tlc.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Uuid;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcAppConfig {

	private Uuid subscriptionId;

	 private Uuid otmcServicesstatusSubscriptionId;
	    private String otmcServicesStatusSubscriptionName;
	    private String otmcServicesStatusPropertySource;
	    private String otmcServicesStatusPropertyDestination;

	    private Uuid otmcInboundstatusSubscriptionId;
	    private String otmcInboundstatusSubscriptionName;
	    private String otmcInboundstatusPropertySource;
	    private String otmcInboundstatusPropertyDestination;
	    
	    private String cdmVersion;

		private String otmcOutboundStatusPropertySource;
	    
	    
	public Uuid getOtmcServicesstatusSubscriptionId() {
			return otmcServicesstatusSubscriptionId;
		}

	@Value("${otmc.services.status.subscription.id}")
		public void setOtmcServicesstatusSubscriptionId(Uuid otmcServicesstatusSubscriptionId) {
			this.otmcServicesstatusSubscriptionId = otmcServicesstatusSubscriptionId;
		}

		public String getOtmcServicesStatusSubscriptionName() {
			return otmcServicesStatusSubscriptionName;
		}

		@Value("${otmc.services.status.subscription.name}")
		public void setOtmcServicesStatusSubscriptionName(String otmcServicesStatusSubscriptionName) {
			this.otmcServicesStatusSubscriptionName = otmcServicesStatusSubscriptionName;
		}

		public String getOtmcServicesStatusPropertySource() {
			return otmcServicesStatusPropertySource;
		}

		@Value("${otmc.services.status.subscription.source}")
		public void setOtmcServicesStatusPropertySource(String otmcServicesStatusPropertySource) {
			this.otmcServicesStatusPropertySource = otmcServicesStatusPropertySource;
		}

		public String getOtmcServicesStatusPropertyDestination() {
			return otmcServicesStatusPropertyDestination;
		}

		@Value("${otmc.services.status.subscription.destination}")
		public void setOtmcServicesStatusPropertyDestination(String otmcServicesStatusPropertyDestination) {
			this.otmcServicesStatusPropertyDestination = otmcServicesStatusPropertyDestination;
		}

		public Uuid getOtmcInboundstatusSubscriptionId() {
			return otmcInboundstatusSubscriptionId;
		}

		@Value("${otmc.inbound.status.subscription.id}")
		public void setOtmcInboundstatusSubscriptionId(Uuid otmcInboundstatusSubscriptionId) {
			this.otmcInboundstatusSubscriptionId = otmcInboundstatusSubscriptionId;
		}

		public String getOtmcInboundstatusSubscriptionName() {
			return otmcInboundstatusSubscriptionName;
		}

		@Value("${otmc.inbound.status.subscription.name}")
		public void setOtmcInboundstatusSubscriptionName(String otmcInboundstatusSubscriptionName) {
			this.otmcInboundstatusSubscriptionName = otmcInboundstatusSubscriptionName;
		}

		public String getOtmcInboundstatusPropertySource() {
			return otmcInboundstatusPropertySource;
		}

		@Value("${otmc.inbound.status.subscription.source}")
		public void setOtmcInboundstatusPropertySource(String otmcInboundstatusPropertySource) {
			this.otmcInboundstatusPropertySource = otmcInboundstatusPropertySource;
		}

		public String getOtmcInboundstatusPropertyDestination() {
			return otmcInboundstatusPropertyDestination;
		}

		@Value("${otmc.inbound.status.subscription.destination}")
		public void setOtmcInboundstatusPropertyDestination(String otmcInboundstatusPropertyDestination) {
			this.otmcInboundstatusPropertyDestination = otmcInboundstatusPropertyDestination;
		}

		public String getCdmVersion() {
			return cdmVersion;
		}

		@Value("${cdm.version}")
		public void setCdmVersion(String cdmVersion) {
			this.cdmVersion = cdmVersion;
		}

	public Uuid getSubscriptionId() {
		return subscriptionId;
	}

	@Value("${tlc.subscriptionId}")
	public void setSubscriptionId(Uuid subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getOtmcOutboundStatusPropertySource() {
		return otmcOutboundStatusPropertySource;
	}

	@Value("${otmc.outbound.status.subscription.source}")
	public void setOtmcOutboundStatusPropertySource(String otmcOutboundStatusPropertySource) {
		this.otmcOutboundStatusPropertySource = otmcOutboundStatusPropertySource;
	}
}
