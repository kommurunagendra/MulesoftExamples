package com.cgi.charm.dynac.bermdrip.multi;

import com.cgi.charm.cdm.bermdrip.BermdripImageAttr;
import com.cgi.charm.cdm.bermdrip.Image;

/**
 * @author CHARM CGI TEAM
 */
class CdmBermdripToMultiConverter {
	private StringBuilder result = new StringBuilder();

	/**
	 * Convert the image, the result can be obtained with {@link #getResult()}.
	 * 
	 * @param image
	 *            the image
	 */
	public void convert(BermdripImageAttr image) {

		if (image.getAnimationDelay() > 0) {
			result.append("[pt").append(image.getAnimationDelay()).append("o")
					.append(image.getAnimationState()).append("]");
		}

		// add graphic tags
		if (image.getImages() != null) {
			for (Image img : image.getImages().getImage()) {
				result.append("[g").append(img.getImageNumber()).append("]");
			}
		}
	}

	public String getResult() {
		return result.toString();
	}

}
