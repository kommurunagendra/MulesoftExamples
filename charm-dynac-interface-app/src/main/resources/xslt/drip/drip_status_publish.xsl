<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:drp="http://drip.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="multi_messages"/>

    <xsl:include href="../util/date_conversion.xsl"/>

    <xsl:template match="/drp:drip_status_publish">
        <tmdd:dMSStatusUpdateMsg>
            <c2cMsgAdmin>
                <subscriptionID>
                    <xsl:value-of select="drp:subscription_id/drp:uuid"/>
                </subscriptionID>
            </c2cMsgAdmin>
            <xsl:for-each select="drp:drip_publish">
                <dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
                    <device-status-header
                            xsi:type="tmdd:DeviceStatusHeaderCharm">
                        <organization-information>
                            <organization-id>RWS</organization-id>
                        </organization-information>
                        <device-id>
                            <xsl:value-of select="./drp:drip_id/drp:id"/>
                        </device-id>
                        <device-status>
                            <xsl:value-of
                                    select="./drp:drip_status_attr/drp:general_status/drp:device_status"/>
                        </device-status>
                        <device-comm-status>
                            <xsl:value-of
                                    select="./drp:drip_status_attr/drp:general_status/drp:device_comm_status"/>
                        </device-comm-status>
                        <device-type>text dms</device-type>
                    </device-status-header>
                    <current-message>
                        <xsl:for-each
                                select="./drp:drip_image_attr/drp:text/drp:line">
                            <xsl:if test="fi:position() != 1">[nl]</xsl:if>
                            <xsl:for-each select="./drp:text_alignment">
                                <xsl:analyze-string select="." regex="(\w+)">
                                    <xsl:matching-substring>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="regex-group(1) = 'other'">[jl1]</xsl:when>
                                            <xsl:when
                                                    test="regex-group(1) = 'left'">[jl2]</xsl:when>
                                            <xsl:when
                                                    test="regex-group(1) = 'center'">[jl3]</xsl:when>
                                            <xsl:when
                                                    test="regex-group(1) = 'right'">[jl4]</xsl:when>
                                            <!--<xsl:if test="number(regex-group(3)) = 5">full</xsl:if>-->
                                            <!--<xsl:value-of select="fi:regex-group(1)"/>-->
                                        </xsl:choose>
                                    </xsl:matching-substring>
                                </xsl:analyze-string>
                            </xsl:for-each>
                            <xsl:value-of select="./drp:text_specific"/>
                        </xsl:for-each>
                        <xsl:for-each
                                select="./drp:drip_image_attr/drp:pictogram">
                            <xsl:value-of
                                    select="fi:concat('[g',./drp:pictogram_number,',',./drp:pictogram_index,']')"/>
                        </xsl:for-each>
                    </current-message>
                    <dms-control-attr>
                        <operation-mode>
                            <xsl:value-of
                                    select="./drp:drip_control_attr/drp:operation_mode"/>
                        </operation-mode>
                        <dim-mode>
                            <xsl:value-of
                                    select="./drp:drip_control_attr/drp:dim_status/drp:dim_mode"/>
                        </dim-mode>
                        <dim-status>
                            <xsl:value-of
                                    select="./drp:drip_control_attr/drp:dim_status/drp:dim_state"/>
                        </dim-status>
                    </dms-control-attr>

                    <dms-status-attr>
                        <climate-status>
                            <xsl:variable name="climate_mode"><xsl:value-of
                                select="./drp:drip_status_attr/drp:general_status/drp:climate_mode"/></xsl:variable>
                            <xsl:choose>
                                <xsl:when
                                        test="$climate_mode = 'none'">0</xsl:when>
                                <xsl:when
                                        test="$climate_mode = 'automatic'">1</xsl:when>
                                <xsl:when
                                        test="$climate_mode = 'manual'">2</xsl:when>
                                <xsl:when
                                        test="$climate_mode = 'automatic_manual'">3</xsl:when>
                            </xsl:choose>
                        </climate-status>

                        <xsl:choose>
                            <xsl:when
                                    test="./drp:drip_status_attr/drp:display_status">
                                <display-status>
                                    <display-mode>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="./drp:drip_status_attr/drp:display_status/drp:display_mode">
                                                1
                                            </xsl:when>
                                            <xsl:otherwise>0</xsl:otherwise>
                                        </xsl:choose>
                                    </display-mode>
                                    <display-state>
                                        <xsl:value-of
                                                select="./drp:drip_status_attr/drp:display_status/drp:display_state"/>
                                    </display-state>
                                    <flasher-state>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="./drp:drip_status_attr/drp:display_status/drp:flasher_state = 'not_available'">
                                                not available
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of
                                                        select="./drp:drip_status_attr/drp:display_status/drp:flasher_state"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </flasher-state>
                                    <pictogram-state>
                                        <xsl:choose>
                                            <xsl:when
                                                    test="./drp:drip_status_attr/drp:display_status/drp:pictogram_state = 'not_available'">
                                                not available
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of
                                                        select="./drp:drip_status_attr/drp:display_status/drp:pictogram_state"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </pictogram-state>
                                </display-status>
                            </xsl:when>
                            <xsl:otherwise>
                                <display-status>
                                    <display-mode>0</display-mode>
                                    <display-state>false</display-state>
                                    <flasher-state>not available</flasher-state>
                                    <pictogram-state>not available</pictogram-state>
                                </display-status>
                            </xsl:otherwise>
                        </xsl:choose>

                        <other-information>
                            <drip-type>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:drip_type"/>
                            </drip-type>
                            <serial-number>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:serial_number"/>
                            </serial-number>
                            <drip-version>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:drip_version"/>
                            </drip-version>
                            <location-code>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:location_code"/>
                            </location-code>
                            <protocol-config>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:protocol_version"/>
                            </protocol-config>
                            <font-name>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:font_name"/>
                            </font-name>
                            <regel-config>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:regel_config"/>
                            </regel-config>
                             <temperature>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:temperature"/>
                            </temperature>
                             <ventilator-status>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:ventilator_status"/>
                            </ventilator-status>
                        </other-information>

                     <xsl:if test="count(drp:drip_status_attr/drp:door_status) &gt; 0 ">
                <door-status-list>
                <xsl:for-each select="drp:drip_status_attr/drp:door_status">
                    <dms-door>
                        <door-nr>
                            <xsl:value-of
                                    select="drp:door_number"/>
                        </door-nr>
                        <door-state>
                            <xsl:value-of
                                    select="drp:door_open"/>
                        </door-state>
                    </dms-door>
                   </xsl:for-each>
                   </door-status-list>
                </xsl:if>

                        <xsl:if
                                test="count(./drp:drip_status_attr/drp:error_details/drp:error) &gt; 0 ">
                            <error-status>
                                <error>
                                    <xsl:value-of
                                            select="./drp:drip_status_attr/drp:error_details/drp:error[position()=1]/drp:error_description"/>
                                </error>
                            </error-status>
                        </xsl:if>

                        <test-result>
                            <!--<heater-circuit-failed>false</heater-circuit-failed>-->
                            <!--<internal-communication-failed>false-->
                            <!--</internal-communication-failed>-->
                            <!--<power-supply-failed>false</power-supply-failed>-->
                            <!--<watchdog-reset>false</watchdog-reset>-->
                            <!--<high-temperature>false</high-temperature>-->
                            <!--Optional: -->
                            <selftest-start-time>
                                <xsl:value-of
                                        select="fi:substring(./drp:drip_status_attr/drp:other_information/drp:selftest_start_time,1,10)"/>
                            </selftest-start-time>
                            <!--Optional: -->
                            <selftest-end-time>
                                <xsl:value-of
                                        select="fi:substring(./drp:drip_status_attr/drp:other_information/drp:selftest_end_time,1,10)"/>
                            </selftest-end-time>
                            <!--Optional: -->
                            <watchdog-reset-counter>
                                <xsl:value-of
                                        select="./drp:drip_status_attr/drp:other_information/drp:watchdog_reset_counter"/>
                            </watchdog-reset-counter>
                        </test-result>
                    </dms-status-attr>
                </dms-status-item>
            </xsl:for-each>
        </tmdd:dMSStatusUpdateMsg>
    </xsl:template>
</xsl:stylesheet>