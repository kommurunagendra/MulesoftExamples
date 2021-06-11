package com.cgi.charm.dynac.bermdrip.multi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.tmdd._3.messages.DMSGraphic;

import com.cgi.charm.cdm.bermdrip.BermdripImageAttr;
import com.cgi.charm.cdm.bermdrip.BermdripInfoReply;
import com.cgi.charm.cdm.bermdrip.BermdripPublish;
import com.cgi.charm.cdm.bermdrip.BermdripStatusPublish;
import com.cgi.charm.dynac.multi.MultiMessages;

/**
 * @author CHARM CGI TEAM
 */
@Component
public class MultiToCdmBermdripConverterService {
	private DmsMultiParser parser = new DmsMultiParser();

	/**
	 * Convert a Multi string to Bermdrip CDM object.
	 * 
	 * @param multi the multi string
	 * @param dmsGraphics bitmap data
	 * @return the parsed object
	 * @throws IllegalStateException if a parsing error occurs
	 */
	public BermdripImageAttr convertToCdm(String multi,List<DMSGraphic> dmsGraphics) {
		MultiToCdmBermdripConverter handler = new MultiToCdmBermdripConverter();
		parser.setDmsGraphics(dmsGraphics);
		
		parser.parse(multi, handler);

		return handler.getResult();
	}
	
	
	
	/**
	 * Convert from BermdripInfoReply to Multi format.
	 * 
	 * @param reply BermdripInfoReply objet          
	 * @return an object containing all the messages from all of the bermdrips
	 *         contained in the reply
	 */
	public MultiMessages convertToMulti(BermdripInfoReply reply) {
		return convertToMulti(reply.getBermdripPublish());
	}

	/**
	 * Convert from {@link BermdripStatusPublish} to {@link MultiMessages}
	 * 
	 * @param publish
	 *            the publish
	 * @return a MultiMessages containing all the message from the publish, one
	 *         for each BERMDRIP.
	 */
	public MultiMessages convertToMulti(BermdripStatusPublish publish) {
		return convertToMulti(publish.getBermdripPublish());
	}

	private MultiMessages convertToMulti(List<BermdripPublish> publishes) {
		List<MultiMessages.MultiMessage> messages = new ArrayList<>();

		for (BermdripPublish publish : publishes) {
			messages.add(convertToMulti(publish));
		}

		return new MultiMessages(messages);
	}

	private MultiMessages.MultiMessage convertToMulti(BermdripPublish bermdripPublish) {
		String multi = convertToMulti(bermdripPublish.getBermdripStatusAttr().getBermdripImageAttr());

		return new MultiMessages.MultiMessage(bermdripPublish.getBermdripId().getId(), multi);
	}

	private String convertToMulti(BermdripImageAttr bermdripImageAttr) {
		CdmBermdripToMultiConverter converter = new CdmBermdripToMultiConverter();

		if(bermdripImageAttr!=null){
			converter.convert(bermdripImageAttr);
		}

		return converter.getResult();
	}
}
