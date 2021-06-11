<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:detector-cat="http://detector.cat.cdm.charm.cgi.com"
	exclude-result-prefixes="#all">

	<xsl:param name="organizationId" />

	<xsl:template match="detector-cat:detector_cat_publish">
		<xsl:param name="messageType" />
		<xsl:choose>
			<xsl:when test="$messageType = 'data'">
				<xsl:for-each select="detector-cat:detector_publish">
					<detector-data-item>
						<organization-information>
							<organization-id>
								<xsl:value-of select="$organizationId" />
							</organization-id>
						</organization-information>
						<detector-list>
							<detector-data-detail>
								<station-id>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
								</station-id>
								<detector-id>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_id/detector-cat:id" />
								</detector-id>
								<detection-time-stamp>
									<date>
										<xsl:value-of
											select="concat(substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 1, 4),substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 6, 2),substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 9, 2))" />
									</date>
									<time>
										<xsl:value-of
											select="concat(substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 12, 2),substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 15, 2),substring(detector-cat:detector_status_attr/detector-cat:last_status_update, 18, 2),0,0,0)" />
									</time>
								</detection-time-stamp>
								<xsl:if
									test="detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:count">
									<vehicle-count>
										<xsl:value-of
											select="detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:count" />
									</vehicle-count>
								</xsl:if>
								<xsl:if
									test="detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:unknown">
									<vehicle-count>
										<xsl:value-of
											select="detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:unknown" />
									</vehicle-count>
								</xsl:if>
								<vehicle-occupancy>0</vehicle-occupancy>
								<start-time>
									<date>
										<xsl:value-of
											select="concat(substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 1, 4),substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 6, 2),substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 9, 2))" />
									</date>
									<time>
										<xsl:value-of
											select="concat(substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 12, 2),substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 15, 2),substring(detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 18, 2),0,0,0)" />
									</time>
								</start-time>

								<xsl:if
									test="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:kmh">
									<vehicle-speed>
										<xsl:value-of
											select="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:kmh" />
									</vehicle-speed>
								</xsl:if>
								<xsl:if
									test="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:no_traffic">
									<vehicle-speed>
										<xsl:value-of
											select="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:no_traffic" />
									</vehicle-speed>
								</xsl:if>
								<xsl:if
									test="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:unknown">
									<vehicle-speed>
										<xsl:value-of
											select="detector-cat:detector_status_attr/detector-cat:speed/detector-cat:unknown" />
									</vehicle-speed>
								</xsl:if>
							</detector-data-detail>
						</detector-list>
					</detector-data-item>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>