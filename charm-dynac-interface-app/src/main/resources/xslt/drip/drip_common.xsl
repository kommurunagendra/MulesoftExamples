<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:drp="http://drip.cdm.charm.cgi.com"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                exclude-result-prefixes="#all">
    <xsl:strip-space elements="*"/>
    <xsl:template match="drp:drip_publish">
        <xsl:variable name="id" select="drp:drip_id/drp:id"/>

        <dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
            <device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
                <organization-information>
                    <organization-id>RWS</organization-id>
                </organization-information>
                <device-id>
                    <xsl:value-of select="$id"/>
                </device-id>
                <device-status>
                    <xsl:value-of
                            select="drp:drip_status_attr/drp:general_status/drp:device_status"/>
                </device-status>
                <device-comm-status>
                    <xsl:value-of
                            select="drp:drip_status_attr/drp:general_status/drp:device_comm_status"/>
                </device-comm-status>
                <device-type>text dms</device-type>
            </device-status-header>
            <current-message> 
            
            <xsl:value-of select="fi:concat(fi:normalize-space(drp:drip_image_attr/drp:duration),fi:normalize-space(drp:drip_image_attr/drp:text/drp:line/drp:text_alignment),fi:normalize-space(drp:drip_image_attr/drp:text/drp:line/drp:text))"/> </current-message>
               <!--  <xsl:value-of
                        select="drp:drip_image_attr/drp:text/drp:line/drp:text_alignment"/>
                <xsl:value-of
                        select="drp:drip_image_attr/drp:text/drp:line/drp:text"/> 
            </current-message> -->

            <dms-control-attr>
                <operation-mode>
                    <xsl:value-of
                            select="drp:drip_control_attr/drp:operation_mode"/>
                </operation-mode>
                <dim-mode>
                    <xsl:value-of
                            select="drp:drip_control_attr/drp:dim_status/drp:dim_mode"/>
                </dim-mode>
                <dim-status>
                    <xsl:value-of
                            select="drp:drip_control_attr/drp:dim_status/drp:dim_value"/>
                </dim-status>
            </dms-control-attr>


            <dms-status-attr>
                <climate-status>
                    <xsl:value-of
                            select="drp:drip_status_attr/drp:general_status/drp:climate_mode"/>
                </climate-status>

                <xsl:choose>
                    <xsl:when test="drp:drip_status_attr/drp:display_status">
                        <display-status>
                            <display-mode><xsl:value-of select="fi:normalize-space(drp:drip_status_attr/drp:display_status/drp:display_mode)"/>
                            </display-mode>
                            <display-state>
                                <xsl:value-of
                                        select="drp:drip_status_attr/drp:display_status/drp:display_status"/>
                            </display-state>
                            <flasher-state>
                                <xsl:value-of
                                        select="drp:drip_status_attr/drp:display_status/drp:flasher_status"/>
                            </flasher-state>
                            <pictogram-state>
                                <xsl:value-of
                                        select="drp:drip_status_attr/drp:display_status/drp:pictogram_status"/>
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
                                select="drp:drip_status_attr/drp:other_information/drp:drip_type"/>
                    </drip-type>
                    <serial-number>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:serial_number"/>
                    </serial-number>
                    <drip-version>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:drip_version"/>
                    </drip-version>
                    <location-code>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:location_code"/>
                    </location-code>
                    <protocol-config>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:protocol_version"/>
                    </protocol-config>
                    <font-name>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:font_name"/>
                    </font-name>
                    <regel-config>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:regel_config"/>
                    </regel-config>
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
                        test="count(drp:drip_status_attr/drp:error_details/drp:error) &gt; 0 ">
                    <error-status>
                        <error>
                            <xsl:value-of
                                    select="drp:drip_status_attr/drp:error_details/drp:error[position()=1]/drp:error_description"/>
                        </error>
                    </error-status>
                </xsl:if>

                <test-result>
                    <heater-circuit-failed>false</heater-circuit-failed>
                    <internal-communication-failed>false
                    </internal-communication-failed>
                    <power-supply-failed>false</power-supply-failed>
                    <watchdog-reset>false</watchdog-reset>
                    <high-temperature>false</high-temperature>
                    <!--Optional: -->
                    <selftest-start-time>
                        <xsl:value-of
                                select="fi:substring(drp:drip_status_attr/drp:other_information/drp:selftest_start_time,1,10)"/>
                    </selftest-start-time>
                    <!--Optional: -->
                    <selftest-end-time>
                        <xsl:value-of
                                select="fi:substring(drp:drip_status_attr/drp:other_information/drp:selftest_end_time,1,10)"/>
                    </selftest-end-time>
                    <!--Optional: -->
                    <watchdog-reset-counter>
                        <xsl:value-of
                                select="drp:drip_status_attr/drp:other_information/drp:watchdog_reset_counter"/>
                    </watchdog-reset-counter>
                </test-result>
            </dms-status-attr>
        </dms-status-item>
    </xsl:template>

</xsl:stylesheet>