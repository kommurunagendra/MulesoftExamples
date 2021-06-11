<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://drip.cdm.charm.cgi.com">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="root">

		<drip_info_reply>
		<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="drip_info_reply">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="metareply"/>
				</xsl:if>
			</xsl:for-each>

			<!-- merging info_reply -->
			<xsl:for-each select="drip_info_reply">
				<xsl:call-template name="subscription_config" />
			</xsl:for-each>
			<xsl:for-each select="drip_info_reply">
				<xsl:call-template name="drip_publish" />
			</xsl:for-each>
		</drip_info_reply>

	</xsl:template>


	<xsl:template name="drip_publish" match="drip_publish">
		<xsl:copy-of select="drip_publish" />
	</xsl:template>
	<xsl:template name="subscription_config" match="subscription_config">
		<xsl:copy-of select="subscription_config" />
	</xsl:template>

</xsl:stylesheet>

