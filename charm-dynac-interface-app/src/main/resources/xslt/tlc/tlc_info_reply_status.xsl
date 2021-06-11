<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tlc="http://tlc.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="multi_message" />
	<xsl:param name="organizationId" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:include href="tlc_common.xsl" />

	<xsl:template match="/tlc:tlc_info_reply">
		<xsl:choose>
			<xsl:when test="tlc:tlc_publish">
				<tmdd:genericDeviceStatusMsg>
					<xsl:apply-templates select="tlc:tlc_publish" />
				</tmdd:genericDeviceStatusMsg>
			</xsl:when>
			<xsl:otherwise>
				<tmdd:genericDeviceStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
					<generic-status-item>
						<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm"
							xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organizationId" />
								</organization-id>
							</organization-information>
							<device-id>dummy</device-id>
							<device-status>unknown</device-status>
						</device-status-header>
					</generic-status-item>
				</tmdd:genericDeviceStatusMsg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>