<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com"
                xmlns:tmdd="http://www.tmdd.org/3/messages"
                exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="no"/>

    <xsl:include href="../util/date_conversion.xsl"/>
    <xsl:include href="bermdrip_common.xsl"/>

    <xsl:template match="/bdrp:bermdrip_info_reply">
	<tmdd:dMSStatusMsg>
	<xsl:if test="bdrp:bermdrip_publish/bdrp:bermdrip_status_attr">
		<xsl:apply-templates select="bdrp:bermdrip_publish" />
	</xsl:if>
	<xsl:if test="not(bdrp:bermdrip_publish/bdrp:bermdrip_status_attr)">
		<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
			<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
				<organization-information>
					<organization-id>RWS</organization-id>
				</organization-information>
				<device-id>
					<xsl:choose>
						<xsl:when test="bdrp:bermdrip_publish/bdrp:bermdrip_id/bdrp:id">
							<xsl:value-of select="bdrp:bermdrip_publish/bdrp:bermdrip_id/bdrp:id" />
						</xsl:when>
						<xsl:otherwise>unknown</xsl:otherwise>
					</xsl:choose>
				</device-id>
				<device-status>unknown</device-status>
			</device-status-header>
			<current-message />
		</dms-status-item>
	</xsl:if>
	</tmdd:dMSStatusMsg>
</xsl:template>
</xsl:stylesheet>