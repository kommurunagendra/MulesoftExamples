<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:drip="http://drip.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/drip:drip_info_reply">
		<tmdd:dMSInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
			<xsl:if test="drip:drip_publish">
				<xsl:for-each select="drip:drip_publish">
					<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm">
						<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of select="drip:drip_id/drip:id" />
							</device-id>
							<device-location>
								<latitude>
									<xsl:value-of
										select="drip:drip_location_attr/drip:position_wgs84/drip:latitude" />
								</latitude>
								<longitude>
									<xsl:value-of
										select="drip:drip_location_attr/drip:position_wgs84/drip:longitude" />
								</longitude>
							</device-location>
							<device-name>
								<xsl:value-of select="drip:drip_inventory_attr/drip:name" />
							</device-name>
							<xsl:if test="drip:drip_inventory_attr/drip:description">
								<device-description>
									<xsl:value-of select="drip:drip_inventory_attr/drip:description" />
								</device-description>
							</xsl:if>
							<device-type>text dms</device-type>
							<scan-on-off>
								<xsl:value-of select="drip:drip_inventory_attr/drip:scan_enabled" />
							</scan-on-off>
							<scan-interval>
								<xsl:value-of select="drip:drip_inventory_attr/drip:scan_interval" />
							</scan-interval>
							<protocol>
								<xsl:value-of select="drip:drip_inventory_attr/drip:protocol" />
							</protocol>
							<hostname>
								<xsl:value-of select="drip:drip_inventory_attr/drip:hostname" />
							</hostname>
							<port>
								<xsl:value-of select="drip:drip_inventory_attr/drip:port" />
							</port>
							<response-timeout>
								<xsl:value-of select="drip:drip_inventory_attr/drip:response_timeout" />
							</response-timeout>
							<command-timeout>
								<xsl:value-of select="drip:drip_inventory_attr/drip:command_timeout" />
							</command-timeout>
						</device-inventory-header>
						<position-bps>
							<xsl:value-of select="drip:drip_location_attr/drip:position_bps" />
						</position-bps>
						<physical-mounting>
							<xsl:value-of
								select="fi:replace(drip:drip_inventory_attr/drip:physical_mounting,'Mounted','')" />
						</physical-mounting>
						<vms-type>
							<xsl:value-of select="drip:drip_inventory_attr/drip:vms_type" />
						</vms-type>
					</dms-inventory-item>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="not(drip:drip_publish)">
				<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm">
					<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
						<organization-information>
							<organization-id>
								<xsl:value-of select="$organization_id" />
							</organization-id>
						</organization-information>
						<device-id>unknown</device-id>
						<device-name>unknown</device-name>
					</device-inventory-header>
				</dms-inventory-item>
			</xsl:if>
		</tmdd:dMSInventoryMsg>
	</xsl:template>
</xsl:stylesheet>