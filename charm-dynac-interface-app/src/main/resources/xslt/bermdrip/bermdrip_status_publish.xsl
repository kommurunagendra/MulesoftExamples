<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="../util/date_conversion.xsl"/>
    <xsl:include href="bermdrip_common.xsl"/>

    <xsl:template match="/bdrp:bermdrip_status_publish">
        <tmdd:dMSStatusUpdateMsg>
            <c2cMsgAdmin>
                <subscriptionID>
                    <xsl:value-of select="bdrp:subscription_id/bdrp:uuid"/>
                </subscriptionID>
            </c2cMsgAdmin>

            <xsl:apply-templates select="bdrp:bermdrip_publish"/>
        </tmdd:dMSStatusUpdateMsg>
    </xsl:template>
</xsl:stylesheet>
