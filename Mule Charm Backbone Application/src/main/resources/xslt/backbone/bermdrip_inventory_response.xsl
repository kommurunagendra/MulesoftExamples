<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://bermdrip.cdm.charm.cgi.com"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="root">
		<bermdrip_inventory_response>
		<xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>
			<xsl:for-each select="bermdrip_inventory_response">
				<xsl:if test="position() = 1">
					<xsl:copy-of select="."/>
				</xsl:if>
			</xsl:for-each>
			<xsl:variable name="inventoryAdded" select="false()" />
			<inventory_mutations>
				<xsl:for-each select="bermdrip_inventory_response">
					<xsl:for-each select="inventory_mutations">
						<xsl:if test="not(normalize-space(inventory_id/uuid)='')">
							<xsl:if test="not($inventoryAdded)">
								<xsl:copy-of select="processed"/>
								<xsl:copy-of select="inventory_id"/>
								<xsl:variable name="inventoryAdded" select="true()" />
							</xsl:if>
							<!-- merging mutation -->
							<xsl:for-each select="mutations">
								<xsl:copy-of select="."/>
							</xsl:for-each>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>
			</inventory_mutations>
		</bermdrip_inventory_response>
	</xsl:template>
</xsl:stylesheet>