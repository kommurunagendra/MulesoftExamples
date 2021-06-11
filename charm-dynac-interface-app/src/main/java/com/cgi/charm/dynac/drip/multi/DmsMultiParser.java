package com.cgi.charm.dynac.drip.multi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cgi.charm.dynac.multi.JustificationLineType;

/**
 * @author CHARM CGI TEAM
 */
class DmsMultiParser {
	private static final Pattern TAG_PATTERN = Pattern.compile("\\[([,/a-zA-Z0-9]+)\\]");

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
		//initDefaults(handler);

		Matcher matcher = TAG_PATTERN.matcher(multi);

		int lastMatch = 0;
		while (matcher.find()) {
			String tag = matcher.group(1);

			if (matcher.start() > lastMatch) {
				handler.text(multi.substring(lastMatch, matcher.start()));
			}

			parseTag(handler, tag);

			lastMatch = matcher.end();
		}

		if (lastMatch < multi.length()) {
			handler.text(multi.substring(lastMatch));
		}
	}

/*	private void initDefaults(DmsMultiParserHandler handler) {

	}*/

	private void parseTag(DmsMultiParserHandler handler, String tag) {
		if (tag.startsWith("jl")) {
			parseTagJl(tag, handler);
		} else if (tag.startsWith("nl")) {
			parseTagNl(tag, handler);
		} else if (tag.startsWith("g")) {
			parseTagG(tag, handler);
		} else if (tag.startsWith("hc")) {
			parseTagHc(tag, handler);
		} else {
			throw new IllegalStateException("unknown tag: " + tag);
		}
	}

	private void parseTagNl(String tag, DmsMultiParserHandler handler) {
		String[] args = parseArgs("nl", tag);

		Integer lineSpacing = args.length == 1 ? Integer.parseInt(args[0]) : null;

		handler.newline(lineSpacing);
	}

	private void parseTagJl(String tag, DmsMultiParserHandler handler) {
		String[] args = parseArgs("jl", tag);

		JustificationLineType type = parseTagJlType(args[0]);

		handler.justificationLine(type);
	}

	private void parseTagG(String tag, DmsMultiParserHandler handler) {
		String[] args = parseArgs("g", tag);

		int dmsGraphicNumber = Integer.parseInt(args[0]);
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		String dmsGraphicVersionID = args.length >= 4 ? args[3] : null;

		handler.graphic(dmsGraphicNumber, x, y, dmsGraphicVersionID);
	}
	

	private void parseTagHc(String tag, DmsMultiParserHandler handler) {
		String[] args = parseArgs("hc", tag);
		
		int characterIndex = Integer.parseInt(args[0], 16);
		
		handler.hexChar(characterIndex);
	}

	private JustificationLineType parseTagJlType(String arg) {
		switch (arg) {
		case "1":
			return JustificationLineType.OTHER;
		case "2":
			return JustificationLineType.LEFT;
		case "3":
			return JustificationLineType.CENTER;
		case "4":
			return JustificationLineType.RIGHT;
		case "5":
			return JustificationLineType.FULL;
		default:
			throw new IllegalStateException("invalid justification code: " + arg);
		}
	}

	private String[] parseArgs(String tagName, String tag) {
		return tagName.equals(tag) ? new String[0] : tag.substring(tagName.length()).split(",");
	}

}
