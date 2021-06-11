package com.cgi.charm.dynac.bermdrip.multi;

import com.cgi.charm.cdm.bermdrip.BermdripImageAttr;
import com.cgi.charm.cdm.bermdrip.Image;
import com.cgi.charm.cdm.bermdrip.Images;

/**
 * @author CHARM CGI TEAM
 */
class MultiToCdmBermdripConverter  implements DmsMultiParserHandler{

	private BermdripImageAttr result;


	/**
	 * Constructor
	 * 
	 */
	public MultiToCdmBermdripConverter() {
		result = new BermdripImageAttr();		
		
		result.setImages(new Images());
		
		//setting default values
		result.setAnimationDelay(0);
		result.setAnimationState(0);
	}


	public BermdripImageAttr getResult() {
		return result;
	}


	@Override
	public void graphic(int dmsGraphicNumber, String dmsGraphicData) {

		Image image = new Image();
		image.setImageNumber(dmsGraphicNumber);
		image.setImageBinary(dmsGraphicData);
		
		this.result.getImages().getImage().add(image);

		//animation state should be equals to no of bitmaps when 
		// there are more than one image excluding when dmsGraphicNumber 0
		//so incrementing state value when ever bitmap added

		if (dmsGraphicNumber > 0
				&& this.result.getImages().getImage().size() > 1) {
			this.result
					.setAnimationState((this.result.getAnimationState() > 0 ? this.result
							.getAnimationState() : 1) + 1);
		}
	}


	@Override
	public void pageTime(int onTime, int offTime) {
		this.result.setAnimationDelay(onTime);
	}


}
