<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis"/>
	<xsl:variable name="message_create_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>
	<xsl:template match="/">
		<prism_inventory_publish xmlns="http://prism.cdm.charm.cgi.com" version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_create_date"/>
				</message_create_date>
			</meta>
			<!-- Need to Change the subscription_id to outside meta -->
				<subscription_id>
				<uuid>
					<xsl:value-of
						select="tmdd:dMSInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>
			<inventory_config>
				<xsl:choose>
					<xsl:when
						test="tmdd:dMSInventoryUpdateMsg/c2cMsgAdmin/informationalText = 'FULLSYNC'">
						<full_sync>true</full_sync>
					</xsl:when>
					<xsl:otherwise>
						<full_sync>false</full_sync>
					</xsl:otherwise>
				</xsl:choose>
				<inventory_id>
					<uuid>
						<xsl:value-of
							select="tmdd:dMSInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
					</uuid>
				</inventory_id>
				<xsl:for-each select="tmdd:dMSInventoryUpdateMsg/dms-inventory-item">
					<prism_inventory>
						<action>
							<xsl:value-of
								select="device-inventory-header/inventory-action" />
						</action>
						<prism_id>
							<id>
								<xsl:value-of
									select="device-inventory-header/device-id" />
							</id>
						</prism_id>
						<prism_inventory_attr>
							<name>
								<xsl:value-of
									select="device-inventory-header/device-name" />
							</name>
							<description>
								<xsl:value-of
									select="device-inventory-header/device-description" />
							</description>
							<scan_enabled>
								<xsl:value-of select="device-inventory-header/scan-on-off" />
							</scan_enabled>
							<scan_interval>
								<xsl:value-of select="device-inventory-header/scan-interval" />
							</scan_interval>
							<prism_type_id>
								<id>
									<xsl:value-of select="prism-type-id" />
								</id>
							</prism_type_id>
							<fep_id>
								<id>
									<xsl:value-of
										select="device-inventory-header/parent-device-id2" />
								</id>
							</fep_id>
							<outstation_id>
								<id>
									<xsl:value-of
										select="device-inventory-header/parent-device-id" />
								</id>
							</outstation_id>
							<os_line_number>
								<xsl:value-of select="device-inventory-header/station-line-number" />
							</os_line_number>
							<os_line_position>
								<xsl:value-of select="device-inventory-header/station-line-position" />
							</os_line_position>
							<identifier>1</identifier>
							<xsl:if test="device-inventory-header/data-owner">
								<owner>
									<xsl:value-of select="device-inventory-header/data-owner"/>
								</owner>
							</xsl:if>
						</prism_inventory_attr>
						<prism_location_attr>
							<xsl:if test="device-inventory-header/position-bps-code">
									<position_bps>
										<xsl:value-of select="device-inventory-header/position-bps-code" />
									</position_bps>
								</xsl:if>
							<position_wgs84>
								<latitude>
									<xsl:value-of
										select="device-inventory-header/device-location/latitude" />
								</latitude>
								<longitude>
									<xsl:value-of
										select="device-inventory-header/device-location/longitude" />
								</longitude>
							</position_wgs84>
						</prism_location_attr>
					</prism_inventory>
				</xsl:for-each>
			</inventory_config>
		</prism_inventory_publish>
	</xsl:template>
</xsl:stylesheet>