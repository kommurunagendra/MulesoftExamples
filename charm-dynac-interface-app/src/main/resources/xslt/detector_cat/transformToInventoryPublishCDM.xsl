<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />
	<xsl:variable name="message_created_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />

	<xsl:template match="/tmdd:detectorInventoryUpdateMsg">
		<detector_cat_inventory_publish
			xmlns="http://detector.cat.cdm.charm.cgi.com" version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_created_date" />
				</message_create_date>
			</meta>
			<subscription_id>
				<uuid>
					<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>
			<inventory_config>
				<xsl:choose>
					<xsl:when test="c2cMsgAdmin/informationalText = 'FULLSYNC'">
						<full_sync>true</full_sync>
					</xsl:when>
					<xsl:otherwise>
						<full_sync>false</full_sync>
					</xsl:otherwise>
				</xsl:choose>
				<inventory_id>
					<uuid>
						<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
					</uuid>
				</inventory_id>

				<!-- populate FEP or Outstation -->
				<xsl:for-each select="detector-inventory-item">
					<xsl:choose>
						<xsl:when
							test="(normalize-space(detector-station-inventory-header/parent-device-id)='')">
							<fep_inventory>
								<action>
									<xsl:value-of
										select="detector-station-inventory-header/inventory-action" />
								</action>
								<fep_id>
									<id>
										<xsl:value-of select="detector-station-inventory-header/device-id" />
									</id>
								</fep_id>
								<fep_inventory_attr>
									<name>
										<xsl:value-of select="detector-station-inventory-header/device-name" />
									</name>
									<description>
										<xsl:value-of
											select="detector-station-inventory-header/device-description" />
									</description>
									<scan_enabled>
										<xsl:value-of select="detector-station-inventory-header/scan-on-off" />
									</scan_enabled>
									<scan_interval>
										<xsl:value-of
											select="detector-station-inventory-header/scan-interval" />
									</scan_interval>

									<xsl:choose>
										<xsl:when
											test="detector-station-inventory-header/protocol = 'fep-partyline'">
											<fep_type>partlyline</fep_type>
										</xsl:when>
										<xsl:otherwise>
											<fep_type>udp</fep_type>
										</xsl:otherwise>
									</xsl:choose>

									<host>
										<xsl:value-of select="detector-station-inventory-header/hostname" />
									</host>
									<port_number>
										<xsl:value-of select="detector-station-inventory-header/port" />
									</port_number>
									<response_timeout>
										<xsl:value-of
											select="detector-station-inventory-header/response-timeout" />
									</response_timeout>
									<command_timeout>
										<xsl:value-of
											select="detector-station-inventory-header/command-timeout" />
									</command_timeout>
									<fep_lines>
										<xsl:value-of select="detector-station-inventory-header/fep-lines" />
									</fep_lines>
									<owner>
									<xsl:value-of select="detector-station-inventory-header/data-owner"/>
                            		</owner>
								</fep_inventory_attr>
							</fep_inventory>
						</xsl:when>

						<xsl:when
							test="not(normalize-space(detector-station-inventory-header/parent-device-id)='')">
							<outstation_inventory>
								<action>
									<xsl:value-of
										select="detector-station-inventory-header/inventory-action" />
								</action>
								<outstation_id>
									<id>
										<xsl:value-of select="detector-station-inventory-header/device-id" />
									</id>
								</outstation_id>
								<outstation_inventory_attr>
									<name>
										<xsl:value-of select="detector-station-inventory-header/device-name" />
									</name>
									<description>
										<xsl:value-of
											select="detector-station-inventory-header/device-description" />
									</description>
									<scan_enabled>
										<xsl:value-of select="detector-station-inventory-header/scan-on-off" />
									</scan_enabled>
									<scan_interval>
										<xsl:value-of
											select="detector-station-inventory-header/scan-interval" />
									</scan_interval>
									<outstation_address>
										<fep_id>
											<id>
												<xsl:value-of
													select="detector-station-inventory-header/parent-device-id" />
											</id>
										</fep_id>
										<os_line_number>
											<xsl:value-of
												select="detector-station-inventory-header/station-line-number" />
										</os_line_number>
										<os_line_position>
											<xsl:value-of
												select="detector-station-inventory-header/station-line-position" />
										</os_line_position>
									</outstation_address>
									<outstation_reference>
										<road_code>
											<xsl:value-of select="outstation-configuration/road-code" />
										</road_code>
										<km_reference>
											<xsl:value-of select="outstation-configuration/km-reference" />
										</km_reference>
									</outstation_reference>
									<os_version>
										<xsl:value-of select="outstation-configuration/os-version" />
									</os_version>
									<detectorstations>
										<xsl:value-of select="outstation-configuration/detector-stations" />
									</detectorstations>
									<detectors>
										<xsl:value-of select="outstation-configuration/detectors" />
									</detectors>
									<detectors_sf>
										<xsl:value-of select="outstation-configuration/detectors-speed-flow" />
									</detectors_sf>
									<sf_enabled>
										<xsl:value-of select="outstation-configuration/speed-flow-enabled" />
									</sf_enabled>
									<msis>
										<xsl:value-of select="outstation-configuration/msis" />
									</msis>
									<msis_arg>
										<xsl:value-of select="outstation-configuration/msis-arg" />
									</msis_arg>
									<base_aid>
										<xsl:value-of select="outstation-configuration/base-aid" />
									</base_aid>
									<owner>
									<xsl:value-of select="detector-station-inventory-header/data-owner"/>
                            		</owner>
								</outstation_inventory_attr>
								<outstation_location_attr>
									<xsl:if test="detector-station-inventory-header/position-bps-code">
										<position_bps>
											<xsl:value-of
												select="detector-station-inventory-header/position-bps-code" />
										</position_bps>
									</xsl:if>
									<position_wgs84>
										<latitude>
											<xsl:value-of
												select="detector-station-inventory-header/device-location/latitude" />
										</latitude>
										<longitude>
											<xsl:value-of
												select="detector-station-inventory-header/device-location/longitude" />
										</longitude>
									</position_wgs84>
								</outstation_location_attr>
							</outstation_inventory>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>

				<xsl:for-each select="detector-inventory-item">
					<xsl:variable name="detectorInvItem" select="." />
					<xsl:for-each select="detector-list/detector">
						<!-- for each nested detector list -->
						<detector_inventory>
							<action>
								<xsl:value-of select="detector-inventory-header/inventory-action" />
							</action>
							<detector_id>
								<id>
									<xsl:value-of select="detector-inventory-header/device-id" />
								</id>
							</detector_id>
							<detector_inventory_attr>
								<name>
									<xsl:value-of select="detector-inventory-header/device-name" />
								</name>
								<description>
									<xsl:value-of select="detector-inventory-header/device-description" />
								</description>
								<scan_enabled>
									<xsl:value-of select="detector-inventory-header/scan-on-off" />
								</scan_enabled>
								<scan_interval>
									<xsl:value-of select="detector-inventory-header/scan-interval" />
								</scan_interval>
								<address>address</address>
								<port>0</port>
								<detector_address>
									<outstation_id>
										<id>
											<xsl:value-of select="detector-inventory-header/parent-device-id" />
										</id>
									</outstation_id>
									<detector_station_nr>
										<xsl:value-of select="detector-inventory-header/station-line-number" />
									</detector_station_nr>
									<detector_nr>
										<xsl:value-of
											select="detector-inventory-header/station-line-position" />
									</detector_nr>
								</detector_address>
								<owner>
									<xsl:value-of select="detector-inventory-header/data-owner"/>
                            		</owner>
							</detector_inventory_attr>
							<detector_location_attr>
								<xsl:if test="detector-inventory-header/position-bps-code">
									<position_bps>
										<xsl:value-of select="detector-inventory-header/position-bps-code" />
									</position_bps>
								</xsl:if>
								<position_wgs84>
									<latitude>
										<xsl:value-of
											select="detector-inventory-header/device-location/latitude" />
									</latitude>
									<longitude>
										<xsl:value-of
											select="detector-inventory-header/device-location/longitude" />
									</longitude>
								</position_wgs84>
							</detector_location_attr>
							<measure_site_table>
										<id>
											<xsl:value-of
												select="measure-site-table/id" />
										</id>
										<version>
											<xsl:value-of select="measure-site-table/version" />
										</version>
										<measure_site_record>
											<id>
												<xsl:value-of
													select="measure-site-table/site-record-id" />
											</id>
											<version>
												<xsl:value-of select="measure-site-table/site-record-version" />
											</version>
										</measure_site_record>
										<measure_site_record_version_time>
											<xsl:call-template name="tmdd_date_to_isodate">
												<xsl:with-param name="tmddDate"
													select="measure-site-table/site-record-version-time" />
											</xsl:call-template>
										</measure_site_record_version_time>
										<computation_method><xsl:value-of select="measure-site-table/computation-method" /></computation_method>
										<xsl:if
											test="not(normalize-space($detectorInvItem/measurement-equipment-ref)='')">
											 <measurement_equipment_reference><xsl:value-of select="$detectorInvItem/measurement-equipment-ref"/></measurement_equipment_reference>
										</xsl:if>
										<xsl:if
											test="not(normalize-space($detectorInvItem/detector-type)='')">
											  <measurement_equipment_type_used>
												<xsl:choose>
													<xsl:when test="$detectorInvItem/detector-type='inductive loop'">
														<xsl:text>loop</xsl:text>
													</xsl:when>
													<xsl:when test="$detectorInvItem/detector-type='video Image'">
														<xsl:text>video detection</xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="$detectorInvItem/detector-type" />
												</xsl:otherwise>
											</xsl:choose>
										</measurement_equipment_type_used>
										</xsl:if>
										<measurement_site_name>
											<xsl:value-of
												select="measure-site-table/measurement-site-name" />
										</measurement_site_name>
										<measurement_site_number_of_lanes>
											<xsl:value-of select="measure-site-table/measurement-site-number-of-lanes" />
										</measurement_site_number_of_lanes>
										<measurement_side>
											<xsl:value-of select="measure-site-table/measurement-side" />
										</measurement_side>
										<xsl:for-each select="measure-site-table/measurement-specific-characteristics-list/measurement-specific-characteristic">
											<measurement_specific_characteristics>
												<index>
													<xsl:value-of select="index" />
												</index>
												<measure_value_type>
													<xsl:value-of select="measure-type" />
												</measure_value_type>
												<measurement_specific>
													<accuracy>
														<xsl:value-of select="accuracy" />
													</accuracy>
													<period>
														<xsl:value-of select="period" />
													</period>
													<lane>
														<xsl:value-of select="specific-lane" />
													</lane>
													<xsl:choose>
														<xsl:when
															test="count(vehicle-characteristics-list/vehicle-characteristic) &gt; 0 ">
															<xsl:for-each
																select="vehicle-characteristics-list/vehicle-characteristic">
																<vehicle_characteristics>
																	<vc_code>
																		<xsl:value-of select="vc-code" />
																	</vc_code>
																	<comparison>
																		<xsl:value-of select="comparison" />
																	</comparison>
																	<length>
																		<xsl:value-of select="length cast as xs:double" />
																	</length>
																</vehicle_characteristics>
															</xsl:for-each>
														</xsl:when>
														<xsl:otherwise>
															<vehicle_no_characteristics>true</vehicle_no_characteristics>
														</xsl:otherwise>
													</xsl:choose>
												</measurement_specific>
											</measurement_specific_characteristics>
										</xsl:for-each>
										<measure_site_location>
											<position_wgs84>
												<latitude>0.00</latitude>
												<longitude>0.00</longitude>
											</position_wgs84>
											<position_extension>
												<carriageway>
													<xsl:value-of select="$detectorInvItem/carriageway" />
												</carriageway>
											</position_extension>
										</measure_site_location>
									</measure_site_table>
						</detector_inventory>
					</xsl:for-each>
				</xsl:for-each>
			</inventory_config>
		</detector_cat_inventory_publish>
	</xsl:template>
</xsl:stylesheet>
