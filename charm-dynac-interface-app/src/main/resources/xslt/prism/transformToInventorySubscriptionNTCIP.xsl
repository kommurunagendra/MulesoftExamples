<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:prism="http://prism.cdm.charm.cgi.com" exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />

	<xsl:param name="organization_id" />
	<xsl:param name="return_address"  />
	<xsl:param name="userId"  />
	<xsl:param name="password"  />
	
	
	<xsl:template match="/prism:prism_inventory_subscription">
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
				<xsl:if test="prism:subscription_config/prism:owner">
				<organization-function>
					<xsl:value-of select="prism:subscription_config/prism:owner" />
				</organization-function>
				</xsl:if>
			</organization-information>
			<device-type>cms sign</device-type>
			<device-information-type>device inventory</device-information-type>
			<xsl:if test="not(prism:subscription_config/prism:all_prism)">
				<device-filter>
					<device-id-list>
						<xsl:for-each select="prism:subscription_config/prism:prism_spec">
							<device-id>
								<xsl:value-of
									select="prism:prism_id/prism:id" />
							</device-id>
						</xsl:for-each>
					</device-id-list>
				</device-filter>
			</xsl:if>
			<c2cMsgAdmin>
			   <returnAddress>
                    <xsl:value-of select="$return_address"/>
                </returnAddress>
				<subscriptionAction>
					<subscriptionAction-item>newSubscription</subscriptionAction-item>
				</subscriptionAction>
				<subscriptionType>
					<subscriptionType-item>onChange</subscriptionType-item>
				</subscriptionType>
				<subscriptionID>
					<xsl:value-of
						select="prism:subscription_config/prism:subscription_id/prism:uuid" />
				</subscriptionID>
				<subscriptionName>
					<xsl:value-of
						select="prism:subscription_config/prism:subscription_attr/prism:subscription_name" />
				</subscriptionName>
				<subscriptionFrequency>1</subscriptionFrequency>
			</c2cMsgAdmin>
		</tmdd:dMSInventorySubscriptionMsg>
	</xsl:template>
</xsl:stylesheet>