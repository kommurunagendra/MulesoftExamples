<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:brg="http://bridge.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="bridge_common.xsl"/>

    <xsl:template match="/brg:bridge_info_reply">
    <xsl:choose>
       <xsl:when test="brg:bridge_publish">
    <tmdd:gateStatusMsg>
            <xsl:apply-templates select="brg:bridge_publish"/>
        </tmdd:gateStatusMsg>
        </xsl:when>
        <xsl:otherwise>
         <tmdd:gateStatusMsg>
					 <gate-status-item>
            <device-status-header
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:type="tmdd:DeviceStatusHeaderCharm">
                
                   <organization-information>
								<organization-id>RWS</organization-id>
							</organization-information>
               
                <device-id>dummy</device-id>
                  
                <device-status>unknown</device-status>
               
                <device-type>bridge</device-type>
            </device-status-header>
            <gate-status>gate partially opened closed</gate-status>
            </gate-status-item>
            </tmdd:gateStatusMsg>
               
                    </xsl:otherwise>
                   
                </xsl:choose>
            
      
			
       
    </xsl:template>
</xsl:stylesheet>