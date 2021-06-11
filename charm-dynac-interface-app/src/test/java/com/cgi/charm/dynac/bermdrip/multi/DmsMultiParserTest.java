package com.cgi.charm.dynac.bermdrip.multi;

import org.tmdd._3.messages.DMSGraphic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author CHARM CGI TEAM
 */
public class DmsMultiParserTest {
	
	/**
	 * 
	 *  supported tag [ptxoy] [g1][np] [g2]
	 *  
	 */
	
	public void graphic() {
		final String multi = "[pt20o5][g1][np]";
		List<DMSGraphic> dmsGraphics = new ArrayList<DMSGraphic>();
		DMSGraphic dmsGraphic = new DMSGraphic();
		dmsGraphic.setGraphicId(1);
		dmsGraphic.setBitmap("");
		dmsGraphics.add(dmsGraphic);
		
		DmsMultiParserHandler handler = runMatcher(multi,dmsGraphics);
		
		verify(handler).graphic(1, "");
		
		verify(handler).pageTime(20, 0);

	}
	

	private DmsMultiParserHandler runMatcher(final String multi,final List<DMSGraphic> dmsGraphics) {
		DmsMultiParserHandler handler = mock(DmsMultiParserHandler.class);

		DmsMultiParser parser = new DmsMultiParser();
		parser.setDmsGraphics(dmsGraphics);
		parser.parse(multi, handler);
		return handler;
	}
}
