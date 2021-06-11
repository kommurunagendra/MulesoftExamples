<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />

	<xsl:variable name="message_create_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />

	<xsl:template match="/tmdd:dMSInventoryUpdateMsg">
		<msi_inventory_publish xmlns="http://msi.cdm.charm.cgi.com"
			version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_create_date" />
				</message_create_date>
			</meta>
			<subscription_id>
				<uuid>
					<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>

			<inventory_config>
				<xsl:choose>
					<xsl:when test="c2cMsgAdmin/informationalText = 'FULLSYNC'">
						<full_sync>true</full_sync>
					</xsl:when>
					<xsl:otherwise>
						<full_sync>false</full_sync>
					</xsl:otherwise>
				</xsl:choose>
				<inventory_id>
					<uuid>
						<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
					</uuid>
				</inventory_id>

				<xsl:apply-templates select="dms-inventory-item" />
			</inventory_config>
		</msi_inventory_publish>
	</xsl:template>

	<xsl:template match="dms-inventory-item">
		<msi_inventory xmlns="http://msi.cdm.charm.cgi.com">
			<action>
				<xsl:value-of select="device-inventory-header/inventory-action" />
			</action>
			<msi_id>
				<id>
					<xsl:value-of select="device-inventory-header/device-id" />
				</id>
			</msi_id>
			<msi_inventory_attr>
				<name>
					<xsl:value-of select="device-inventory-header/device-name" />
				</name>
				<description>
					<xsl:value-of select="device-inventory-header/device-description" />
				</description>
				<!-- As per data mapping sheet value has been replaced with below string -->
				<scan_enabled>
					<xsl:value-of select="device-inventory-header/scan-on-off" />
				</scan_enabled>
				<scan_interval>
					<xsl:value-of select="device-inventory-header/scan-interval" />
				</scan_interval>
				<fep_id>
					<id>
						<xsl:value-of select="device-inventory-header/parent-device-id2" />
					</id>
				</fep_id>
				<outstation_id>
					<id>
						<xsl:value-of select="device-inventory-header/parent-device-id" />
					</id>
				</outstation_id>
				<os_line_number>
					<xsl:value-of select="device-inventory-header/station-line-number" />
				</os_line_number>
				<os_line_position>
					<xsl:value-of select="device-inventory-header/station-line-position" />
				</os_line_position>
				<identifier>
					<xsl:value-of select="device-inventory-header/fep-lines" />
				</identifier>
				<xsl:if test="device-inventory-header/data-owner">
					<owner>
						<xsl:value-of select="device-inventory-header/data-owner"/>
					</owner>
				</xsl:if>
			</msi_inventory_attr>
			<msi_location_attr>
				<xsl:if test="device-inventory-header/position-bps-code">
					<position_bps>
						<xsl:value-of select="device-inventory-header/position-bps-code" />
					</position_bps>
				</xsl:if>
				<position_wgs84>
					<latitude>
						<xsl:value-of select="device-inventory-header/device-location/latitude" />
					</latitude>
					<longitude>
						<xsl:value-of select="device-inventory-header/device-location/longitude" />
					</longitude>
				</position_wgs84>
			</msi_location_attr>
		</msi_inventory>
	</xsl:template>
</xsl:stylesheet>