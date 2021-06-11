<xsl:stylesheet version="2.0"
                xmlns:fi="http://www.w3.org/2005/xpath-functions"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="multi_message"/>
    <xsl:param name="organization_id"/>
    <xsl:include href="../util/date_conversion.xsl"/>

    <xsl:template match="/bdrp:bermdrip_info_reply">
	<tmdd:dMSInventoryMsg>
		<xsl:if test="bdrp:bermdrip_publish">
			<xsl:apply-templates select="bdrp:bermdrip_publish" />
		</xsl:if>

		<xsl:if test="not(bdrp:bermdrip_publish)">

			<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm">
				<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<device-id>dummy</device-id>
					<device-name>dummy</device-name>
				</device-inventory-header>
			</dms-inventory-item>

		</xsl:if>
	</tmdd:dMSInventoryMsg>
</xsl:template>

    <xsl:template match="bdrp:bermdrip_publish">
        <dms-inventory-item xsi:type="tmdd:DMSInventoryCharm">
            <device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
                <organization-information>
                    <organization-id>
                        <xsl:value-of select="$organization_id"/>
                    </organization-id>
                </organization-information>
                <device-id>
                    <xsl:value-of select="bdrp:bermdrip_id/bdrp:id"/>
                </device-id>
                <device-location>
                    <latitude>
                        <xsl:value-of
                                select="bdrp:bermdrip_location_attr/bdrp:position_wgs84/bdrp:latitude"/>
                    </latitude>
                    <longitude>
                        <xsl:value-of
                                select="bdrp:bermdrip_location_attr/bdrp:position_wgs84/bdrp:longitude"/>
                    </longitude>
                </device-location>
                <device-name>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:name"/>
                </device-name>
                <device-description>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:description"/>
                </device-description>
                <device-type>graphical dms</device-type>
                <scan-on-off>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:scan_enabled"/>
                </scan-on-off>
                <scan-interval>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:scan_interval"/>
                </scan-interval>
                <inventory-action>add</inventory-action>
                <protocol>
                    <xsl:choose>
                        <xsl:when
                                test="starts-with(bdrp:bermdrip_inventory_attr/bdrp:protocol,'tcp-2')">
                            <xsl:text>tcp-2.1</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text>tcp-1.1</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
                </protocol>
                <hostname>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:hostname"/>
                </hostname>
                <port>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:port"/>
                </port>
                <response-timeout>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:response_timeout"/>
                </response-timeout>
                <command-timeout>
                    <xsl:value-of
                            select="bdrp:bermdrip_inventory_attr/bdrp:command_timeout"/>
                </command-timeout>
              <position-bps-code>
                <xsl:value-of select="bdrp:bermdrip_location_attr/bdrp:position_bps"/>
            </position-bps-code>
            </device-inventory-header>
            <dms-sign-type>
                <xsl:value-of
                        select="bdrp:bermdrip_inventory_attr/bdrp:dms_sign_type"/>
            </dms-sign-type>

            <physical-mounting>
                <xsl:value-of select="fi:replace(bdrp:bermdrip_inventory_attr/bdrp:physical_mounting,'Mounted','')"/>
            </physical-mounting>
            <vms-type>
                <xsl:value-of select="bdrp:bermdrip_inventory_attr/bdrp:vms_type"/>
            </vms-type>
        </dms-inventory-item>
    </xsl:template>
</xsl:stylesheet>