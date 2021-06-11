<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:prism="http://prism.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />

	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/prism:prism_info_reply">
		<xsl:choose>
			<xsl:when test="prism:prism_publish">
				<tmdd:dMSInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
					<xsl:apply-templates select="prism:prism_publish" />
				</tmdd:dMSInventoryMsg>
			</xsl:when>
			<xsl:otherwise>
				<tmdd:dMSInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
					<dms-inventory-item xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="tmdd:DMSInventoryCharm">
						<device-inventory-header
							xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="tmdd:DeviceInventoryHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>0</device-id>
							<device-name>dummy</device-name>
						</device-inventory-header>
					</dms-inventory-item>
				</tmdd:dMSInventoryMsg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="prism:prism_publish">
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
					<xsl:value-of select="prism:prism_id/prism:id" />
				</device-id>
				<xsl:choose>
					<xsl:when test="prism:prism_location_attr">
						<device-location>
							<latitude>
								<xsl:value-of
									select="prism:prism_location_attr/prism:position_wgs84/prism:latitude">
								</xsl:value-of>
							</latitude>
							<longitude>
								<xsl:value-of
									select="prism:prism_location_attr/prism:position_wgs84/prism:longitude">
								</xsl:value-of>
							</longitude>
						</device-location>
					</xsl:when>
					<xsl:otherwise>
						<device-location>
							<latitude>0.0</latitude>
							<longitude>0.0</longitude>
						</device-location>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="prism:prism_inventory_attr">
						<device-name>
							<xsl:value-of select="prism:prism_inventory_attr/prism:name" />
						</device-name>
						<device-description>
							<xsl:value-of select="prism:prism_inventory_attr/prism:description" />
						</device-description>

						<device-type>cms sign</device-type>

						<scan-on-off>
							<xsl:value-of select="prism:prism_inventory_attr/prism:scan_enabled" />
						</scan-on-off>
						<scan-interval>
							<xsl:value-of select="prism:prism_inventory_attr/prism:scan_interval" />
						</scan-interval>
						<inventory-action>add</inventory-action>
						<protocol>udp-1.1</protocol>
						<hostname>10.2.2.2</hostname>
						<port>3333</port>
						<response-timeout>2000</response-timeout>
						<command-timeout>1000</command-timeout>
						<parent-device-id>
							<xsl:value-of select="prism:prism_inventory_attr/prism:fep_id/prism:id" />
						</parent-device-id>
						<parent-device-id2>
							<xsl:value-of
								select="prism:prism_inventory_attr/prism:outstation_id/prism:id" />
						</parent-device-id2>
						<station-line-number>
							<xsl:value-of select="prism:prism_inventory_attr/prism:os_line_number" />
						</station-line-number>
						<station-line-position>
							<xsl:value-of select="prism:prism_inventory_attr/prism:os_line_position" />
						</station-line-position>
						<fep-lines>
							<xsl:value-of select="prism:prism_inventory_attr/prism:identifier" />
						</fep-lines>
						<xsl:if test="prism:prism_location_attr/prism:position_bps">
							<position-bps-code>
								<xsl:value-of select="prism:prism_location_attr/prism:position_bps" />
							</position-bps-code>
						</xsl:if>

						<xsl:if test="prism:prism_inventory_attr/prism:owner">
							<data-owner>
								<xsl:value-of select="prism:prism_inventory_attr/prism:owner" />
							</data-owner>
						</xsl:if>

					</xsl:when>
					<xsl:otherwise>
						<device-name>unknown</device-name>
						<device-type>cms sign</device-type>
						<scan-on-off>false</scan-on-off>
						<scan-interval>0</scan-interval>
						<inventory-action>add</inventory-action>
						<protocol>udp-1.1</protocol>
						<hostname>0</hostname>
						<port>0</port>
						<response-timeout>0</response-timeout>
						<command-timeout>0</command-timeout>
					</xsl:otherwise>
				</xsl:choose>
			</device-inventory-header>
			<dms-sign-type>variable message sign VMS</dms-sign-type>
			<xsl:if test="prism:prism_inventory_attr">
				<prism-type-id>
					<xsl:value-of
						select="prism:prism_inventory_attr/prism:prism_type_id/prism:id" />
				</prism-type-id>
			</xsl:if>
		</dms-inventory-item>
	</xsl:template>
</xsl:stylesheet>
	