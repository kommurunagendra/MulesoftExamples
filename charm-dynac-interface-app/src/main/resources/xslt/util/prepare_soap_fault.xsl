<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tmdd="http://www.tmdd.org/3/messages">
	<xsl:output omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*" />

	<xsl:template match="node()[not(self::*)]">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/tmdd:errorReportMsg">
		<soapenv:Fault xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tmdd="http://www.tmdd.org/3/messages">
			<faultcode>soapenv:Server</faultcode>
			<faultstring><xsl:value-of select="error-text" /></faultstring>
			<detail>
				<tmdd:errorReportMsg>
					<xsl:apply-templates select="node()" />
				</tmdd:errorReportMsg>
			</detail>
		</soapenv:Fault>

	</xsl:template>

	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="node()|@*" />
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
