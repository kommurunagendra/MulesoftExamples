package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.dynac.multi.JustificationLineType;

/**
 * @author CHARM CGI TEAM
 */
interface DmsMultiParserHandler {
	/**
	 * A line justification tag
	 * 
	 * @param justificationLineType of type JustificationLineType
	 */
	void justificationLine(JustificationLineType justificationLineType);

	/**
	 * Text should be output using the current line settings.
	 * 
	 * @param text of type String
	 */
	void text(String text);

	/**
	 * A newline is started using the given line spacing.
	 * 
	 * @param lineSpacing of type Integer
	 */
	void newline(Integer lineSpacing);

	/**
	 * Display a graphic from the DMS graphics table.
	 * <p>
	 * Please see section 6.4.8.
	 * </p>
	 * 
	 * @param dmsGraphicNumber
	 *            is an octet string up to three characters in length indicating
	 *            the dmsGraphicNumber from the graphic table (not the
	 *            dmsGraphicIndex). â€œnâ€� shall be a numeric value between 1 and
	 *            255.
	 * @param x
	 *            specifies the horizontal displacement in pixels of the graphic
	 *            image from the left edge of the sign. A value of 1 specifies
	 *            that the left edge of the graphic image is in the left-most
	 *            pixel column of the sign. â€œxâ€� shall be a numeric value ranging
	 *            from 1 to the width of the sign minus the width of the graphic
	 *            plus 1.
	 * @param y
	 *            specifies the vertical displacement in pixels of the graphic
	 *            image from the top edge of the sign. A value of 1 specifies
	 *            that the top edge of the graphic image is in the top-most
	 *            pixel row of the sign. â€œyâ€� shall be a numeric value ranging
	 *            from 1 to the height of the sign minus the height of the
	 *            graphic plus 1.
	 * @param dmsGraphicVersionID
	 *            is an optional 4-digit hexadecimal number indicating the
	 *            graphicVersionID from the graphic table. Each â€˜câ€™ in â€œccccâ€�
	 *            shall be an ASCII character in the range from 0-9 or A-F.
	 */
	void graphic(int dmsGraphicNumber, int x, int y, String dmsGraphicVersionID);
	

	/**
	 * Display the character from the current font that has the given character index.
	 * <p>
	 * Please see section 6.4.9.
	 * </p> 
	 * 
	 * @param characterIndex character index
	 */
	void hexChar(int characterIndex);	
}
