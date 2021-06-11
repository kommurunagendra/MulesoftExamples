<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />
	<xsl:variable name="bericht_aanmaak"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />
	<xsl:template match="/tmdd:dMSMessageInventoryUpdateMsg">
		<prism_inventory_publish xmlns="http://prism.cdm.charm.cgi.com"
			version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$bericht_aanmaak" />
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

				<xsl:for-each-group select="dms-message-inventory-item"
					group-by="device-id">
					 
					<prism_library>
						<action>
							<xsl:value-of select="inventory-action" />
						</action>
						<prism_type_id>
							<id>
								<xsl:value-of select="device-id" />
							</id>
						</prism_type_id>
						<prism_type_description>
							<xsl:value-of select="description" />
						</prism_type_description>
						<xsl:for-each-group select="current-group()" group-by="message-number">
							<prism_state>
								<prism_state_nr>
									<xsl:value-of select="message-number" />
								</prism_state_nr>
								<prism_state_mnemonic>
									<xsl:value-of select="message" />
								</prism_state_mnemonic>
							</prism_state>
						</xsl:for-each-group>

					</prism_library> 

				</xsl:for-each-group>

				<!-- <xsl:for-each select="dms-message-inventory-item">
					<prism_library>
						<action>
							<xsl:value-of select="inventory-action" />
						</action>
						<prism_type_id>
							<id>
								<xsl:value-of select="device-id" />
							</id>
						</prism_type_id>
						<prism_type_description>
							<xsl:value-of select="description" />
						</prism_type_description>
						<prism_state>
							<prism_state_nr>
								<xsl:value-of select="message-number" />
							</prism_state_nr>
							<prism_state_mnemonic>
								<xsl:value-of select="message" />
							</prism_state_mnemonic>
						</prism_state>
					</prism_library>
				</xsl:for-each> -->
			</inventory_config>
		</prism_inventory_publish>
	</xsl:template>
</xsl:stylesheet>
