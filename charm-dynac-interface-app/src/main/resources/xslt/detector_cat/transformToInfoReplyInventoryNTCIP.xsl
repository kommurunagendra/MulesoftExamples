<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:detector-cat="http://detector.cat.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:param name="device_type"/>

	<xsl:template match="/detector-cat:detector_cat_info_reply">
		<ns2:detectorInventoryMsg  xmlns:ns2="http://www.tmdd.org/3/messages" xmlns:ns3="http://www.ntcip.org/c2c-message-administration">
			<detector-inventory-item xsi:type="ns2:DetectorInventoryCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<detector-station-inventory-header	xsi:type="ns2:DeviceInventoryHeaderCharm">
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<device-id>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
					</device-id>
					<device-location>
						<latitude>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:outstation_location_attr/detector-cat:position_wgs84/detector-cat:latitude" />
						</latitude>
						<longitude>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:outstation_location_attr/detector-cat:position_wgs84/detector-cat:longitude" />
						</longitude>
					</device-location>
					<device-name>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:name" />
					</device-name>
					<device-description>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:description" />
					</device-description>
					<device-type>
						<xsl:value-of select="$device_type" />
					</device-type>
					<scan-on-off>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:scan_enabled" />
					</scan-on-off>
					<scan-interval>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:scan_interval" />
					</scan-interval>
				</detector-station-inventory-header>
				<detector-list xsi:nil="true" />
			</detector-inventory-item>
			<detector-inventory-item xsi:type="ns2:DetectorInventoryNDW" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<detector-station-inventory-header xsi:type="ns2:DeviceInventoryHeaderCharm">
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<device-id>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_id/detector-cat:id" />
					</device-id>
					<device-location>
						<latitude>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:outstation_location_attr/detector-cat:position_wgs84/detector-cat:latitude" />
						</latitude>
						<longitude>
							<xsl:value-of
								select="detector-cat:detector_publish/detector-cat:outstation_location_attr/detector-cat:position_wgs84/detector-cat:longitude" />
						</longitude>
					</device-location>
					<device-name>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:name" />
					</device-name>
					<device-description>
						<xsl:value-of
							select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:description" />
					</device-description>
					<device-type>
						<xsl:value-of select="$device_type"/>
					</device-type>
					<scan-on-off>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:scan_enabled" />
					</scan-on-off>
					<scan-interval>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:scan_interval" />
					</scan-interval>
					<parent-device-id>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:outstation_address/detector-cat:fep_id/detector-cat:id" />
					</parent-device-id>
					<station-line-number>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:outstation_address/detector-cat:os_line_number" />
					</station-line-number>
					<station-line-position>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:outstation_address/detector-cat:os_line_position" />
					</station-line-position>
				</detector-station-inventory-header>
				<detector-list>
					<detector xsi:type="ns2:DetectorInventoryDetailsCharm">
						<detector-inventory-header xsi:type="ns2:DeviceInventoryHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_id/detector-cat:id" />
							</device-id>
							<device-location>
								<latitude>
									<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_location_attr/detector-cat:position_wgs84/detector-cat:latitude" />
								</latitude>
								<longitude>
									<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_location_attr/detector-cat:position_wgs84/detector-cat:longitude" />
								</longitude>
							</device-location>
							<device-name>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:name" />
							</device-name>
							<device-description>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:description" />
							</device-description>
							<device-type>
								<xsl:value-of select="$device_type"/>
							</device-type>
							<scan-on-off>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:scan_enabled" />
							</scan-on-off>
							<scan-interval>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:scan_interval" />
							</scan-interval>
							<hostname>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:address" />
							</hostname>
							<port>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:port" />
							</port>
							<parent-device-id>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:detector_address/detector-cat:outstation_id/detector-cat:id" />
							</parent-device-id>
							<station-line-number>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:detector_address/detector-cat:detector_station_nr" />
							</station-line-number>
							<station-line-position>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_inventory_attr/detector-cat:detector_address/detector-cat:detector_nr" />
							</station-line-position>
							<xsl:if test="detector-cat:detector_publish/detector-cat:detector_location_attr/detector-cat:position_bps">
								<position-bps-code>
								<xsl:value-of select="detector-cat:detector_publish/detector-cat:detector_location_attr/detector-cat:position_bps" />
							</position-bps-code>
							</xsl:if>
						</detector-inventory-header>
						<detector-type>
							<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:equipment_type_used" />
						</detector-type>
						<measure-type>
							<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measure_type" />
						</measure-type>
						<accuracy>
							<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:accuracy" />
						</accuracy>
						<period>
							<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:period" />
						</period>
						<specific-lane>
							<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:lane" />
						</specific-lane>
						<vehicle-characteristics-list>
							<vehicle-characteristic>
								<vc-code>
									<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:vehicle_characteristics/detector-cat:vc_code" />
								</vc-code>
								<comparison>
									<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:vehicle_characteristics/detector-cat:comparison" />
								</comparison>
								<length>
									<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measurement/detector-cat:measurement_specific/detector-cat:vehicle_characteristics/detector-cat:length" />
								</length>
							</vehicle-characteristic>
						</vehicle-characteristics-list>
						<index>7</index>
					</detector>
				</detector-list>
				<outstation-configuration>
					<os-version>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:os_version" />
					</os-version>
					<detector-stations>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:detectorstations" />
					</detector-stations>
					<detectors>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:detectors" />
					</detectors>
					<detectors-speed-flow>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:detectors_sf" />
					</detectors-speed-flow>
					<speed-flow-enabled>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:sf_enabled" />
					</speed-flow-enabled>
					<msis>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:msis" />
					</msis>
					<base-aid>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:base_aid" />
					</base-aid>
					<msis-arg>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:msis_arg" />
					</msis-arg>
					<road-code>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:outstation_reference/detector-cat:road_code" />
					</road-code>
					<km-reference>
						<xsl:value-of select="detector-cat:detector_publish/detector-cat:outstation_inventory_attr/detector-cat:outstation_reference/detector-cat:km_reference" />
					</km-reference>
				</outstation-configuration>
				<detector-type>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:equipment_type_used" />
				</detector-type>
				<computation-method>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:computation_method" />
				</computation-method>
				<measurement-side>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measure_side" />
				</measurement-side>
				<detectors>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measure_site_number_of_lanes" />
				</detectors>
				<site-table-record-version>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:version" />
				</site-table-record-version>
				<site-table-version>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measure_site_table/detector-cat:version" />
				</site-table-version>
				<carriageway>
					<xsl:value-of select="detector-cat:detector_publish/detector-cat:measure_site_table_record/detector-cat:measure_site_location/detector-cat:carriageway" />
				</carriageway>
			</detector-inventory-item>
		</ns2:detectorInventoryMsg>
	</xsl:template>
</xsl:stylesheet>
