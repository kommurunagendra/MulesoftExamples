package com.cgi.charm.dynac.mapping.drip;

import org.custommonkey.xmlunit.Transform;
import com.cgi.charm.dynac.mapping.XsltTest;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class DripInfoReplyStatusTest extends XsltTest {

	public DripInfoReplyStatusTest() {
		super("xslt/drip/drip_info_reply_status.xsl");
	}

	@Override
	protected void configParameters(Transform testTransform) {
		/*testTransform.setParameter("multi_messages", "<multi-messages><multi-message><id>D A20 2,223</id>"
				+ "<multi>[jl3]tot A4 aansl Leiderdorp[nl][jl2]&lt;- via A12/A4 27 min[nl][jl4]via N11 16 min -&gt;</multi></multi-message></multi-messages>");
				*/
		testTransform.setParameter("organization_id", "RWS");
	}

	//TODO: Ignored, currently fails on whitespace comparison issues, migrate to xmlunit 2!
	@Test
	public void shouldTestStatusReply() {
		expectTransformResult("messages/drip/mocked_drip_info_success_reply.xml", "messages/drip/mocked_dMS_success_status_response.xml");
	}
}
