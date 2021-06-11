<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://hardshoulder.cdm.charm.cgi.com">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="root">
		<msi_info_reply>
			<xsl:attribute name="version">
			<xsl:value-of select="*/@version" />
	  	</xsl:attribute>
			<xsl:for-each select="hardshoulder_info_reply">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="metareply" />
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="hardshoulder_info_reply">
				<xsl:copy-of select="subscription_config_hardshoulder" />
			</xsl:for-each>

			<xsl:for-each select="hardshoulder_info_reply">
				<xsl:copy-of select="hardshoulder_publish" />
			</xsl:for-each>
		</msi_info_reply>
	</xsl:template>
</xsl:stylesheet>