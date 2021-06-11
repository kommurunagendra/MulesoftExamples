package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.cdm.drip.DripImageAttr;
import com.cgi.charm.cdm.drip.Text;
import com.cgi.charm.cdm.drip.TextAlignment;
import com.cgi.charm.dynac.multi.JustificationLineType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author CHARM CGI TEAM
 */
public class MultiToCdmDripConverterTest {
	private MultiToCdmDripConverter converter;

	@Before
	public void setup() {
		converter = new MultiToCdmDripConverter();
	}

	@Test
	public void shouldAdaptToCdmText() {
		whenAdapterIsGivenATextWith3NewLines();

		thenVerifyItIsConvertedIntoACdmMessageWith3Lines();
	}

	@Test
	public void shouldAdaptToCdmGraphicAndText() {
		whenAdapterIsGivenAGraphicAndText();

		thenVerifyItIsConvertedIntoACdmMessageWithGraphicAndText();
	}

	private void thenVerifyItIsConvertedIntoACdmMessageWith3Lines() {
		DripImageAttr image = converter.getResult();

		Text text = image.getText();
		assertNotNull(text);
		assertEquals(3, text.getLine().size());

		assertEquals("tot A4 aansl Leiderdorp", text.getLine().get(0).getTextSpecific());
		assertEquals(TextAlignment.CENTER, text.getLine().get(0).getTextAlignment());

		assertEquals("<- via A12/A4 27 min", text.getLine().get(1).getTextSpecific());
		assertEquals(TextAlignment.LEFT, text.getLine().get(1).getTextAlignment());

		assertEquals("via N11 16 min ->", text.getLine().get(2).getTextSpecific());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(2).getTextAlignment());
	}

	private void thenVerifyItIsConvertedIntoACdmMessageWithGraphicAndText() {
		DripImageAttr image = converter.getResult();

		Text text = image.getText();
		assertNotNull(text);
		assertEquals(5, text.getLine().size());

		assertEquals("A12 na", text.getLine().get(0).getTextSpecific());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(0).getTextAlignment());

		//assertEquals("� V'daal", text.getLine().get(1).getText());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(1).getTextAlignment());

		assertEquals("", text.getLine().get(2).getTextSpecific());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(2).getTextAlignment());

		assertEquals("Utrecht volg", text.getLine().get(3).getTextSpecific());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(3).getTextAlignment());

		assertEquals("A50/A15 ->", text.getLine().get(4).getTextSpecific());
		assertEquals(TextAlignment.RIGHT, text.getLine().get(4).getTextAlignment());

		assertEquals(1, image.getPictogram().size());

		assertEquals(0, image.getPictogram().get(0).getPictogramIndex());
		assertEquals(5, image.getPictogram().get(0).getPictogramNumber());
	}

	/**
	 * Example based on the following string:
	 * 
	 * <pre>
	 * [gfilename][jl4]A 12 na[nl][jl4][hcFF] V’ daal[nl][jl4]Utrecht volg[nl][jl4]A50/A15 [hcFE]
	 * </pre>
	 */
	private void whenAdapterIsGivenAGraphicAndText() {
		converter.graphic(5, 1, 1, null);
		converter.justificationLine(JustificationLineType.RIGHT);
		converter.text("A12 na");
		converter.newline(null);
		// 0x00DE = Þ
		converter.hexChar(0x00DE);
		converter.justificationLine(JustificationLineType.RIGHT);
		converter.text(" V'daal");
		converter.newline(2);
		converter.justificationLine(JustificationLineType.RIGHT);
		converter.text("Utrecht volg");
		converter.newline(null);
		converter.justificationLine(JustificationLineType.RIGHT);
		converter.text("A50/A15 ->");
	}

	private void whenAdapterIsGivenATextWith3NewLines() {
		converter.justificationLine(JustificationLineType.CENTER);
		converter.text("tot A4 aansl Leiderdorp");
		converter.newline(null);
		converter.justificationLine(JustificationLineType.LEFT);
		converter.text("<- via A12/A4 27 min");
		converter.newline(null);
		converter.justificationLine(JustificationLineType.RIGHT);
		converter.text("via N11 16 min ->");
	}
}
