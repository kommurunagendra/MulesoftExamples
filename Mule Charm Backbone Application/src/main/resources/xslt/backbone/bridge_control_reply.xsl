<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://bridge.cdm.charm.cgi.com" version="2.0">
   <xsl:template match="root">
      <bridge_control_reply >
         <xsl:attribute name="version">
		<xsl:value-of select="*/@version"/>
	  </xsl:attribute>

         <xsl:apply-templates select="bridge_control_reply[1]/metareply" />
         <!-- merging control_reply -->
         <xsl:for-each select="bridge_control_reply/control_reply">
            <xsl:copy-of select="." />
         </xsl:for-each>
      </bridge_control_reply>
   </xsl:template>
   <xsl:template match="bridge_control_reply[1]/metareply">
      <xsl:copy-of select="." />
   </xsl:template>
</xsl:stylesheet>