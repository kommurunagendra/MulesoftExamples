<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:brg="http://bridge.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />

	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/brg:bridge_info_reply">
		<tmdd:gateInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">

			<xsl:if test="brg:bridge_publish">
				<xsl:for-each select="brg:bridge_publish">

					<gate-inventory-item>
						<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
							<organization-information>
								<organization-id>RWS</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of select="brg:bridge_id/brg:id" />
							</device-id>
							<device-location>
								<latitude>
									<xsl:value-of
										select="brg:bridge_location_attr/brg:position_wgs84/brg:latitude" />
								</latitude>
								<longitude>
									<xsl:value-of
										select="brg:bridge_location_attr/brg:position_wgs84/brg:longitude" />
								</longitude>
							</device-location>
							<device-name>
								<xsl:value-of select="brg:bridge_inventory_attr/brg:name" />
							</device-name>
							<xsl:if test="brg:bridge_inventory_attr/brg:description">
								<device-description>
									<xsl:value-of select="brg:bridge_inventory_attr/brg:description" />
								</device-description>
							</xsl:if>
							
							<device-type>bridge</device-type>
							<scan-on-off>
								<xsl:value-of select="brg:bridge_inventory_attr/brg:scan_enabled" />
							</scan-on-off>
							<scan-interval>
								<xsl:value-of select="brg:bridge_inventory_attr/brg:scan_interval" />
							</scan-interval>
						</device-inventory-header>
					</gate-inventory-item>
				</xsl:for-each>
			</xsl:if>

			<xsl:if test="not(brg:bridge_publish)">
				<gate-inventory-item>
					<device-inventory-header xsi:type="tmdd:DeviceInventoryHeaderCharm">
						<organization-information>
							<organization-id>RWS</organization-id>
						</organization-information>
						<device-id>dummy</device-id>
						<device-name>dummy</device-name>
						<device-type>bridge</device-type>

					</device-inventory-header>
				</gate-inventory-item>
			</xsl:if>
		</tmdd:gateInventoryMsg>
	</xsl:template>
</xsl:stylesheet>