<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://bermdrip.cdm.charm.cgi.com"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="root">
		<bermdrip_info_reply>
		<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="bermdrip_info_reply">
				<xsl:if test="position() = 1">
					 <xsl:copy-of select="metareply"/>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="bermdrip_info_reply">
				<xsl:call-template name="subscription_config"/>
			</xsl:for-each>
			<xsl:for-each select="bermdrip_info_reply">
			   <xsl:call-template name="bermdrip_publish"/>
			</xsl:for-each>
		</bermdrip_info_reply>
	</xsl:template>


<xsl:template name="bermdrip_publish" match="bermdrip_publish">
        <xsl:copy-of select="bermdrip_publish"/>
</xsl:template>
<xsl:template name="subscription_config" match="subscription_config">
        <xsl:copy-of select="subscription_config"/>
</xsl:template>
</xsl:stylesheet>