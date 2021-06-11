<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:detector="http://detector.si.cdm.charm.cgi.com"
	exclude-result-prefixes="#all">

	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="organization_id" />

	<xsl:template match="detector:detector_publish">
		<xsl:param name="messageType" />
		<xsl:choose>
			<xsl:when test="$messageType = 'data'">
				<xsl:for-each select="detector:detector_publish">
					<detector-data-item>
						<detector-list>
							<detector-data-detail>
								<xsl:if test="detector:fep_id/detector:id">
									<site-id>
										<xsl:value-of select="detector:fep_id/detector:id" />
									</site-id>
								</xsl:if>
								<xsl:if test="detector:outstation_id/detector:id">
									<station-id>
										<xsl:value-of select="detector:outstation_id/detector:id" />
									</station-id>
								</xsl:if>
								<xsl:if test="detector:detector_id/detector:id">
									<detector-id>
										<xsl:value-of select="detector:detector_id/detector:id" />
									</detector-id>
									<xsl:if test="detector:detector_status_attr">
										<detection-time-stamp>
											<xsl:call-template name="isodate_to_tmdd_date">
												<xsl:with-param name="isoDateTimeInUtc"
													select="detector:detector_status_attr/detector:last_status_update" />
											</xsl:call-template>
										</detection-time-stamp>
										<xsl:if
											test="detector:detector_status_attr/detector:speed/detector:kmh">
											<vehicle-speed>
												<xsl:value-of
													select="detector:detector_status_attr/detector:speed/detector:kmh" />
											</vehicle-speed>
										</xsl:if>
										<xsl:if
											test="detector:detector_status_attr/detector:speed/detector:no_traffic">
											<vehicle-speed>
												<xsl:value-of
													select="detector:detector_status_attr/detector:speed/detector:no_traffic" />
											</vehicle-speed>
										</xsl:if>
										<xsl:if
											test="detector:detector_status_attr/detector:speed/detector:unknown">
											<vehicle-speed>
												<xsl:value-of
													select="detector:detector_status_attr/detector:speed/detector:unknown" />
											</vehicle-speed>
										</xsl:if>
										<xsl:if
											test="detector:detector_status_attr/detector:intensity/detector:count">
											<vehicle-count>
												<xsl:value-of
													select="detector:detector_status_attr/detector:intensity/detector:count" />
											</vehicle-count>
										</xsl:if>
										<xsl:if
											test="detector:detector_status_attr/detector:intensity/detector:unknown">
											<vehicle-count>
												<xsl:value-of
													select="detector:detector_status_attr/detector:intensity/detector:unknown" />
											</vehicle-count>
										</xsl:if>
									</xsl:if>
								</xsl:if>
							</detector-data-detail>
						</detector-list>
					</detector-data-item>
				</xsl:for-each>
			</xsl:when>

			<xsl:when test="$messageType = 'inventory'">
				<xsl:for-each select="detector:detector_publish">
					<xsl:if test="detector:fep_id/detector:id">
						<detector-inventory-item>
							<detector-station-inventory-header>
								<device-id>
									<xsl:value-of select="detector:fep_id/detector:id" />
								</device-id>
								<device-name>
									<xsl:value-of select="detector:fep_inventory_attr/detector:name" />
								</device-name>
								<device-description>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:description" />
								</device-description>
								<scan-on-off>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:scan_enabled" />
								</scan-on-off>
								<scan-interval>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:scan_interval" />
								</scan-interval>
								<protocol>
									<xsl:value-of select="detector:fep_inventory_attr/detector:fep_type" />
								</protocol>
								<hostname>
									<xsl:value-of select="detector:fep_inventory_attr/detector:host" />
								</hostname>
								<port>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:port_number" />
								</port>
								<response-timeout>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:response-timeout" />
								</response-timeout>
								<command-timeout>
									<xsl:value-of
										select="detector:fep_inventory_attr/detector:command_timeout" />
								</command-timeout>
							</detector-station-inventory-header>
						</detector-inventory-item>
					</xsl:if>
					<xsl:if test="detector:outstation_id/detector:id">
						<detector-inventory-item>
							<detector-station-inventory-header>
								<device-id>
									<xsl:value-of select="detector:outstation_id/detector:id" />
								</device-id>
								<device-name>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:name" />
								</device-name>
								<device-description>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:description" />
								</device-description>
								<scan-on-off>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:scan_enabled" />
								</scan-on-off>
								<scan-interval>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:scan_interval" />
								</scan-interval>
								<parent-device-id>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:outstation_address/detector:fep_id/detector:id" />
								</parent-device-id>
								<station-line-number>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:outstation_address/detector:os_line_number" />
								</station-line-number>
								<station-line-position>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:outstation_address/detector:os_line_position" />
								</station-line-position>
								<device-location>
									<latitude>
										<xsl:value-of
											select="detector:outstation_location_attr/detector:position_wgs84/detector:latitude" />
									</latitude>
									<longitude>
										<xsl:value-of
											select="detector:outstation_location_attr/detector:position_wgs84/detector:longitude" />
									</longitude>
								</device-location>
							</detector-station-inventory-header>
							<outstation-configuration>
								<road-code>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:outstation_reference/detector:road_code" />
								</road-code>
								<km-reference>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:outstation_reference/detector:km-reference" />
								</km-reference>
								<os-version>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:os_version" />
								</os-version>
								<detection-stations>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:detectorstations" />
								</detection-stations>
								<detectors>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:detectors" />
								</detectors>
								<detectors-speed-flow>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:detectors_sf" />
								</detectors-speed-flow>
								<speed-flow-enabled>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:sf_enabled" />
								</speed-flow-enabled>
								<msis>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:msis" />
								</msis>
								<msis-arg>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:msis_arg" />
								</msis-arg>
								<base-aid>
									<xsl:value-of
										select="detector:outstation_inventory_attr/detector:base_aid" />
								</base-aid>
							</outstation-configuration>
						</detector-inventory-item>
					</xsl:if>
					<xsl:if test="detector:detector_id/detector:id">
						<detector-inventory-item>
							<detector-list>
								<detector>
									<detector-inventory-header>
										<device-id>
											<xsl:value-of select="detector:detector_id/detector:id" />
										</device-id>
										<device-name>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:name" />
										</device-name>
										<device-description>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:description" />
										</device-description>
										<scan-on-off>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:scan_enabled" />
										</scan-on-off>
										<scan-interval>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:scan_interval" />
										</scan-interval>
										<parent-device-id>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:detector_address/detector:outstation_id/detector:id" />
										</parent-device-id>
										<station-line-number>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:detector_address/detector:detector_station_nr" />
										</station-line-number>
										<station-line-position>
											<xsl:value-of
												select="detector:detector_inventory_attr/detector:detector_address/detector:detector_nr" />
										</station-line-position>
										<device-location>
											<latitude>
												<xsl:value-of
													select="detector:detector_location_attr/detector:position_wgs84/detector:latitude" />
											</latitude>
											<longitude>
												<xsl:value-of
													select="detector:detector_location_attr/detector:position_wgs84/detector:longitude" />
											</longitude>
										</device-location>
									</detector-inventory-header>
								</detector>
							</detector-list>
						</detector-inventory-item>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>

			<xsl:when test="$messageType = 'status'">
				<xsl:for-each select="detector:detector_publish">
					<xsl:if test="detector:fep_id/detector:id">
						<detector-status-item>
							<detector-station-status-header>
								<device-id>
									<xsl:value-of select="detector:fep_id/detector:id" />
								</device-id>
								<xsl:if test="detector:fep_status_attr">
									<last-comm-time>
										<xsl:call-template name="isodate_to_tmdd_date">
											<xsl:with-param name="isoDateTimeInUtc"
												select="detector:fep_status_attr/detector:last_status_update" />
										</xsl:call-template>
									</last-comm-time>
									<device-mode>
										<xsl:value-of select="detector:fep_status_attr/detector:fep_mode" />
									</device-mode>
								</xsl:if>
							</detector-station-status-header>
						</detector-status-item>
					</xsl:if>
					<xsl:if test="detector:outstation_id/detector:id">
						<detector-status-item>
							<detector-station-status-header>
								<device-id>
									<xsl:value-of select="detector:outstation_id/detector:id" />
								</device-id>
								<xsl:if test="detector:outstation_status_attr">
									<last-comm-time>
										<xsl:call-template name="isodate_to_tmdd_date">
											<xsl:with-param name="isoDateTimeInUtc"
												select="detector:outstation_status_attr/detector:last_status_update" />
										</xsl:call-template>
									</last-comm-time>
									<device-mode>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:outstation_mode" />
									</device-mode>
									<aux-power-available>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:aux_power_available" />
									</aux-power-available>
									<power-supply>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:power_supply" />
									</power-supply>
									<cold-start>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:report_cold_start" />
									</cold-start>
									<fatal-error>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:fatal_error" />
									</fatal-error>
									<maintenance_code>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:maintenance_code" />
									</maintenance_code>
									<aid_override>
										<xsl:value-of
											select="detector:outstation_status_attr/detector:aid_override" />
									</aid_override>
									<!-- TODO: Need to add some more fields here. Waiting for KTC to 
										come back on the missing fields. -->
								</xsl:if>
							</detector-station-status-header>
						</detector-status-item>
					</xsl:if>
					<xsl:if test="detector:detector_id/detector:id">
						<detector-status-item>
							<detector-list>
								<detector>
									<detector-status-header>
										<device-id>
											<xsl:value-of select="detector:detector_id/detector:id" />
										</device-id>
										<xsl:if test="detector:detector_status_attr">
											<last-comm-time>
												<xsl:call-template name="isodate_to_tmdd_date">
													<xsl:with-param name="isoDateTimeInUtc"
														select="detector:detector_status_attr/detector:last_status_update" />
												</xsl:call-template>
											</last-comm-time>
											<device-status>
												<xsl:value-of
													select="detector:detector_status_attr/detector:detector_state" />
											</device-status>
										</xsl:if>
									</detector-status-header>
								</detector>
							</detector-list>
						</detector-status-item>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>