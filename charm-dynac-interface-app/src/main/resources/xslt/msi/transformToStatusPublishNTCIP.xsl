<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:msi="http://msi.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />


	<xsl:include href="msi_common.xsl" />

	<xsl:template match="/msi:msi_status_publish">
		<tmdd:dMSStatusUpdateMsg xmlns:tmdd="http://www.tmdd.org/3/messages">

			<c2cMsgAdmin>
				<subscriptionID>
					<xsl:value-of select="msi:subscription_id/msi:uuid" />
				</subscriptionID>
			</c2cMsgAdmin>
			
				<xsl:apply-templates select="msi:msi_publish" />
			

		</tmdd:dMSStatusUpdateMsg>
	</xsl:template>
</xsl:stylesheet>
