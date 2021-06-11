<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tlc="http://tlc.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="tlc_common.xsl"/>

    <xsl:template match="/tlc:tlc_status_publish">
        <tmdd:genericDeviceStatusUpdateMsg>
            <c2cMsgAdmin>
                <subscriptionID><xsl:value-of select="tlc:subscription_id/tlc:uuid"/></subscriptionID>
            </c2cMsgAdmin>
            <xsl:apply-templates select="tlc:tlc_publish"/>
        </tmdd:genericDeviceStatusUpdateMsg>
    </xsl:template>
</xsl:stylesheet>