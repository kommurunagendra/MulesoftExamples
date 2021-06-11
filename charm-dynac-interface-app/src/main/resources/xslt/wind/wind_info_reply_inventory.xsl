<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wind="http://wind.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="multi_message" />
	<xsl:param name="organizationId" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/wind:wind_info_reply">
		<xsl:choose>
			<xsl:when test="wind:wps_publish">
				<tmdd:genericDeviceInventoryMsg
					xmlns:tmdd="http://www.tmdd.org/3/messages">
					<xsl:apply-templates select="wind:wps_publish" />
				</tmdd:genericDeviceInventoryMsg>
			</xsl:when>
			<xsl:otherwise>
				<tmdd:genericDeviceInventoryMsg
					xmlns:tmdd="http://www.tmdd.org/3/messages">
					<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
						<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organizationId" />
								</organization-id>
							</organization-information>
							<device-id>dummy</device-id>
							<device-name>dummy</device-name>
						</device-inventory-header>
					</dms-inventory-item>
				</tmdd:genericDeviceInventoryMsg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="wind:wps_publish">
		<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organizationId" />
					</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="wind:wps_id/wind:id" />
				</device-id>
				<device-location>
					<latitude>
						<xsl:value-of
							select="wind:wps_location_attr/wind:position_wgs84/wind:latitude" />
					</latitude>
					<longitude>
						<xsl:value-of
							select="wind:wps_location_attr/wind:position_wgs84/wind:longitude" />
					</longitude>
				</device-location>
				<device-name>
					<xsl:value-of select="wind:wps_inventory_attr/wind:name" />
				</device-name>
				<xsl:if test="wind:wps_inventory_attr/wind:description">
					<device-description>
						<xsl:value-of select="wind:wps_inventory_attr/wind:description" />
					</device-description>
				</xsl:if>
				<device-type>wind sensor</device-type>
				<scan-on-off>
					<xsl:value-of select="wind:wps_inventory_attr/wind:scan_enabled" />
				</scan-on-off>

				<hostname>
					<xsl:value-of select="wind:wps_inventory_attr/wind:address" />
				</hostname>
				<port>
					<xsl:value-of select="wind:wps_inventory_attr/wind:port" />
				</port>

			</device-inventory-header>
			<position-bps>
				<xsl:value-of select="wind:wps_location_attr/wind:position_bps" />
			</position-bps>
		</dms-inventory-item>
	</xsl:template>
</xsl:stylesheet>