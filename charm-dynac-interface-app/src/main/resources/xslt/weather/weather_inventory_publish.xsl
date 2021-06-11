<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" 
	xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />

	<xsl:variable name="message_create_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />

	<xsl:template match="/tmdd:eSSInventoryUpdateMsg">
		<weather_inventory_publish xmlns="http://weather.cdm.charm.cgi.com" version="1.0.0">
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
				<xsl:apply-templates select="ess-inventory-item" />
			</inventory_config>
		</weather_inventory_publish>
	</xsl:template>

	<xsl:template match="ess-inventory-item">
		<weather_inventory xmlns="http://weather.cdm.charm.cgi.com">
			<action>
				<xsl:value-of select="device-inventory-header/inventory-action" />
			</action>
			<weather_id>
				<id>
					<xsl:value-of select="device-inventory-header/device-id" />
				</id>
			</weather_id>
			<weather_inventory_attr>
				<name>
					<xsl:value-of select="device-inventory-header/device-name" />
				</name>
				<description>
					<xsl:value-of select="device-inventory-header/device-description" />
				</description>
				<address>
					<xsl:value-of select="device-inventory-header/hostname" />
				</address>
				<port>
					<xsl:value-of select="device-inventory-header/port" />
				</port>
				<path>
					<xsl:value-of select="device-inventory-header/file-name-path" />
				</path>
				<username>
					<xsl:value-of select="device-inventory-header/authentication/user-id" />
				</username>
				<password>
					<xsl:value-of select="device-inventory-header/authentication/password" />
				</password>
				<scan_enabled>
					<xsl:value-of select="device-inventory-header/scan-on-off" />
				</scan_enabled>
				<scan_interval>
					<xsl:value-of select="device-inventory-header/scan-interval" />
				</scan_interval>
			</weather_inventory_attr>
		</weather_inventory>
	</xsl:template>
</xsl:stylesheet>