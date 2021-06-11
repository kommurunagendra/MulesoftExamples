<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                xmlns:drp="http://drip.cdm.charm.cgi.com"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:param name="return_address" required="yes"/>
    <xsl:param name="userId" required="yes"/>
    <xsl:param name="password" required="yes"/>
    <xsl:param name="organizationId" required="yes"/>

    <xsl:template match="/drp:drip_inventory_subscription">
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
            <device-type>text dms</device-type>
            <device-information-type>device inventory</device-information-type>
            <c2cMsgAdmin>
                <returnAddress><xsl:value-of select="$return_address"/></returnAddress>
                <subscriptionAction>
                    <subscriptionAction-item>replaceSubscription</subscriptionAction-item>
                </subscriptionAction>
                <subscriptionType>
                    <subscriptionType-item>onChange</subscriptionType-item>
                </subscriptionType>
                <subscriptionID>
                    <xsl:value-of
                            select="drp:subscription_config/drp:subscription_id/drp:uuid"/>
                </subscriptionID>
                <subscriptionName>
                    <xsl:value-of
                            select="drp:subscription_config/drp:subscription_attr/drp:subscription_name"/>
                </subscriptionName>
                <xsl:choose>
                    <xsl:when
                            test="drp:subscription_config/drp:subscription_attr/drp:subscription_frequency">
                        <subscriptionFrequency>
                            <xsl:value-of
                                    select="drp:subscription_config/drp:subscription_attr/drp:subscription_frequency"/>
                        </subscriptionFrequency>
                    </xsl:when>
                    <xsl:otherwise>
                        <subscriptionFrequency>1</subscriptionFrequency>
                    </xsl:otherwise>
                </xsl:choose>
            </c2cMsgAdmin>
        </tmdd:dMSInventorySubscriptionMsg>
    </xsl:template>
</xsl:stylesheet>