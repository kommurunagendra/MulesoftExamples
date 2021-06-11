<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:detector-cat="http://detector.cat.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">
	
	<xsl:output method="xml" indent="no" />
	<xsl:param name="organizationId" />
	<xsl:include href="../util/date_conversion.xsl" />
	
	<xsl:template match="/detector-cat:detector_cat_status_publish">
		<tmdd:detectorStatusUpdateMsg>
			<c2cMsgAdmin>
				<subscriptionID>
					<xsl:value-of select="detector-cat:subscription_id/detector-cat:uuid" />
				</subscriptionID>
			</c2cMsgAdmin>
			<detector-status-item>
				<detector-station-status-header>
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organizationId" />
						</organization-id>
					</organization-information>
					<device-id>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:detector_id/detector-cat:id" />
					</device-id>
					<device-status>
						<xsl:variable name="detectorState"
							select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:detector_state" />
						<xsl:choose>
							<xsl:when test="$detectorState = 'ok'">
								<xsl:text>on</xsl:text>
							</xsl:when>
							<xsl:when test="$detectorState = 'nok'">
								<xsl:text>off</xsl:text>
							</xsl:when>
							<xsl:when test="$detectorState = 'degraded'">
								<xsl:text>marginal</xsl:text>
							</xsl:when>
							<xsl:when test="$detectorState = 'unreliable'">
								<xsl:text>unknown</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of
									select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:detector_state" />
							</xsl:otherwise>
						</xsl:choose>
					</device-status>
					<last-comm-time>
						<xsl:call-template name="isodate_to_tmdd_date">
							<xsl:with-param name="isoDateTimeInUtc"
								select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update" />
						</xsl:call-template>
					</last-comm-time>
				</detector-station-status-header>
				<detector-list>
					<detector xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xsi:type="tmdd:DetectorStatusDetailsRSW">
						<detector-status-header>
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organizationId" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of
									select="detector-cat:detector_publish/detector-cat:detector_id/detector-cat:id" />
							</device-id>
							<device-status>
								<xsl:variable name="detectorState"
									select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:detector_state" />

								<xsl:choose>
									<xsl:when test="$detectorState = 'ok'">
										<xsl:text>on</xsl:text>
									</xsl:when>
									<xsl:when test="$detectorState = 'nok'">
										<xsl:text>off</xsl:text>
									</xsl:when>
									<xsl:when test="$detectorState = 'degraded'">
										<xsl:text>marginal</xsl:text>
									</xsl:when>
									<xsl:when test="$detectorState = 'unreliable'">
										<xsl:text>unknown</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of
											select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:detector_state" />
									</xsl:otherwise>
								</xsl:choose>
							</device-status>
						</detector-status-header>
						<connection-type>
							<xsl:variable name="connectionType"
								select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:connection_type" />

							<xsl:choose>
								<xsl:when test="$connectionType = 'urgent'">
									<xsl:text>yes</xsl:text>
								</xsl:when>
								<xsl:when test="$connectionType = 'basic'">
									<xsl:text>no</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:connection_type" />
								</xsl:otherwise>
							</xsl:choose>
						</connection-type>
					</detector>
				</detector-list>
			</detector-status-item>
		</tmdd:detectorStatusUpdateMsg>
	</xsl:template>
</xsl:stylesheet>