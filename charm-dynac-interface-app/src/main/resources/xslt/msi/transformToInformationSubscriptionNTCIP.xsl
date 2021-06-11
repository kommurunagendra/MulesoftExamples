<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:msi="http://msi.cdm.charm.cgi.com" exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />

	<xsl:param name="organization_id" />
	<xsl:param name="userId" required="yes" />
	<xsl:param name="password" required="yes" />
	<xsl:param name="return_address"/>

	<xsl:template match="/msi:msi_inventory_subscription">
		<tmdd:dMSInventorySubscriptionMsg
			xmlns:tmdd="http://www.tmdd.org/3/messages">

			<authentication>
				<user-id>
					<xsl:value-of select="$userId" />
				</user-id>
				<password>
					<xsl:value-of select="$password" />
				</password>
			</authentication>
			<organization-information>
				<organization-id>
					<xsl:value-of select="$organization_id" />
				</organization-id>
			</organization-information>
			<organization-requesting>
				<organization-id>
					<xsl:value-of select="$organization_id" />
				</organization-id>
			</organization-requesting>
			<device-type>msi</device-type>
			<device-information-type>device inventory</device-information-type>
			<xsl:if test="msi:subscription_config/msi:msi_spec">
				<device-filter>
					<device-id-list>
						<xsl:for-each select="msi:subscription_config/msi:msi_spec/msi:msi_id">
							<device-id><xsl:value-of select="msi:id"/></device-id>
						</xsl:for-each>
					</device-id-list>
				</device-filter>
			</xsl:if>
			<c2cMsgAdmin>
				<returnAddress>
					<xsl:value-of select="$return_address" />
				</returnAddress>
				<subscriptionAction>
					<subscriptionAction-item>replaceSubscription</subscriptionAction-item>
				</subscriptionAction>
				<subscriptionType>
					<subscriptionType-item>onChange</subscriptionType-item>
				</subscriptionType>
				<subscriptionID>
					<xsl:value-of
						select="msi:subscription_config/msi:subscription_id/msi:uuid" />
				</subscriptionID>
				<subscriptionName>
					<xsl:value-of
						select="msi:subscription_config/msi:subscription_attr/msi:subscription_name" />
				</subscriptionName>
				<subscriptionFrequency>1</subscriptionFrequency>
			</c2cMsgAdmin>
		</tmdd:dMSInventorySubscriptionMsg>
	</xsl:template>
</xsl:stylesheet>
