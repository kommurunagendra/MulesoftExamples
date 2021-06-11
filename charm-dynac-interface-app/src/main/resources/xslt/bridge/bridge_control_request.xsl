<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns="http://bridge.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:include href="../util/date_conversion.xsl"/>

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/tmdd:gateControlRequestMsg">
        <bridge_control_request version="1.0.0">
            <meta>
                <message_id>
                    <uuid>
                        <xsl:value-of select="$message_uuid"/>
                    </uuid>
                </message_id>
                <message_create_date>
                    <xsl:value-of select="$message_create_date"/>
                </message_create_date>
            </meta>
            <control>
                <bridge_spec>
                    <bridge_id>
                        <id>
                            <xsl:value-of
                                    select="device-control-request-header/device-id"/>
                        </id>
                    </bridge_id>
                </bridge_spec>
                <bridge_control_attr>
                    <status>
                        <xsl:variable name="status" select="gate-control"/>
                        <xsl:choose>
                            <xsl:when test="$status = 'close gate'">closing</xsl:when>
                            <xsl:when test="$status = 'open gate'">opening</xsl:when>
                            <xsl:when test="$status = 'closed'">closed</xsl:when>
                            <xsl:when test="$status = 'opened'">open</xsl:when>
                        </xsl:choose>
                    </status>
                    <control_timestamp>
                        <xsl:call-template name="tmdd_date_to_isodate">
                            <xsl:with-param name="tmddDate"
                                            select="device-control-request-header/command-request-time"/>
                        </xsl:call-template>
                    </control_timestamp>
                </bridge_control_attr>
            </control>
        </bridge_control_request>
    </xsl:template>
</xsl:stylesheet>
