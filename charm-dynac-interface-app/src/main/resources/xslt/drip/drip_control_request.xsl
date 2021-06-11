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
    <xsl:param name="multi"
               select="tmdd:dMSControlRequestMsg/dms-command-parameters/dms-message"/>

    <xsl:variable name="message_create_date"
                  select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

    <xsl:template match="tmdd:dMSControlRequestMsg">
        <drip_control_request version="1.0.0">
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
            <drip_control>
                <drip_id>
                    <id>
                        <xsl:value-of
                                select="device-control-request-header/device-id"/>
                    </id>
                </drip_id>
                <xsl:if test="dms-request-command = 'put up custom message'">
                    <drip_image_attr>
                        <duration>-1</duration>
                        <text>
                            <xsl:for-each
                                    select="fi:tokenize($multi, '(\[nl\])')">
                                <line>
                                    <text_alignment>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="fi:matches(.,'(\[jl\d\])')">
                                                <xsl:analyze-string select="."
                                                                    regex="(\[(jl(\d))\])">
                                                    <xsl:matching-substring>
                                                        <xsl:choose>
                                                        <xsl:when test="number(regex-group(3)) = 2">left</xsl:when>
                                                        <xsl:when test="number(regex-group(3)) = 3">center</xsl:when>
                                                        <xsl:when test="number(regex-group(3)) = 4">right</xsl:when>
                                                        <xsl:otherwise>center</xsl:otherwise>
                                                        </xsl:choose>
                                                        <!--<xsl:if test="number(regex-group(3)) = 5">full</xsl:if>-->
                                                    </xsl:matching-substring>
                                                </xsl:analyze-string>
                                            </xsl:when>
                                              <!-- default value -->
                                            <xsl:otherwise>left</xsl:otherwise>
                                        </xsl:choose>
                                    </text_alignment>
                                    <text_specific>
                                    <xsl:analyze-string select="."
                                                            regex="(\[.*\])?([\w \d]+)">
                                            <xsl:matching-substring>
                                                <xsl:value-of
                                                        select="fi:regex-group(2)"/>
                                            </xsl:matching-substring>
                                        </xsl:analyze-string>
                                        </text_specific>
                                    <text_generic> <xsl:analyze-string select="."
                                                            regex="(\[.*\])?([\w \d]+)">
                                            <xsl:matching-substring>
                                                <xsl:value-of
                                                        select="fi:regex-group(2)"/>
                                            </xsl:matching-substring>
                                        </xsl:analyze-string>
                                        </text_generic>
                                </line>
                            </xsl:for-each>
                        </text>
                        <xsl:if test="fi:matches($multi, '(\[g)')">
                            <xsl:for-each select="fi:tokenize($multi, '(\[g)')">
                                <xsl:if test="fi:matches(.,'((\d),(\d)\])')">
                                    <pictogram>
                                        <pictogram_number>
                                            <xsl:analyze-string select="."
                                                                regex="((\d),(\d)\])">
                                                <xsl:matching-substring>
                                                    <xsl:value-of
                                                            select="fi:regex-group(2)"/>
                                                </xsl:matching-substring>
                                            </xsl:analyze-string>
                                        </pictogram_number>
                                        <pictogram_index>
                                            <xsl:analyze-string select="."
                                                                regex="((\d),(\d)\])">
                                                <xsl:matching-substring>
                                                    <xsl:value-of
                                                            select="fi:regex-group(3)"/>
                                                </xsl:matching-substring>
                                            </xsl:analyze-string>
                                        </pictogram_index>
                                    </pictogram>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:if>
                        <xsl:choose>
                            <xsl:when test="matches($multi, '(\[fl)')">
                                <flasher>
                                    <flasher_mode>on</flasher_mode>
                                </flasher>
                            </xsl:when>
                            <xsl:otherwise>
                                
                                    <flasher_mode>off</flasher_mode>
                                
                            </xsl:otherwise>
                        </xsl:choose>
                    </drip_image_attr>
                </xsl:if>
                <drip_control_attr>
                    <control_timestamp><xsl:value-of select="$message_create_date"/></control_timestamp>
                    <operation_mode>
                        <xsl:value-of
                                select="dms-command-parameters/dms-control-attr/operation-mode"/>
                    </operation_mode>
                    <dim_status>
                        <dim_mode>
                            <xsl:value-of
                                    select="dms-command-parameters/dms-control-attr/dim-mode"/>
                        </dim_mode>
                        <dim_state>
                            <xsl:value-of
                                    select="dms-command-parameters/dms-control-attr/dim-status"/>
                        </dim_state>
                    </dim_status>
                </drip_control_attr>
            </drip_control>
        </drip_control_request>
    </xsl:template>
</xsl:stylesheet>