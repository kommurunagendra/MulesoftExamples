package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.dynac.multi.JustificationLineType;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author CHARM CGI TEAM
 */
public class DmsMultiParserTest {
	@Test
	public void alignment() {
		final String multi = "DEFLFT[jl2]LFT[jl3]CNTR[jl4]RIGHT";

		DmsMultiParserHandler handler = runMatcher(multi);

		verify(handler).text("DEFLFT");
		verify(handler).justificationLine(JustificationLineType.LEFT);
		verify(handler).text("LFT");
		verify(handler).justificationLine(JustificationLineType.CENTER);
		verify(handler).text("CNTR");
		verify(handler).justificationLine(JustificationLineType.RIGHT);
		verify(handler).text("RIGHT");
	}

	@Test
	public void shouldNewLine() {
		final String multi = "[nl]THIS IS[nl5]A TEST";

		DmsMultiParserHandler handler = runMatcher(multi);

		verify(handler).newline(null);
		verify(handler).text("THIS IS");
		verify(handler).newline(5);
		verify(handler).text("A TEST");
	}
	
	/**
	 * According to Jarred Russel from Ktc the following string must be supported:
	 * [gfilename][jl4]A 12 na[nl][jl4][hcFF] V’ daal[nl][jl4]Utrecht volg[nl][jl4]A50/A15 [hcFE]
	 *  <p>
	 *  Please note that [gfilename] will not be supported, instead it will use a number.
	 *  </p>
	 */
	@Test 
	public void shouldGraphic() {
		final String multi = "[g123,1,1,0001]";
		
		DmsMultiParserHandler handler = runMatcher(multi);
		
		verify(handler).graphic(123, 1, 1, "0001");
	}
	
	@Test
	public void shouldHexChar() {
		final String multi = "[hc00DE]";
		
		DmsMultiParserHandler handler = runMatcher(multi);
		
		verify(handler).hexChar(0x00DE);
	}

	private DmsMultiParserHandler runMatcher(final String multi) {
		DmsMultiParserHandler handler = mock(DmsMultiParserHandler.class);

		DmsMultiParser parser = new DmsMultiParser();

		parser.parse(multi, handler);
		return handler;
	}
}
