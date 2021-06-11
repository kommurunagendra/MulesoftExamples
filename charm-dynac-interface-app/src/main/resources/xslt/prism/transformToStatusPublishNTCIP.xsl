<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:prism="http://prism.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />


	<xsl:include href="prismCommon.xsl" />

	<xsl:template match="/prism:prism_status_publish">
		<tmdd:dMSStatusUpdateMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
			<c2cMsgAdmin>
				<subscriptionID>
					<xsl:value-of select="prism:subscription_id/prism:uuid" />
				</subscriptionID>
			</c2cMsgAdmin>
			<xsl:apply-templates select="prism:prism_publish" />
		</tmdd:dMSStatusUpdateMsg>
	</xsl:template>
</xsl:stylesheet>