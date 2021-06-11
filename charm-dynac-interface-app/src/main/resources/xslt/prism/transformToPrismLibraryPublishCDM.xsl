<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />

	<xsl:variable name="message_created_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />

	<xsl:template match="/">
		<prism_inventory_publish xmlns="http://prism.cdm.charm.cgi.com"
			version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of
							select="tmdd:dMSMessageInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_created_date"></xsl:value-of>
				</message_create_date>
			</meta>
			<subscription_id>
				<uuid>
					<xsl:value-of
						select="tmdd:dMSMessageInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>
			<inventory_config>
				<xsl:choose>
					<xsl:when
						test="tmdd:dMSMessageInventoryUpdateMsg/c2cMsgAdmin/informationalText = 'FULLSYNC'">
						<full_sync>true</full_sync>
					</xsl:when>
					<xsl:otherwise>
						<full_sync>false</full_sync>
					</xsl:otherwise>
				</xsl:choose>
				<inventory_id>
					<uuid>
						<xsl:value-of
							select="tmdd:dMSMessageInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
					</uuid>
				</inventory_id>
				<xsl:for-each-group
					select="tmdd:dMSMessageInventoryUpdateMsg/dms-message-inventory-item"
					group-by="device-id">
					<prism_library>
						<action>
							<xsl:value-of select="inventory-action" />
						</action>
						<prism_type_id>
							<id>
								<xsl:value-of select="current-grouping-key()" />
							</id>
						</prism_type_id>
						<prism_type_description>
							<xsl:value-of select="description" />
						</prism_type_description>

						<xsl:for-each select="current-group()">
							<prism_state>
								<prism_state_nr>
									<xsl:value-of select="message-number" />
								</prism_state_nr>
								<prism_state_mnemonic>
									<xsl:value-of select="message" />
								</prism_state_mnemonic>
							</prism_state>
						</xsl:for-each>
					</prism_library>
				</xsl:for-each-group>
			</inventory_config>
		</prism_inventory_publish>
	</xsl:template>
</xsl:stylesheet>
