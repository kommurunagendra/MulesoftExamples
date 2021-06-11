package com.cgi.charm.sc.tlc.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cgi.charm.cdm.tlc.SubscriptionConfig;
import com.cgi.charm.cdm.tlc.SubscriptionType;
import com.cgi.charm.cdm.tlc.TlcStatusSubscription;
import com.cgi.charm.sc.tlc.persistence.mapper.TlcStatusSubscriptionMapper;
import com.cgi.charm.sc.tlc.persistence.model.TlcSubscriptionInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcCommandService;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;
import com.cgi.charm.util.handlers.Handler;

/**
 * @author venkatramana.derangu Handles Tlc Status Subscription
 */
public class TlcStatusSubscriptionInbundHandler implements
		Handler<TlcSubscriptionInfo, TlcStatusSubscription> {

	private static final Logger log = Logger
			.getLogger(TlcStatusSubscriptionInbundHandler.class);
	
	@Autowired
	private TlcQueryService tlcQueryServiceImpl;
	@Autowired
	private TlcCommandService tlcCommandServiceImpl;
	@Autowired
	private TlcStatusSubscriptionMapper tlcStatusSubscriptionMapper;

	/**
	 * Stores the TlcStatusSubcription Details Into The Subscription Datastore
	 * @param tlcStatusSubscription store the request into TlcSubscriptionInfo
	 * @return TlcSubscriptionInfo return subscriptionInfo
	 */
	@Override
	public TlcSubscriptionInfo handle(
			TlcStatusSubscription tlcStatusSubscription) {

		String subscriptionId = tlcStatusSubscription.getSubscriptionConfig()
				.getSubscriptionId().getUuid();

		SubscriptionConfig config = null;

		TlcSubscriptionInfo subscriptionInfo = tlcQueryServiceImpl
				.getSubscriptionById(subscriptionId);
		if (subscriptionInfo != null) {
			if (tlcStatusSubscription.getSubscriptionConfig().getTlcSpec()
					.isEmpty()
					&& !Boolean.TRUE.equals(tlcStatusSubscription
							.getSubscriptionConfig().getAllTlcs())) {
				tlcCommandServiceImpl.removeSubscriptionById(subscriptionId);
				log.debug("Subscription is removed from the database" + subscriptionId);
				subscriptionInfo.setAllTlcs(false);
			} else {
				config = tlcStatusSubscriptionMapper
						.mapFromPersistenceDataStore(subscriptionInfo);
				config = (SubscriptionConfig) tlcStatusSubscription
						.getSubscriptionConfig().copyTo(config);
				subscriptionInfo=tlcStatusSubscriptionMapper.toCdm(config);
				tlcCommandServiceImpl.addSubscription(subscriptionInfo);
				log.debug("Subscription updated" + subscriptionId);
			}
		} else {
			subscriptionInfo = tlcStatusSubscriptionMapper
					.toCdm(tlcStatusSubscription.getSubscriptionConfig());
			tlcCommandServiceImpl.addSubscription(subscriptionInfo);
			log.debug("Subscription added" + subscriptionId);
		}
		return subscriptionInfo;
	}
}
