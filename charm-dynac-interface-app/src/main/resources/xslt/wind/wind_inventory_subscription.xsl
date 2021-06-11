<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wind="http://wind.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="userId" required="yes" />
	<xsl:param name="password" required="yes" />
	<xsl:param name="organizationId" required="yes" />

	<xsl:template match="/wind:wind_inventory_subscription">
		<tmdd:deviceInformationSubscriptionMsg>
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
					<xsl:value-of select="$organizationId" />
				</organization-id>
			</organization-information>
			<device-type>wind sensor</device-type>
			<device-information-type>device inventory</device-information-type>
			<c2cMsgAdmin>
				<subscriptionAction>
					<subscriptionAction-item>replaceSubscription</subscriptionAction-item>
				</subscriptionAction>
				<subscriptionType>
					<subscriptionType-item>onChange</subscriptionType-item>
				</subscriptionType>
				<subscriptionID>
					<xsl:value-of
						select="wind:subscription_config/wind:subscription_id/wind:uuid" />
				</subscriptionID>
				<subscriptionName>
					<xsl:value-of
						select="wind:subscription_config/wind:subscription_attr/wind:subscription_name" />
				</subscriptionName>
				<subscriptionFrequency>1</subscriptionFrequency>
			</c2cMsgAdmin>

		<!-- no device-filter to be mapped when all_wpss is true -->
			<xsl:if test="wind:subscription_config/wind:wps_spec">
				<device-filter>
					<device-id-list>
						<xsl:for-each
							select="wind:subscription_config/wind:wps_spec/wind:wps_id">

							<device-id>
								<xsl:value-of select="./wind:id" />
							</device-id>

						</xsl:for-each>
					</device-id-list>
				</device-filter>
			</xsl:if>

		</tmdd:deviceInformationSubscriptionMsg>
	</xsl:template>
</xsl:stylesheet>