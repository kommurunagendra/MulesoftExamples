<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:prism="http://prism.cdm.charm.cgi.com" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:param name="request_id" />


	<xsl:template match="/prism:prism_control_reply">
		<tmdd:deviceControlResponseMsg
			xmlns:tmdd="http://www.tmdd.org/3/messages" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                               xsi:type="tmdd:DeviceControlResponseCharm">
			<organization-information>
				<organization-id>
					<xsl:value-of select="$organization_id" />
				</organization-id>
			</organization-information>
			<device-id>
					<xsl:value-of select="prism:prism_control_mutations/prism:prism_control/prism:prism_id/prism:id" />
				</device-id>
				<xsl:choose>
					<xsl:when test="$request_id!=''"><request-id><xsl:value-of select="$request_id" /></request-id></xsl:when>
					<xsl:otherwise>	<request-id>request</request-id></xsl:otherwise>
				
				</xsl:choose>
			
				
				<request-status>
				<xsl:choose>
					<xsl:when test="prism:prism_control_mutations/prism:processed = 'true'">
						<xsl:text>requested changes completed</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>request rejected invalid command parameters</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</request-status>
			<device-type>cms sign</device-type>
			<comments>dummyComments</comments>
		</tmdd:deviceControlResponseMsg>
	</xsl:template>
</xsl:stylesheet>
