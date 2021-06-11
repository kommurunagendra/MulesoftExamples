<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:prism="http://prism.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="prismCommon.xsl" />


	<xsl:template match="/prism:prism_info_reply">
		<xsl:choose>
			<xsl:when test="prism:prism_publish">
				<tmdd:dMSStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
					<xsl:apply-templates select="prism:prism_publish" />
				</tmdd:dMSStatusMsg>
			</xsl:when>
			<xsl:otherwise>
				<tmdd:dMSStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
					<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
						<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm"
							xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>0</device-id>
							<device-status>on</device-status>
						</device-status-header>
						<current-message>dummy</current-message>
					</dms-status-item>
				</tmdd:dMSStatusMsg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>