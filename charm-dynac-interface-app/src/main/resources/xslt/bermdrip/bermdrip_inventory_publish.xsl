<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns="http://bermdrip.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/tmdd:dMSInventoryUpdateMsg">
        <bermdrip_inventory_publish version="1.0.0">
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
            <subscription_id>
                <uuid>
                    <xsl:value-of select="c2cMsgAdmin/subscriptionID"/>
                </uuid>
            </subscription_id>

            <inventory_config>
                <xsl:choose>
                    <xsl:when
                            test="c2cMsgAdmin/informationalText = 'FULLSYNC'">
                        <full_sync>true</full_sync>
                    </xsl:when>
                    <xsl:otherwise>
                        <full_sync>false</full_sync>
                    </xsl:otherwise>
                </xsl:choose>
                <inventory_id>
                    <uuid>
                        <xsl:value-of select="c2cMsgAdmin/subscriptionID"/>
                    </uuid>
                </inventory_id>

                <xsl:apply-templates select="dms-inventory-item"/>
            </inventory_config>
        </bermdrip_inventory_publish>
    </xsl:template>

    <xsl:template match="dms-inventory-item">
        <bermdrip_inventory xmlns="http://bermdrip.cdm.charm.cgi.com">
            <bermdrip_id>
                <id>
                    <xsl:value-of select="device-inventory-header/device-id"/>
                </id>
            </bermdrip_id>
            <action>
                <xsl:value-of
                        select="device-inventory-header/inventory-action"/>
            </action>
            <bermdrip_inventory_attr>
                <name>
                    <xsl:value-of select="device-inventory-header/device-name"/>
                </name>
                <description>
                    <xsl:value-of
                            select="device-inventory-header/device-description"/>
                </description>
                <owner>dynac</owner>
                <scan_enabled>
                    <xsl:value-of select="device-inventory-header/scan-on-off"/>
                </scan_enabled>
                <scan_interval>
                    <xsl:value-of
                            select="device-inventory-header/scan-interval"/>
                </scan_interval>
                <dms_sign_type>
                    <xsl:value-of select="dms-sign-type"/>
                </dms_sign_type>
                <protocol>
                    <xsl:analyze-string select="device-inventory-header/protocol" regex="(tcp-\d).*">
                        <xsl:matching-substring>
                            <xsl:value-of select="fi:regex-group(1)"/>
                        </xsl:matching-substring>
                    </xsl:analyze-string>
                </protocol>
                <hostname>
                    <xsl:value-of select="device-inventory-header/hostname"/>
                </hostname>
                <port>
                    <xsl:value-of select="device-inventory-header/port"/>
                </port>
                <response_timeout>
                    <xsl:value-of
                            select="device-inventory-header/response-timeout"/>
                </response_timeout>
                <command_timeout>
                    <xsl:value-of
                            select="device-inventory-header/command-timeout"/>
                </command_timeout>
                <physical_mounting>
                    <xsl:value-of select="fi:concat(physical-mounting, 'Mounted')"/>
                </physical_mounting>
                <vms_type>
                    <xsl:value-of select="vms-type"/>
                </vms_type>
            </bermdrip_inventory_attr>
            <bermdrip_location_attr>
                <position_bps>
                    <xsl:value-of select="device-inventory-header/position-bps-code"/>
                </position_bps>
                <position_wgs84>
                    <latitude>
                        <xsl:value-of
                                select="device-inventory-header/device-location/latitude"/>
                    </latitude>
                    <longitude>
                        <xsl:value-of
                                select="device-inventory-header/device-location/longitude"/>
                    </longitude>
                </position_wgs84>
                <xsl:if	test="device-inventory-header/link-direction">
                	<direction><xsl:value-of select="device-inventory-header/link-direction"/></direction>
                </xsl:if>
            </bermdrip_location_attr>
        </bermdrip_inventory>
    </xsl:template>
</xsl:stylesheet>