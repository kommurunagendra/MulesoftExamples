package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.cdm.drip.DripImageAttr;
import com.cgi.charm.cdm.drip.Line;
import com.cgi.charm.cdm.drip.Text;
import com.cgi.charm.cdm.drip.TextAlignment;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author CHARM CGI TEAM
 */
public class CdmDripToMultiConverterTest {
	@Test
	public void shouldTekstDrip() {
		DripImageAttr image = new DripImageAttr()
			.withText(new Text()
				.withLine(
						new Line()
							.withTextSpecific("tot A4 aansl Leiderdorp")
							.withTextAlignment(TextAlignment.CENTER),
						new Line()
							.withTextSpecific("<- via A12/A4 27 min")
							.withTextAlignment(TextAlignment.LEFT),
						new Line()
							.withTextSpecific("via N11 16 min ->")
							.withTextAlignment(TextAlignment.RIGHT)
					));
		
		CdmDripToMultiConverter converter = new CdmDripToMultiConverter();
		
		converter.convert(image);
		String multi = converter.getResult();
		
		
		assertEquals("[jl3]tot A4 aansl Leiderdorp[nl][jl2]<- via A12/A4 27 min[nl][jl4]via N11 16 min ->", multi);
	}
}
