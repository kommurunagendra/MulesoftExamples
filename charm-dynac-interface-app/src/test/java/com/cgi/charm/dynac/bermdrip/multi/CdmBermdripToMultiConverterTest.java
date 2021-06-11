package com.cgi.charm.dynac.bermdrip.multi;

import com.cgi.charm.cdm.bermdrip.BermdripImageAttr;
import com.cgi.charm.cdm.bermdrip.Image;
import com.cgi.charm.cdm.bermdrip.Images;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author CHARM CGI TEAM
 */
public class CdmBermdripToMultiConverterTest {
	@Test
	public void shouldTestBermdrip() {
		BermdripImageAttr image = new BermdripImageAttr();
		image.setAnimationDelay(4);
		image.setAnimationState(0);
		
		List<Image> imageList = new ArrayList<>();
		Image imageObj = new Image();
		imageObj.setImageNumber(1);
		imageList.add(imageObj);
		image.setImages(new Images(imageList));
		CdmBermdripToMultiConverter converter = new CdmBermdripToMultiConverter();

		converter.convert(image);
		String multi = converter.getResult();

		assertEquals("[pt4o0][g1]", multi);
	}
}
