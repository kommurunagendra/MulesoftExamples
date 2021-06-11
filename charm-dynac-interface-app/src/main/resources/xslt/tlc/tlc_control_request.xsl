<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns="http://tlc.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="../util/date_conversion.xsl"/>

    <xsl:param name="message_uuid"/>
    <xsl:param name="current_time_in_millis"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="tmdd:genericDeviceControlRequestMsg">
        <tlc_control_request version="1.0.0">
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
            <tlc_control>
                <tlc_id>
                    <id>
                        <xsl:value-of
                                select="device-control-request-header/device-id"/>
                    </id>
                </tlc_id>
                <tlc_control_attr>
                    <control_timestamp>
                        <xsl:call-template name="tmdd_date_to_isodate">
                            <xsl:with-param name="tmddDate"
                                            select="device-control-request-header/command-request-time"/>
                        </xsl:call-template>
                    </control_timestamp>

                    <xsl:for-each select="generic-object-list/generic-object">
                        <ivera_object>
                            <name>
                                <xsl:value-of
                                        select="./object-name"/>
                            </name>
                            <displayname>
                                <xsl:value-of
                                        select="./display-name"/>
                            </displayname>
                            <parameters>
                                <xsl:for-each
                                        select="./generic-element-list/generic-element">
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

                </tlc_control_attr>
            </tlc_control>
        </tlc_control_request>
    </xsl:template>
</xsl:stylesheet>