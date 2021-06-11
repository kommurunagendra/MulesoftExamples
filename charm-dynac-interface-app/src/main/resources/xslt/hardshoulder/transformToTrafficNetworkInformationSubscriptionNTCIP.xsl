<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:hardshoulder="http://hardshoulder.cdm.charm.cgi.com"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:param name="return_address" required="yes" />
	<xsl:param name="organization_id" />
	<xsl:param name="requesting_organization_id" />
<xsl:param name="userId" />
<xsl:param name="password" />
	<xsl:template match="/hardshoulder:hardshoulder_inventory_subscription">
		
	    <tmdd:trafficNetworkInformationSubscriptionMsg xmlns:tmdd="http://www.tmdd.org/3/messages">

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
					<xsl:value-of select="$requesting_organization_id" />
				</organization-id>
			</organization-requesting>
         <network-information-type>hard shoulder inventory</network-information-type>
         <c2cMsgAdmin>
            <!--Optional:-->
            <informationalText>hard shoulder inventory subscription</informationalText>
            <returnAddress><xsl:value-of select="$return_address"/></returnAddress>
            <subscriptionAction>
               <subscriptionAction-item>replaceSubscription</subscriptionAction-item>
            </subscriptionAction>
            <subscriptionType>
               <subscriptionType-item>onChange</subscriptionType-item>
            </subscriptionType>
            <subscriptionID> <xsl:value-of select="hardshoulder:subscription_config/hardshoulder:subscription_id/hardshoulder:uuid"/></subscriptionID>
              <subscriptionName>
                        <xsl:value-of
                                select="hardshoulder:subscription_config/hardshoulder:subscription_attr/hardshoulder:subscription_name"/>
                    </subscriptionName>
            <subscriptionFrequency>1</subscriptionFrequency>
         </c2cMsgAdmin>
      </tmdd:trafficNetworkInformationSubscriptionMsg>

	</xsl:template>
</xsl:stylesheet>
