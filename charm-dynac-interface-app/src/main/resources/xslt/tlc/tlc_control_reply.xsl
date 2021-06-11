<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:tlc="http://tlc.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="organizationId" />
	<xsl:param name="requestid" />
	<xsl:param name="devicetype" />

	<xsl:template match="tlc:tlc_control_reply">
		<xsl:choose>
			<xsl:when test="tlc:tlc_control_mutations">
				<tmdd:deviceControlResponseMsg
					xsi:type="tmdd:DeviceControlResponseCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organizationId" />
						</organization-id>
					</organization-information>
					<device-id>
						<xsl:value-of
							select="tlc:tlc_control_mutations/tlc:tlc_control/tlc:tlc_id/tlc:id" />
					</device-id>
					<request-id>
						<xsl:value-of select="$requestid" />
					</request-id>
					<request-status>
						<xsl:choose>
							<xsl:when test="tlc:tlc_control_mutations/tlc:processed = 'true'">
								<xsl:text>requested changes completed</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>request rejected invalid command parameters</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</request-status>
					<device-type>
						<xsl:value-of select="$devicetype" />
					</device-type>
					<comments>comments</comments>
				</tmdd:deviceControlResponseMsg>
			</xsl:when>
			<xsl:otherwise>
				<tmdd:deviceControlResponseMsg
					xsi:type="tmdd:DeviceControlResponseCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					xmlns:tmdd="http://www.tmdd.org/3/messages">
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organizationId" />
						</organization-id>
					</organization-information>
					<device-id>dummy</device-id>
					<request-id>dummy</request-id>
					<request-status>request queued/not implemented</request-status>
					<comments>comments</comments>
				</tmdd:deviceControlResponseMsg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>