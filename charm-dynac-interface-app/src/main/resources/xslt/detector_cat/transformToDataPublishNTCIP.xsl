<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:detector-cat="http://detector.cat.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />
	<xsl:param name="organizationId" />
	<xsl:template match="/detector-cat:detector_cat_data_publish">
		<tmdd:detectorDataUpdateMsg>
			<c2cMsgAdmin>
				<subscriptionID>
					<xsl:value-of select="detector-cat:subscription_id/detector-cat:uuid" />
				</subscriptionID>
			</c2cMsgAdmin>
			<detector-data-item>
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organizationId" />
					</organization-id>
				</organization-information>
				<detector-list>
					<detector-data-detail xsi:type="tmdd:DetectorDataDetailRSW"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
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
									select="concat(substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 1, 4),substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 6, 2),substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 9, 2))" />
							</date>
							<time>
								<xsl:value-of
									select="concat(substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 12, 2),substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 15, 2),substring(detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:last_status_update, 18, 2))" />
							</time>
						</detection-time-stamp>
						<xsl:choose>
							<xsl:when
								test="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:count">
								<vehicle-count>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:count" />
								</vehicle-count>
							</xsl:when>
							<xsl:when
								test="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:intensity/detector-cat:unknown">
								<vehicle-count>99999</vehicle-count>
							</xsl:when>
						</xsl:choose>
						<vehicle-occupancy>0</vehicle-occupancy>
						<start-time>
							<date>
								<xsl:value-of
									select="concat(substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 1, 4),substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 6, 2),substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 9, 2))" />
							</date>
							<time>
								<xsl:value-of
									select="concat(substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 12, 2),substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 15, 2),substring(detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:examination_start_time, 18, 2))" />
							</time>
						</start-time>

						<xsl:if
							test="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:speed/detector-cat:kmh">
							<vehicle-speed>
								<xsl:value-of
									select="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:speed/detector-cat:kmh" />
							</vehicle-speed>
						</xsl:if>
						<xsl:if
							test="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:speed/detector-cat:no_traffic">
							<vehicle-speed>0</vehicle-speed>
						</xsl:if>
						<xsl:if
							test="detector-cat:detector_publish/detector-cat:detector_status_attr/detector-cat:speed/detector-cat:unknown">
							<vehicle-speed>255</vehicle-speed>
						</xsl:if>

						<vehicles-reliable>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:time_vehicles_reliable" />
						</vehicles-reliable>

						<vehicle-passages>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:time_vehicle_passages" />
						</vehicle-passages>
						<vehicles-reliable-incomplete>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:number_vehicles_reliable_incomplete" />
						</vehicles-reliable-incomplete>
						<vehicles-unreliable>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:number_vehicles_unreliable" />
						</vehicles-unreliable>
						<detector-unreliable>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:detector_unreliable" />
						</detector-unreliable>
						<point-cover-time>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:point_cover_time" />
						</point-cover-time>
						<congestion-designation>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:congestion_designation" />
						</congestion-designation>
						<vehicle-category>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:number_vehicle_category" />
						</vehicle-category>
						<category-attr-list>
							<vehicle-category-attr>
								<code>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:vehicle_category_attr/detector-cat:vc_code" />
								</code>
								<number>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:vehicle_category_attr/detector-cat:vc_number_v" />
								</number>
								<sum-rates>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:vehicle_category_attr/detector-cat:vc_sum_rates_v" />
								</sum-rates>
								<sum-square>
									<xsl:value-of
										select="detector-cat:detector_publish/detector-cat:detector_traffic_report_attr/detector-cat:vehicle_category_attr/detector-cat:vc_sum_square_v" />
								</sum-square>
							</vehicle-category-attr>
						</category-attr-list>
					</detector-data-detail>
				</detector-list>
			</detector-data-item>
		</tmdd:detectorDataUpdateMsg>
	</xsl:template>
</xsl:stylesheet>
