<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://bermdrip.cdm.charm.cgi.com"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="root">
		<bermdrip_control_reply>
		<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="bermdrip_control_reply">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="metareply" />	
				</xsl:if>
			</xsl:for-each>
			<!-- merging bermdrip_control_mutations -->
			<xsl:for-each select="bermdrip_control_reply">
				<xsl:for-each select="bermdrip_control_mutations">
					<xsl:copy-of select="." />	
				</xsl:for-each>
			</xsl:for-each>
		</bermdrip_control_reply>
	</xsl:template>
</xsl:stylesheet>