<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" />
	<xsl:param name="xmlContent" />
	<xsl:template match="/">
	<tlc_cdm_error>
		<error_message>
			Failed validation against XSD
		</error_message>
		<xml_content>
			<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
			<xsl:value-of select="$xmlContent" />
			<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
		</xml_content>
	</tlc_cdm_error>
	</xsl:template>
</xsl:stylesheet> 
