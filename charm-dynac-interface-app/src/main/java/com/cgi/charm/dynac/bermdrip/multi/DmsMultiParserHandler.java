package com.cgi.charm.dynac.bermdrip.multi;

/**
 * @author CHARM CGI TEAM
 */
interface DmsMultiParserHandler {
	

	/**
	 * Display a graphic from the DMS graphics table.
	 *  
	 * 
	 * @param dmsGraphicIndex
	 *            is an integer index
	 * @param dmsGraphicData
	 *            is base64encoded string represents a bitmap image
	 *
	 */
	void graphic(int dmsGraphicIndex, String dmsGraphicData);
	

	/**
	 * Support Customizable Flashing/Animation Time when multiple images.
	 * <p>
	 * Please see section 6.4.16.
	 * </p> 
	 * 
	 * @param onTime
	 * 		   specifies value in tenth of a second. A value of 20 specifies image/page to be displayed
	 * 			for 2 seconds.
	 * 
	 * @param offTime
	 * 		  specifies value in tenth of a second. A value of 5 specifies that delay that 
	 * 		   for 0.5 seconds.
	 * 		  
	 */
	void pageTime(int onTime, int offTime);


	
}
