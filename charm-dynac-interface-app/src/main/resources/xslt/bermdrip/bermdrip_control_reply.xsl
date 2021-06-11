<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>
    <xsl:param name="organization_id"/>
    <xsl:param name="request_id"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/bdrp:bermdrip_control_reply">
        <tmdd:deviceControlResponseMsg xsi:type="tmdd:DeviceControlResponseCharm">
            <organization-information>
                <organization-id>
                    <xsl:value-of select="$organization_id"/>
                </organization-id>
            </organization-information>
            <device-id>
                <xsl:value-of
                        select="bdrp:bermdrip_control_mutations/bdrp:bermdrip_control/bdrp:bermdrip_id/bdrp:id"/>
            </device-id>
            <request-id>
                <xsl:value-of select="$request_id"/>
            </request-id>
            <request-status>
                <xsl:choose>
                    <xsl:when
                            test="bdrp:bermdrip_control_mutations/bdrp:processed = 'true'">
                        <xsl:text>requested changes completed</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>request rejected invalid command parameters</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </request-status>
            <device-type>graphical dms</device-type>
            <comments>dummyComments</comments>
        </tmdd:deviceControlResponseMsg>
    </xsl:template>
</xsl:stylesheet>
