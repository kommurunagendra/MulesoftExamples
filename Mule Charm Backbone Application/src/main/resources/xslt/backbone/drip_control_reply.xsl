<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://drip.cdm.charm.cgi.com"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="no" />

	<xsl:template match="root">
		<drip_control_reply>
		<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="drip_control_reply">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="metareply" />
				</xsl:if>
			</xsl:for-each>
			
			<!-- merging drip_control_mutations -->
			<xsl:for-each select="drip_control_reply">
				<xsl:if test="count(drip_control_mutations[processed = 'true'])>0">
					<xsl:for-each select="drip_control_mutations">
						<xsl:copy-of select="." />
					</xsl:for-each>
                </xsl:if>
			</xsl:for-each>
		</drip_control_reply>
	</xsl:template>
</xsl:stylesheet>