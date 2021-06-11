package com.cgi.charm.dynac.drip.multi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.drip.DripImageAttr;
import com.cgi.charm.cdm.drip.DripInfoReply;
import com.cgi.charm.cdm.drip.DripPublish;
import com.cgi.charm.cdm.drip.DripStatusPublish;
import com.cgi.charm.dynac.multi.MultiMessages;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class MultiToCdmDripConverterService {
	private DmsMultiParser parser = new DmsMultiParser();

	/**
	 * Convert a Multi string to CDM object.
	 * 
	 * @param multi the multi string
	 * @return the parsed object
	 * @throws IllegalStateException if a parsing error occurs
	 */
	public DripImageAttr convertToCdm(String multi) {
		MultiToCdmDripConverter handler = new MultiToCdmDripConverter();

		parser.parse(multi, handler);

		return handler.getResult();
	}

	/**
	 * Convert from DripInfoReply to Multi format.
	 * 
	 * @param reply
	 *            the drip info reply
	 * @return an object containing all the messages from all of the drips
	 *         contained in the reply
	 */
	public MultiMessages convertToMulti(DripInfoReply reply) {
		return convertToMulti(reply.getDripPublish());
	}

	/**
	 * Convert from {@link DripStatusPublish} to {@link MultiMessages}
	 * 
	 * @param publish
	 *            the publish
	 * @return a MultiMessages containing all the message from the publish, one
	 *         for each DRIP.
	 */
	public MultiMessages convertToMulti(DripStatusPublish publish) {
		return convertToMulti(publish.getDripPublish());
	}

	private MultiMessages convertToMulti(List<DripPublish> publishes) {
		List<MultiMessages.MultiMessage> messages = new ArrayList<>();

		for (DripPublish publish : publishes) {
			messages.add(convertToMulti(publish));
		}

		return new MultiMessages(messages);
	}

	private MultiMessages.MultiMessage convertToMulti(DripPublish dripPublish) {
		String multi = convertToMulti(dripPublish.getDripImageAttr());

		return new MultiMessages.MultiMessage(dripPublish.getDripId().getId(), multi);
	}

	private String convertToMulti(DripImageAttr dripImageAttr) {
		CdmDripToMultiConverter converter = new CdmDripToMultiConverter();

		if(dripImageAttr!=null){
			converter.convert(dripImageAttr);
		}

		return converter.getResult();
	}
}
