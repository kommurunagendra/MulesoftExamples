<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:wind="http://wind.cdm.charm.cgi.com"
                xmlns:xls="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                exclude-result-prefixes="#all">

    <xsl:template match="wind:wps_publish">
        <generic-status-item>
            <device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
                <organization-information>
                    <organization-id>RWS</organization-id>
                </organization-information>
                <device-id>
                     <xsl:value-of select="wind:wps_id/wind:id"/>
                </device-id>
                <device-status>on</device-status>
                <device-type>wind sensor</device-type>
            </device-status-header>
           
              <generic-device-list>
                <xsl:if test="wind:wps_status_attr">
                	 <!-- map status values -->
                	<xsl:if test="wind:wps_status_attr/wind:status_attr">
                    <generic-object>
                        <object-name>status</object-name>
                        <generic-element-list>                        
                            <generic-element>
                                <element-name>state</element-name>
                                <element-value>
                                    <xsl:value-of
                                            select="wind:wps_status_attr/wind:status_attr/wind:state"/>
                                </element-value>
                            </generic-element>                          
                        </generic-element-list>
                    </generic-object>
                    </xsl:if>
                    
                    <!-- map advice values -->
                    <xsl:if test="wind:wps_status_attr/wind:advice_attr">
                    <generic-object>
                        <object-name>advice</object-name>
                        <generic-element-list>
                            <generic-element>
                                <element-name>time</element-name>
                                <element-value><xsl:value-of select="wind:wps_status_attr/wind:advice_attr/wind:time"/></element-value>
                            </generic-element>
                            <generic-element>
                                <element-name><xsl:value-of select="wind:wps_status_attr/wind:advice_attr/wind:group"/></element-name>
                                <element-value><xsl:value-of select="wind:wps_status_attr/wind:advice_attr/wind:value"/></element-value>
                            </generic-element>           
                        </generic-element-list>
                    </generic-object>
             		</xsl:if>
                </xsl:if>
            </generic-device-list>
        </generic-status-item>
    </xsl:template>
</xsl:stylesheet>