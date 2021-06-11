<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:brg="http://bridge.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">

    <xsl:include href="../util/date_conversion.xsl"/>
    <xsl:param name="organizationId"/>
    <xsl:template match="brg:bridge_publish">
        <gate-status-item>
            <device-status-header
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:type="tmdd:DeviceStatusHeaderCharm">
                <organization-information>
                    <organization-id>
                        <xsl:value-of select="$organizationId"/>
                    </organization-id>
                </organization-information>
                <device-id>
                    <xsl:value-of select="brg:bridge_id/brg:id"/>
                </device-id>
                <device-status>on</device-status>
                <xsl:if test="not(normalize-space(brg:bridge_status_attr/brg:timestamp)='')">
                    <last-comm-time>
                        <xsl:call-template name="isodate_to_tmdd_date">
                            <xsl:with-param name="isoDateTimeInUtc"
                                            select="brg:bridge_status_attr/brg:timestamp"/>
                        </xsl:call-template>
                    </last-comm-time>
                </xsl:if>
                <device-type>bridge</device-type>
            </device-status-header>
            <gate-status>
                <xsl:variable name="bridgeStatus"
                              select="brg:bridge_status_attr/brg:status"/>
                <xsl:choose>
                    <xsl:when test="$bridgeStatus = 'open'">
                        <xsl:text>gate open</xsl:text>
                    </xsl:when>
                    <xsl:when test="$bridgeStatus = 'closed'">
                        <xsl:text>gate closed</xsl:text>
                    </xsl:when>
                    <xsl:when test="$bridgeStatus = 'opening'">
                        <xsl:text>opening</xsl:text>
                    </xsl:when>
                    <xsl:when test="$bridgeStatus = 'closing'">
                        <xsl:text>closing</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>gate partially opened closed</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </gate-status>
        </gate-status-item>
    </xsl:template>
</xsl:stylesheet>