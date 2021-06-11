<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns="http://drip.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/tmdd:dMSInventoryRequestMsg">
        <drip_info_request version="1.0.0">
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
            <info_spec>
                <inventory>true</inventory>
                <status>false</status>
            </info_spec>

            <xsl:choose>
                <!-- TODO what about device-filter? -->
                <xsl:when test="device-filter/device-id-list">
                    <xsl:for-each
                            select="device-filter/device-id-list/device-id">
                        <drip_spec>
                            <drip_id>
                                <id>
                                    <xsl:value-of select="."/>
                                </id>
                            </drip_id>
                        </drip_spec>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <all_drips>true</all_drips>
                </xsl:otherwise>
            </xsl:choose>
            <all_subscriptions>false</all_subscriptions>
        </drip_info_request>
    </xsl:template>
</xsl:stylesheet>