<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tlc="http://tlc.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>
    <xsl:param name="userId" required="yes"/>
    <xsl:param name="password" required="yes"/>
    <xsl:param name="organizationId" required="yes"/>
    <xsl:param name="return_address" required="yes"/>

    <xsl:template match="/tlc:tlc_inventory_subscription">
        <tmdd:deviceInformationSubscriptionMsg>
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
            <!--Info for both TDI and VRI will be sent by dynac, regardless of provided type-->
            <device-type>TDI</device-type>
            <!--<device-type>VRI</device-type>-->
            <device-information-type>device inventory</device-information-type>
            <device-filter>
                <xsl:choose>
                    <xsl:when test="tlc:subscription_config/tlc:tlc_spec">
                        <device-id-list>
                            <xsl:for-each
                                select="tlc:subscription_config/tlc:tlc_spec/tlc:tlc_id">
                                <device-id>
                                    <xsl:value-of select="./tlc:id"/>
                                </device-id>
                                </xsl:for-each>
                       </device-id-list>
                    </xsl:when>
                </xsl:choose>
            </device-filter>
            <c2cMsgAdmin>
            	<returnAddress>
            		<xsl:value-of select="$return_address"/>
            	</returnAddress>
                <subscriptionAction>
                    <subscriptionAction-item>replaceSubscription</subscriptionAction-item>
                </subscriptionAction>
                <subscriptionType>
                    <subscriptionType-item>onChange</subscriptionType-item>
                </subscriptionType>
                <subscriptionID>
                    <xsl:value-of
                            select="tlc:subscription_config/tlc:subscription_id/tlc:uuid"/>
                </subscriptionID>
                <subscriptionName>
                    <xsl:value-of
                            select="tlc:subscription_config/tlc:subscription_attr/tlc:subscription_name"/>
                </subscriptionName>
                <subscriptionFrequency>1</subscriptionFrequency>
            </c2cMsgAdmin>
        </tmdd:deviceInformationSubscriptionMsg>
    </xsl:template>
</xsl:stylesheet>