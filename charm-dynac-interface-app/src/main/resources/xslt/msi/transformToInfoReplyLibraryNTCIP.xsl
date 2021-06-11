<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msi="http://msi.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />

	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/msi:msi_info_reply">
		<tmdd:dMSInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
			<xsl:apply-templates select="msi:msi_publish" />
		</tmdd:dMSInventoryMsg>
	</xsl:template>

	<xsl:template match="msi:msi_publish">
		<dms-inventory-item xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:type="tmdd:DMSInventoryCharm">
			<device-inventory-header xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:type="tmdd:DeviceInventoryHeaderCharm">
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organization_id" />
					</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="msi:msi_id/msi:id" />
				</device-id>
				<device-location>
					<xsl:if test="msi:msi_location_attr">
						<latitude>
							<xsl:value-of
								select="msi:msi_location_attr/msi:position_wgs84/msi:latitude" />
						</latitude>
						<longitude>
							<xsl:value-of
								select="msi:msi_location_attr/msi:position_wgs84/msi:longitude" />
						</longitude>
					</xsl:if>
				</device-location>
				<xsl:if test="msi:msi_inventory_attr">
					<device-name>
						<xsl:value-of select="msi:msi_inventory_attr/msi:name" />
					</device-name>
					<xsl:if test="msi:msi_inventory_attr/msi:description">
						<device-description>
							<xsl:value-of select="msi:msi_inventory_attr/msi:description" />
						</device-description>
					</xsl:if>

					<device-type>msi</device-type>
					<scan-on-off>
						<xsl:value-of select="msi:msi_inventory_attr/msi:scan_enabled" />
					</scan-on-off>
					<scan-interval>
						<xsl:value-of select="msi:msi_inventory_attr/msi:scan_interval" />
					</scan-interval>
		<!-- 			<inventory-action>add</inventory-action>
					<protocol>udp-1.1</protocol>
					<hostname>dummy</hostname>
					<port><xsl:text>0</xsl:text></port>
					<response-timeout>0</response-timeout>
					<command-timeout>0</command-timeout> -->

					<parent-device-id>
						<xsl:value-of select="msi:msi_inventory_attr/msi:outstation_id/msi:id" />
					</parent-device-id>
					<parent-device-id2>
						<xsl:value-of select="msi:msi_inventory_attr/msi:fep_id/msi:id" />
					</parent-device-id2>
					<station-line-number>
						<xsl:value-of select="msi:msi_inventory_attr/msi:os_line_number" />
					</station-line-number>
					<station-line-position>
						<xsl:value-of select="msi:msi_inventory_attr/msi:os_line_position" />
					</station-line-position>
					<fep-lines>
						<xsl:value-of select="msi:msi_inventory_attr/msi:identifier" />
					</fep-lines>
					<data-owner>dynac</data-owner>
				</xsl:if>

			</device-inventory-header>
			<xsl:if test="msi:msi_location_attr/msi:position_bps">
			<position-bps><xsl:value-of select="msi:msi_location_attr/msi:position_bps" /></position-bps>
			</xsl:if>
		</dms-inventory-item>
	</xsl:template>
</xsl:stylesheet>