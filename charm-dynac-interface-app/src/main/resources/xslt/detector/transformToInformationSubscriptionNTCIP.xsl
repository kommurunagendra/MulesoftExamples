<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:detector="http://detector.si.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:param name="userId" />
	<xsl:param name="password" />
	<xsl:param name="return_address" />

	<xsl:template match="/detector:detector_si_inventory_subscription">
		<tmdd:deviceInformationSubscriptionMsg
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
			<device-type>detector station</device-type>
			<device-information-type>device inventory</device-information-type>

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
							select="detector:subscription_config/detector:subscription_id/detector:uuid" />
					</subscriptionID>
					<subscriptionName>
						<xsl:value-of
							select="detector:subscription_config/detector:subscription_attr/detector:subscription_name" />
					</subscriptionName>
					<subscriptionFrequency>1</subscriptionFrequency>
			</c2cMsgAdmin>
		</tmdd:deviceInformationSubscriptionMsg>
	</xsl:template>
</xsl:stylesheet>
