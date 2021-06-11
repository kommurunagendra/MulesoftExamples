<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://drip.cdm.charm.cgi.com" version="2.0">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="root">
		<drip_inventory_response>
			<xsl:attribute name="version">
		<xsl:value-of select="*/@version" />
	  </xsl:attribute>
			<xsl:apply-templates select="drip_inventory_response[1]/metareply" />
			<xsl:variable name="inventoryAdded" select="false()" />
			<inventory_mutations>
				<xsl:for-each select="drip_inventory_response/inventory_mutations">
					<xsl:if test="not(normalize-space(inventory_id/uuid)='')">
						<xsl:if test="not($inventoryAdded)">
							<xsl:copy-of select="processed" />
							<xsl:copy-of select="inventory_id" />
							<xsl:variable name="inventoryAdded" select="true()" />
						</xsl:if>
						<!-- merging mutations -->
						<xsl:for-each select="mutations">
							<xsl:call-template name="mutations" />
						</xsl:for-each>
					</xsl:if>
				</xsl:for-each>
			</inventory_mutations>
		</drip_inventory_response>
	</xsl:template>
	<xsl:template match="drip_inventory_response[1]/metareply">
		<xsl:copy-of select="." />
	</xsl:template>
	<xsl:template name="mutations" match="mutations">
		<xsl:copy-of select="." />
	</xsl:template>
</xsl:stylesheet>