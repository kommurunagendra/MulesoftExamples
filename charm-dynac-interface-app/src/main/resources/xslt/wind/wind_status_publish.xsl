<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:wind="http://wind.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="wind_common.xsl"/>

    <xsl:template match="/wind:wind_status_publish">
        <tmdd:genericDeviceStatusUpdateMsg>
            <c2cMsgAdmin>
                <subscriptionID>
                    <xsl:value-of select="wind:subscription_id/wind:uuid"/>
                </subscriptionID>
            </c2cMsgAdmin>
            <xsl:apply-templates select="wind:wps_publish"/>
        </tmdd:genericDeviceStatusUpdateMsg>
    </xsl:template>
</xsl:stylesheet>