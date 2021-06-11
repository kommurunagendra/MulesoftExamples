<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:detector="http://detector.si.cdm.charm.cgi.com">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:param name="request_id" />

	<xsl:template match="/detector:detector_si_control_reply">
		<tmdd:deviceControlResponseMsg xsi:type="tmdd:DeviceControlResponseCharm"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<organization-information>
				<organization-id>
					<xsl:value-of select="$organization_id" />
				</organization-id>
			</organization-information>

			<xsl:choose>
				<xsl:when
					test="detector:control_mutations[1]/detector:control/detector:fep_control/detector:fep_id">
					<device-id>
						<xsl:value-of
							select="detector:control_mutations[1]/detector:control/detector:fep_control/detector:fep_id/detector:id" />
					</device-id>


					<xsl:choose>
						<xsl:when test="$request_id">
							<request-id>
								<xsl:value-of select="$request_id" />
							</request-id>
						</xsl:when>
						<xsl:otherwise>
							<request-id>request</request-id>
						</xsl:otherwise>
					</xsl:choose>

					<xsl:choose>
						<xsl:when
							test="detector:control_mutations[1]/detector:processed = 'true'">
							<request-status>requested changes completed</request-status>
						</xsl:when>
						<xsl:otherwise>
							<request-status>request rejected invalid command parameters
							</request-status>
						</xsl:otherwise>
					</xsl:choose>
					<device-type>mtm</device-type>
				</xsl:when>

				<xsl:when
					test="detector:control_mutations[1]/detector:control/detector:outstation_control/detector:outstation_id">
					<device-id>
						<xsl:value-of
							select="detector:control_mutations[1]/detector:control/detector:outstation_control/detector:outstation_id/detector:id" />
					</device-id>
					<xsl:choose>
						<xsl:when test="$request_id">
							<request-id>
								<xsl:value-of select="$request_id" />
							</request-id>
						</xsl:when>
						<xsl:otherwise>
							<request-id>request</request-id>
						</xsl:otherwise>
					</xsl:choose>

					<xsl:choose>
						<xsl:when
							test="detector:control_mutations[1]/detector:processed = 'true'">
							<request-status>requested changes completed</request-status>
						</xsl:when>
						<xsl:otherwise>
							<request-status>request rejected invalid command parameters
							</request-status>
						</xsl:otherwise>
					</xsl:choose>
					<device-type>mtm</device-type>
				</xsl:when>

				<xsl:when
					test="detector:control_mutations[1]/detector:control/detector:detector_control/detector:detector_id">
					<device-id>
						<xsl:value-of
							select="detector:control_mutations[1]/detector:control/detector:detector_control/detector:detector_id/detector:id" />
					</device-id>
					<xsl:choose>
						<xsl:when test="$request_id">
							<request-id>
								<xsl:value-of select="$request_id" />
							</request-id>
						</xsl:when>
						<xsl:otherwise>
							<request-id>request</request-id>
						</xsl:otherwise>
					</xsl:choose>

					<xsl:choose>
						<xsl:when
							test="detector:control_mutations[1]/detector:processed = 'true'">
							<request-status>requested changes completed</request-status>
						</xsl:when>
						<xsl:otherwise>
							<request-status>request rejected invalid command parameters
							</request-status>
						</xsl:otherwise>
					</xsl:choose>
					<device-type>mtm</device-type>
				</xsl:when>
			</xsl:choose>
		</tmdd:deviceControlResponseMsg>
	</xsl:template>
</xsl:stylesheet>