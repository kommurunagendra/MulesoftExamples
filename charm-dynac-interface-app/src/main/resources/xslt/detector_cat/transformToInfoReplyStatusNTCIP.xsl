<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:detector-cat="http://detector.cat.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />

	<xsl:template match="/detector-cat:detector_cat_info_reply">
		<tmdd:detectorStatusMsg>
			<detector-status-item>
				<detector-station-status-header>
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<device-id>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
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
						<date>
							<xsl:value-of
								select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 1, 4),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 6, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 9, 2))" />
						</date>
						<time>
							<xsl:value-of
								select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 12, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 15, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 18, 2),0,0,0)" />
						</time>
					</last-comm-time>
				</detector-station-status-header>
				<detector-list>
					<detector>
						<detector-status-header>
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of
									select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
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
								<date>
									<xsl:value-of
										select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 1, 4),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 6, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 9, 2))" />
								</date>
								<time>
									<xsl:value-of
										select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 12, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 15, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 18, 2),0,0,0)" />
								</time>
							</last-comm-time>
						</detector-status-header>
					</detector>
				</detector-list>
			</detector-status-item>
			<detector-status-item xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="tmdd:DetectorStatusCharm">
				<detector-list>
					<detector>
						<detector-status-header>
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of
									select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
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
								<date>
									<xsl:value-of
										select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 1, 4),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 6, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 9, 2))" />
								</date>
								<time>
									<xsl:value-of
										select="concat(substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 12, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 15, 2),substring(detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:last_status_update, 18, 2),0,0,0)" />
								</time>
							</last-comm-time>
						</detector-status-header>
					</detector>
				</detector-list>
				<traffic-flow>
					<traffic-flow-number>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:traffic_flow/detector-cat:traffic_flow_number" />
					</traffic-flow-number>
					<aid-active>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:traffic_flow/detector-cat:aid_active" />
					</aid-active>
					<aid-recommendation>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:traffic_flow/detector-cat:aid_recommendation" />
					</aid-recommendation>
				</traffic-flow>
				<local-overide>
					<channel1>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:local_override/detector-cat:channel1" />
					</channel1>
					<channel2>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:local_override/detector-cat:channel2" />
					</channel2>
					<channel3>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:local_override/detector-cat:channel3" />
					</channel3>
					<channel4>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:local_override/detector-cat:channel4" />
					</channel4>
				</local-overide>
				<statistics>
					<statistics-updated>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_statistics_attr/detector-cat:statistics_updated" />
					</statistics-updated>
					<count1>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_statistics_attr/detector-cat:f1_count" />
					</count1>
					<count2>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_statistics_attr/detector-cat:f2_count" />
					</count2>
					<count3>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_statistics_attr/detector-cat:f3_count" />
					</count3>
				</statistics>

				<device-mode>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:outstation_mode" />
				</device-mode>
				<aux-power-available>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:aux_power_available" />
				</aux-power-available>
				<power-supply>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:power_supply" />
				</power-supply>
				<cold-start>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:report_cold_start" />
				</cold-start>
				<fatal-error>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:fatal_error" />
				</fatal-error>
				<maintenance-code>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:maintenance_code" />
				</maintenance-code>
				<aid-override>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:aid_override" />
				</aid-override>
				<algorithm-status>UnderInstationControl</algorithm-status>
				<dim-state>true</dim-state>
				<dynamic-max-speed>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:dynamic_max_speed" />
				</dynamic-max-speed>
				<measure-number>
					<xsl:value-of
						select="detector-cat:detector_publish/detector-cat:outstation_status_attr/detector-cat:measure_number" />
				</measure-number>
				<exception-code>a</exception-code>
				<!-- TODO: Need to add some more fields here. Waiting for KTC to come 
					back on the missing fields. -->
			</detector-status-item>
		</tmdd:detectorStatusMsg>
	</xsl:template>
</xsl:stylesheet>