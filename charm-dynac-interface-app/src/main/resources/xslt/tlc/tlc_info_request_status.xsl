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

    <xsl:template match="/tmdd:genericDeviceStatusRequestMsg">
        <tlc_info_request version="1.0.0">
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
                <xsl:choose>
                    <xsl:when test="device-information-type = 'device status'">
                        <inventory>false</inventory>
                        <status>true</status>
                    </xsl:when>
                    <xsl:otherwise>
                        <inventory>true</inventory>
                        <status>false</status>
                    </xsl:otherwise>
                </xsl:choose>
            </info_spec>
            <xsl:choose>
                <xsl:when test="device-filter/device-id-list">
                    <xsl:for-each
                            select="device-filter/device-id-list/device-id">
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
                    <all_tlc>true</all_tlc>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="device-filter/data-owner">
              <owner>
                <xsl:value-of select="device-filter/data-owner"/>
                </owner>
                </xsl:if>
            <realtime_status>
            	<xsl:choose>
                    <xsl:when test="realtime-status"> <xsl:value-of select="realtime-status"/> </xsl:when>
                    <xsl:otherwise> false </xsl:otherwise>
                </xsl:choose>
            </realtime_status>
            
            <all_subscriptions>false</all_subscriptions>
            <xsl:if test="device-filter/object-id-list">
                <tlc_extra>
                <xsl:for-each select="device-filter/object-id-list/generic-object">
                    <ivera_object>
                        <name>
                            <xsl:value-of select="object-name"/>
                        </name>
                        <displayname>
                        	<xsl:value-of select="display-name"/>
                        </displayname>
                        <parameters>
                            <xsl:for-each
                                    select="generic-element-list/generic-element">
                                <parameter>
                                    <key>
                                        <xsl:value-of select="element-name"/>
                                    </key>
                                    <value>
                                    	<xsl:value-of select="element-value"/>
                                    </value>
                                </parameter>
                            </xsl:for-each>
                        </parameters>
                    </ivera_object>
                    </xsl:for-each>
                </tlc_extra>
            </xsl:if>
        </tlc_info_request>
    </xsl:template>
</xsl:stylesheet>