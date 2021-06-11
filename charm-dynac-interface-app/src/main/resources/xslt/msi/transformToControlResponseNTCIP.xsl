<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:tmdd="http://www.tmdd.org/3/messages" xmlns:msi="http://msi.cdm.charm.cgi.com" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />
	<xsl:param name="organization_id" />
	<xsl:param name="request_id" />

	<xsl:template match="/msi:msi_control_reply">
		<tmdd:deviceControlResponseMsg xmlns:tmdd="http://www.tmdd.org/3/messages" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                               xsi:type="tmdd:DeviceControlResponseCharm">
			<organization-information>
				<organization-id><xsl:value-of select="$organization_id" /></organization-id>
			</organization-information>
			<device-id><xsl:value-of select="msi:msi_control_mutations/msi:msi_control/msi:msi_id/msi:id" /></device-id>

				<xsl:choose>
					<xsl:when test="$request_id!=''"><request-id><xsl:value-of select="$request_id" /></request-id></xsl:when>
					<xsl:otherwise>	<request-id>request</request-id></xsl:otherwise>
				
				</xsl:choose>
			<request-status>
				<xsl:choose>
					<xsl:when test="msi:msi_control_mutations/msi:processed = 'true'">
						<xsl:text>requested changes completed</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>request rejected invalid command parameters</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</request-status>
			<device-type>msi</device-type>
			<comments>dummyComments</comments>
		</tmdd:deviceControlResponseMsg>
	</xsl:template>
</xsl:stylesheet>
