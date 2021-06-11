package com.cgi.charm.dynac.mapping.drip;

import com.cgi.charm.dynac.mapping.XsltTest;
import org.custommonkey.xmlunit.Transform;
import org.junit.Test;

/**
 * @author CHARM CGI TEAM
 */
public class DripStatusPublishTest extends XsltTest {

    public DripStatusPublishTest() {
        super("xslt/drip/drip_status_publish.xsl");
    }

    @Override
    protected void configParameters(Transform testTransform) {
        testTransform.setParameter("multi_messages", "<multi-messages><multi-message><id>D A20 2,225</id>"
                + "<multi>[jl3]tot A4 aansl Leiderdorp[nl][jl2]&lt;- via A12/A4 27 min[nl][jl4]via N11 16 min -&gt;</multi></multi-message><multi-message><id>D A20 2,223</id>"
                + "<multi>[jl3]tot A4 aansl Leiderdorp[nl][jl2]&lt;- via A12/A4 27 min[nl][jl4]via N11 16 min -&gt;</multi></multi-message></multi-messages>");
    }

    @Test
    public void shouldTestPublish() {
        expectTransformResult("messages/drip/drip_status_publish.xml", "messages/drip/dMSStatusUpdateMsg.xml");
    }
}
