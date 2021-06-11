<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tlc="http://tlc.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="multi_message" />
	<xsl:param name="organizationId" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/tlc:tlc_info_reply">
		<xsl:choose>
			<xsl:when test="tlc:tlc_publish">
				<tmdd:genericDeviceInventoryMsg>
					<xsl:apply-templates select="tlc:tlc_publish" />
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

	<xsl:template match="tlc:tlc_publish">
		<dms-inventory-item xsi:type="tmdd:DMSInventoryCharm">
			<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organizationId" />
					</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="tlc:tlc_id/tlc:id" />
				</device-id>
				<device-location>
					<latitude>
						<xsl:value-of
							select="tlc:tlc_inventory_attr/tlc:tlc_location_attr/tlc:position_wgs84/tlc:latitude" />
					</latitude>
					<longitude>
						<xsl:value-of
							select="tlc:tlc_inventory_attr/tlc:tlc_location_attr/tlc:position_wgs84/tlc:longitude" />
					</longitude>
				</device-location>
				<device-name>
					<xsl:value-of select="tlc:tlc_inventory_attr/tlc:name" />
				</device-name>
				<xsl:if test="tlc:tlc_inventory_attr/tlc:description">
					<device-description>
						<xsl:value-of select="tlc:tlc_inventory_attr/tlc:description" />
					</device-description>
				</xsl:if>
				<device-url>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_communication/tlc:pin_code" />
				</device-url>
				<device-type>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_generic_attr/tlc:tlc_type" />
				</device-type>
				<scan-on-off>
					<xsl:value-of select="tlc:tlc_inventory_attr/tlc:scan_enabled" />
				</scan-on-off>
				<scan-interval>
					<xsl:value-of select="tlc:tlc_inventory_attr/tlc:scan_interval" />
				</scan-interval>
				<hostname>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_communication/tlc:ipnr_vri" />
				</hostname>
				<port>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_communication/tlc:portnumber_client" />
				</port>
				<fep-lines>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_communication/tlc:portnumber_server_asb" />
				</fep-lines>
				<position-bps-code>
					<xsl:value-of
						select="tlc:tlc_inventory_attr/tlc:tlc_location_attr/tlc:position_bps" />
				</position-bps-code>
			</device-inventory-header>
		</dms-inventory-item>
	</xsl:template>
</xsl:stylesheet>