<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:msi="http://msi.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />


	<xsl:include href="msi_common.xsl" />

	<xsl:template match="/msi:msi_info_reply">
		<tmdd:dMSStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
			<xsl:apply-templates select="msi:msi_publish" />
		</tmdd:dMSStatusMsg>
	</xsl:template>
</xsl:stylesheet>