<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">
    <xsl:output method="xml" indent="no"/>

    <xsl:param name="return_address" required="yes"/>
    <xsl:param name="userId" required="yes"/>
    <xsl:param name="password" required="yes"/>
    <xsl:param name="organizationId" required="yes"/>

    <xsl:template match="/bdrp:bermdrip_inventory_subscription">
        <tmdd:dMSInventorySubscriptionMsg>
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
            <device-type>graphical dms</device-type>
            <device-information-type>device inventory</device-information-type>
            <c2cMsgAdmin>
                <returnAddress>
                    <xsl:value-of
                            select="bdrp:subscription_config/bdrp:return_address"/>
                </returnAddress>
                <subscriptionAction>
                    <subscriptionAction-item>replaceSubscription</subscriptionAction-item>
                </subscriptionAction>
                <subscriptionType>
                    <subscriptionType-item>onChange</subscriptionType-item>
                </subscriptionType>
                <subscriptionID>
                    <xsl:value-of
                            select="bdrp:subscription_config/bdrp:subscription_id/bdrp:uuid"/>
                </subscriptionID>
                <subscriptionName>
                    <xsl:value-of
                            select="bdrp:subscription_config/bdrp:subscription_attr/bdrp:subscription_name"/>
                </subscriptionName>
                <subscriptionFrequency>1</subscriptionFrequency>
            </c2cMsgAdmin>
        </tmdd:dMSInventorySubscriptionMsg>
    </xsl:template>
</xsl:stylesheet>
