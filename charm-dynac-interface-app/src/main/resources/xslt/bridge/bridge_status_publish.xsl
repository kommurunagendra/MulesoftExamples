<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:bridge="http://bridge.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="bridge_common.xsl"/>

    <xsl:template match="/bridge:bridge_status_publish">
        <tmdd:gateStatusUpdateMsg>
            <c2cMsgAdmin>
                <subscriptionID>
                    <xsl:value-of select="bridge:subscription_id/bridge:uuid"/>
                </subscriptionID>
            </c2cMsgAdmin>
            <xsl:apply-templates select="bridge:bridge_publish"/>
        </tmdd:gateStatusUpdateMsg>
    </xsl:template>
</xsl:stylesheet>
