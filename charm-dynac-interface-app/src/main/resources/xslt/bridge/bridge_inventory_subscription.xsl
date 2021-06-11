<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:brg="http://bridge.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="return_address" required="yes"/>
    <xsl:param name="userId" required="yes"/>
    <xsl:param name="password" required="yes"/>
    <xsl:param name="organizationId" required="yes"/>

    <xsl:template match="/brg:bridge_inventory_subscription">
        <tmdd:gateInventorySubscriptionMsg>
            <authentication>
                <user-id>
                    <xsl:value-of select="$userId"/>
                </user-id>
                <password>
                    <xsl:value-of select="$password"/>
                </password>
            </authentication>
            <organization-information>
                <organization-id>
                    <xsl:value-of select="$organizationId"/>
                </organization-id>
            </organization-information>
            <device-type>bridge</device-type>
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
                            select="brg:subscription_config/brg:subscription_id/brg:uuid"/>
                </subscriptionID>
                <xsl:if test="brg:subscription_config/brg:subscription_attr/brg:subscription_name">
                    <subscriptionName>
                        <xsl:value-of
                                select="brg:subscription_config/brg:subscription_attr/brg:subscription_name"/>
                    </subscriptionName>
                </xsl:if>
            </c2cMsgAdmin>
        </tmdd:gateInventorySubscriptionMsg>
    </xsl:template>
</xsl:stylesheet>
