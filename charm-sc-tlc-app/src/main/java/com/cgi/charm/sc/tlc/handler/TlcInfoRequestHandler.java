package com.cgi.charm.sc.tlc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcInfoReply;
import com.cgi.charm.cdm.tlc.TlcInfoRequest;
import com.cgi.charm.sc.tlc.mapper.TlcInfoReplyMapper;
import com.cgi.charm.util.handlers.Handler;
/**
 * Handles TLC Info request.
 * 
 * @author CGI.CHARM.Team
 *
 */
@Component
public class TlcInfoRequestHandler implements
		Handler<TlcInfoReply, TlcInfoRequest> {

	@Autowired
	private TlcInfoReplyMapper tlcInfoReplyMapper;

	/**
	 * Pass the TlcInfoRequest Object to the Mapper Class
	 * @param tlcInfoRequest InfoRequest to prepare Inforeply
	 * @return tlcInfoReplyMapper 
	 */

	@Override
	public TlcInfoReply handle(TlcInfoRequest tlcInfoRequest) {
		return tlcInfoReplyMapper.map(tlcInfoRequest);
	}

}
