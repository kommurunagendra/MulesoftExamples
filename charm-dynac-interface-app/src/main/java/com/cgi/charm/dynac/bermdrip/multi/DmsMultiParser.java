package com.cgi.charm.dynac.bermdrip.multi;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tmdd._3.messages.DMSGraphic;

/**
 * @author CHARM CGI TEAM
 */
class DmsMultiParser {
	private static final Pattern TAG_PATTERN = Pattern.compile("\\[([,/a-zA-Z0-9]+)\\]");
	
	private boolean pageTimeProcessed = false;
	
	private List<DMSGraphic> dmsGraphics;

	/**
	 * Parse a single Multi string and call the handler for each occurrence of
	 * Multi tag.
	 * 
	 * 
	 * @param multi
	 *            the Multi string
	 * @param handler
	 *            the handler
	 */
	public void parse(String multi, DmsMultiParserHandler handler) {

		
		initDefaults();
		
		Matcher matcher = TAG_PATTERN.matcher(multi);

		while (matcher.find()) {
			String tag = matcher.group(1);

			parseTag(handler, tag);
		}

	}
	
	private void initDefaults(){
		pageTimeProcessed = false;		
	}

	private void parseTag(DmsMultiParserHandler handler, String tag) {
		//As per current design parsing pagetime[pt] tag only once
		if (tag.startsWith("pt")) {
			if( !pageTimeProcessed){
				parseTagPt(tag, handler);
				pageTimeProcessed = true;
			}
		}else if (tag.startsWith("np")){
			//currently we are not handling New page tag. so just return
			return;
		}else if (tag.startsWith("g")) {
			parseTagG(tag, handler);
		} else {
			throw new IllegalStateException("unknown tag: " + tag);
		}
	}

	
	private void parseTagPt(String tag, DmsMultiParserHandler handler) {
		//e.g [pt20o5]
		String[] args = tag.substring(2).split("o");
		
		int pageOnTime = 0;
		int pageOffTime = 0;
		if(args!=null && args.length>1){	
			pageOnTime = Integer.parseInt(args[0]);
			pageOffTime = Integer.parseInt(args[1]);

		}		
		handler.pageTime(pageOnTime, pageOffTime);		
	}

	private void parseTagG(String tag, DmsMultiParserHandler handler) {
		String[] args = parseArgs("g", tag);

		int dmsGraphicNumber = Integer.parseInt(args[0]);
		
		for(DMSGraphic graphic : this.dmsGraphics){
			if(graphic.getGraphicId() == dmsGraphicNumber){
				handler.graphic(dmsGraphicNumber, graphic.getBitmap());
				break;
			}				
		}		
	}
	
	private String[] parseArgs(String tagName, String tag) {
		return tagName.equals(tag) ? new String[0] : tag.substring(tagName.length()).split("o");
	}


	public void setDmsGraphics(List<DMSGraphic> dmsGraphics) {
		this.dmsGraphics = dmsGraphics;
	}

}
