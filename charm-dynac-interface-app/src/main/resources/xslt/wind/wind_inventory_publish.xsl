<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns="http://wind.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="tmdd:genericDeviceInventoryUpdateMsg">
        <wind_inventory_publish version="1.0.0">
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
        </wind_inventory_publish>
    </xsl:template>

    <xsl:template match="dms-inventory-item">
        <wps_inventory>
            <action>
                <xsl:value-of
                        select="device-inventory-header/inventory-action"/>
            </action>
            <wps_id>
                <id>
                    <xsl:value-of select="device-inventory-header/device-id"/>
                </id>
            </wps_id>
            <wps_inventory_attr>
                <name>
                    <xsl:value-of select="device-inventory-header/device-name"/>
                </name>
                <xsl:if test="device-inventory-header/device-description">
                    <description>
                        <xsl:value-of
                                select="device-inventory-header/device-description"/>
                    </description>
                </xsl:if>
                <scan_enabled>
                    <xsl:value-of select="device-inventory-header/scan-on-off"/>
                </scan_enabled>
                 <address>
                    <xsl:value-of select="device-inventory-header/hostname"/>
                </address>
                 <port>
                    <xsl:value-of select="device-inventory-header/port"/>
                </port>
               
            </wps_inventory_attr>
                <wps_location_attr>
                    <position_bps>
                        <xsl:value-of
                                select="device-inventory-header/position-bps-code"/>
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
                </wps_location_attr>           
        </wps_inventory>
    </xsl:template>
</xsl:stylesheet>