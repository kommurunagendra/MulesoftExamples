<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:loc="http://location.cdm.charm.cgi.com"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="root">
		<location_info_reply xmlns="http://location.cdm.charm.cgi.com">
				<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="loc:location_info_reply">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="."/>
				</xsl:if>
			</xsl:for-each>
			<!-- merging loc:location_reply -->
			<xsl:for-each select="loc:location_info_reply">
				<xsl:for-each select="loc:location_reply">
					<xsl:copy-of select="."/>
				</xsl:for-each>
			</xsl:for-each>
		</location_info_reply>
	</xsl:template>
</xsl:stylesheet>