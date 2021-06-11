<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns="http://bermdrip.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="/tmdd:dMSControlRequestMsg">
        <bermdrip_control_request version="1.0.0">
            <meta>
                <message_id>
                    <uuid>
                        <xsl:value-of
                                select="$message_uuid"/>
                    </uuid>
                </message_id>
                <message_create_date>
                    <xsl:value-of
                            select="$message_create_date"/>
                </message_create_date>
            </meta>
            <bermdrip_control>
                <bermdrip_id>
                    <id>
                        <xsl:value-of
                                select="device-control-request-header/device-id"/>
                    </id>
                </bermdrip_id>
                <bermdrip_image_attr>
                    <images>
                        <xsl:for-each
                                select="dms-command-parameters/dms-graphics">
                            <image>
                                <image_number>
                                    <xsl:value-of select="./graphic-id"/>
                                </image_number>
                                <image_binary>
                                    <xsl:value-of select="./bitmap"/>
                                </image_binary>
                                <image_encoding>base64</image_encoding>
                                <image_mimetype>image/png</image_mimetype>
                                <image_height>0</image_height>
                                <image_width>0</image_width>
                            </image>
                        </xsl:for-each>
                    </images>
                    <animation_state>
                        <xsl:value-of select="dms-command-parameters/animation-state"/>
                    </animation_state>
                    <animation_delay>
                        <xsl:value-of select="dms-command-parameters/animation-delay"/>
                    </animation_delay>
                </bermdrip_image_attr>
                <bermdrip_control_attr>
                    <control_timestamp><xsl:value-of
                            select="$message_create_date"/></control_timestamp>
                    <operation_mode>online</operation_mode>
                    <dim_status>
                        <dim_mode>
                            <xsl:value-of select="dms-command-parameters/dms-control-attr/dim-mode"/>
                        </dim_mode>
                        <dim_level>
                            <xsl:value-of select="dms-command-parameters/dms-control-attr/dim-status"/>
                        </dim_level>
                    </dim_status>
                </bermdrip_control_attr>
            </bermdrip_control>
        </bermdrip_control_request>
    </xsl:template>
</xsl:stylesheet>
