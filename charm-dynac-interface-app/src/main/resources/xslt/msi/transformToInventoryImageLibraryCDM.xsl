<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />
	
		<xsl:variable name="message_create_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />
	
	
	<xsl:template match="/tmdd:dMSMessageInventoryUpdateMsg">
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
					<xsl:value-of
						select="c2cMsgAdmin/subscriptionID" />
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
				<xsl:for-each
					select="dms-message-inventory-item">
					<msi_image_library>
						<action>
							<xsl:value-of select="inventory-action" />
						</action>
						<image_id>
							<id>
								<xsl:value-of select="device-id" />
							</id>
						</image_id>
						<image_mnemonic>
							<xsl:value-of select="message" />
						</image_mnemonic>
						<image_description>
							<xsl:value-of select="description" />
						</image_description>
						<image>
								<xsl:choose>
								<xsl:when test="image-codes/group-code">
									<group_code>
										<xsl:value-of select="image-codes/group-code" />
									</group_code>
									</xsl:when>
									<xsl:otherwise><group_code>0</group_code></xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
								<xsl:when test="image-codes/legend-code">
									<legend_code>
										<xsl:value-of select="image-codes/legend-code" />
									</legend_code>
									</xsl:when>
									<xsl:otherwise><legend_code>0</legend_code></xsl:otherwise>
									</xsl:choose>
								<xsl:choose>
								<xsl:when test="image-codes/variation-code">
									<variation_code>
										<xsl:value-of select="image-codes/variation-code" />
									</variation_code>									
									</xsl:when>
									<xsl:otherwise><variation_code>0</variation_code></xsl:otherwise>
								</xsl:choose>
								<xsl:if test="image-codes/msi-nibble">
									<msi_nibble>
										<xsl:value-of select="image-codes/msi-nibble" />
									</msi_nibble>
								</xsl:if>
								<xsl:if test="image-codes/flasher-byte">
									<flasher_byte>
										<xsl:value-of select="image-codes/fasher-byte" />
									</flasher_byte>
								</xsl:if>
						</image>
					</msi_image_library>
				</xsl:for-each>
			</inventory_config>
		</msi_inventory_publish>
	</xsl:template>
</xsl:stylesheet>
