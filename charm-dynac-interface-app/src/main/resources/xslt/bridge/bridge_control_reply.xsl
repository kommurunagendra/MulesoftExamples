<xsl:stylesheet version="2.0"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:brg="http://bridge.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>
    <xsl:param name="organization_id"/>
    <xsl:param name="request_id"/>

    <xsl:template match="/brg:bridge_control_reply">
        <tmdd:deviceControlResponseMsg xsi:type="tmdd:DeviceControlResponseCharm">
            <organization-information>
                <organization-id>
                    <xsl:value-of select="$organization_id"/>
                </organization-id>
            </organization-information>
            <device-id>
                <xsl:value-of
                        select="brg:control_reply[1]/brg:bridge_spec/brg:bridge_id/brg:id"/>
            </device-id>
            <request-id>
                <xsl:value-of select="$request_id"/>
            </request-id>
            <request-status>
                <xsl:choose>
                    <xsl:when
                            test="xs:boolean(brg:control_reply[1]/brg:processed)">
                        <xsl:text>requested changes completed</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>request rejected invalid command parameters</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </request-status>
            <device-type>bridge</device-type>
            <comments>dummyComments</comments>
        </tmdd:deviceControlResponseMsg>
    </xsl:template>
</xsl:stylesheet>
