<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns="http://tlc.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/tmdd:deviceInformationSubscriptionMsg">
        <tlc_status_subscription version="1.0.0">
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
            <subscription_config>
                <subscription_id>
                    <uuid>
                        <xsl:value-of select="c2cMsgAdmin/subscriptionID"/>
                    </uuid>
                </subscription_id>
                <subscription_attr>
                    <subscription_name>
                        <xsl:value-of
                                select="c2cMsgAdmin/subscriptionName"/>
                    </subscription_name>
                    <subscription_type>status</subscription_type>
                </subscription_attr>
                <xsl:choose>
                    <xsl:when test="device-filter/device-id-list">
                        <xsl:for-each
                                select="device-filter/device-id-list">
                            <tlc_spec>
                                <tlc_id>
                                    <id>
                                        <xsl:value-of select="."/>
                                    </id>
                                </tlc_id>
                            </tlc_spec>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <all_tlcs>true</all_tlcs>
                    </xsl:otherwise>
                </xsl:choose>
            </subscription_config>
        </tlc_status_subscription>
    </xsl:template>
</xsl:stylesheet>