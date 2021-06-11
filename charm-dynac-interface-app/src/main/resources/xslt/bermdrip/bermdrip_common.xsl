<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:bdrp="http://bermdrip.cdm.charm.cgi.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="#all">
	<xsl:strip-space elements="*" />
	<xsl:template match="bdrp:bermdrip_publish">
		<xsl:variable name="id" select="bdrp:bermdrip_id/bdrp:id" />
		<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
			<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
				<organization-information>
					<organization-id>RWS</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="$id" />
				</device-id>
				<device-status>
					<xsl:value-of
						select="bdrp:bermdrip_status_attr/bdrp:general_status/bdrp:device_status" />
				</device-status>
				<device-comm-status>
					<xsl:value-of
						select="bdrp:bermdrip_status_attr/bdrp:general_status/bdrp:device_comm_status" />
				</device-comm-status>
				<device-type>graphical dms</device-type>
			</device-status-header>
			<current-message />
			<xsl:if test="bdrp:bermdrip_control_attr">
				<dms-control-attr>

					<operation-mode>
						<xsl:value-of select="bdrp:bermdrip_control_attr/bdrp:operation_mode" />
					</operation-mode>
					<dim-mode>
						<xsl:value-of
							select="bdrp:bermdrip_control_attr/bdrp:dim_status/bdrp:dim_mode" />
					</dim-mode>
					<dim-status>
						<xsl:value-of
							select="bdrp:bermdrip_control_attr/bdrp:dim_status/bdrp:dim_level" />
					</dim-status>
				</dms-control-attr>
			</xsl:if>
			<xsl:if test="bdrp:bermdrip_status_attr">
				<dms-status-attr>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:status_result">
						<status-result>
							<minor-led-failure>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:status_result/bdrp:minor_led_failure" />
							</minor-led-failure>
							<critical-led-failure>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:status_result/bdrp:critical_led_failure" />
							</critical-led-failure>
						</status-result>
					</xsl:if>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:sign_status">
						<sign-status>
							<signal-address>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:signal_address" />
							</signal-address>
							<message-address>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:message_address" />
							</message-address>
							<type>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:type" />
							</type>
							<luminance-state>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:luminance_state" />
							</luminance-state>
							<luminance-level>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:luminance_level" />
							</luminance-level>
							<temperature>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:temperature" />
							</temperature>
							<animation-state>
								<xsl:value-of select="bdrp:bermdrip_status_attr/bdrp:bermdrip_image_attr/bdrp:animation_state" />
							</animation-state>
							<animation-delay>
								<xsl:value-of select="bdrp:bermdrip_status_attr/bdrp:bermdrip_image_attr/bdrp:animation_delay" />
							</animation-delay>
							<offline-operational-time>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:sign_status/bdrp:offline_operational_time" />
							</offline-operational-time>
						</sign-status>
					</xsl:if>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:vehicle_information">
						<vehicle-information>
							<vehicle-number>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:vehicle_number" />
							</vehicle-number>
							<petrol-level>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:petrol_level" />
							</petrol-level>
							<power-voltage>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:power_voltage" />
							</power-voltage>
							<power-voltage-to-low>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:power_voltage_too_low" />
							</power-voltage-to-low>
							<power-voltage-very-low>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:power_voltage_very_low" />
							</power-voltage-very-low>
							<fuse-defect>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:fuse_defect" />
							</fuse-defect>
							<overvoltage-protection-enabled>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:overvoltage_protection_enabled" />
							</overvoltage-protection-enabled>
							<petrol-level-to-low>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:petrol_level_too_low" />
							</petrol-level-to-low>
							<power-generator-on>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:power_generator_on" />
							</power-generator-on>
							<ventilator-on>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:ventilator_on" />
							</ventilator-on>
							<heating-on>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:heating_on" />
							</heating-on>
							<internal-lighting-on>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:internal_lighting_on" />
							</internal-lighting-on>
							<power-present>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:power_present" />
							</power-present>
							<moisture-present>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_information/bdrp:moisture_present" />
							</moisture-present>
						</vehicle-information>
					</xsl:if>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:vehicle_position">
						<vehicle-position>
							<latitude>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_position/bdrp:latitude" />
							</latitude>
							<longitude>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:vehicle_position/bdrp:longitude" />
							</longitude>
						</vehicle-position>
					</xsl:if>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:door_status">
						<door-status-list>
							<xsl:for-each select="bdrp:bermdrip_status_attr/bdrp:door_status">
								<dms-door>
									<door-nr>
										<xsl:value-of select="bdrp:door_number" />
									</door-nr>
									<door-state>
										<xsl:value-of select="bdrp:door_open" />
									</door-state>
								</dms-door>
							</xsl:for-each>
						</door-status-list>
					</xsl:if>
					<xsl:if test="bdrp:bermdrip_status_attr/bdrp:test_result">
						<test-result>
							<heater-circuit-failed>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:test_result/bdrp:heater_circuit_failed" />
							</heater-circuit-failed>
							<internal-communication-failed>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:test_result/bdrp:internal_communication_failed" />
							</internal-communication-failed>
							<power-supply-failed>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:test_result/bdrp:power_supply_failed" />
							</power-supply-failed>
							<watchdog-reset>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:test_result/bdrp:watchdog_reset" />
							</watchdog-reset>
							<high-temperature>
								<xsl:value-of
									select="bdrp:bermdrip_status_attr/bdrp:test_result/bdrp:high_temperature" />
							</high-temperature>
						</test-result>
					</xsl:if>
				</dms-status-attr>
			</xsl:if>
			<xsl:choose>
				<xsl:when
					test="count(bdrp:bermdrip_status_attr/bdrp:bermdrip_image_attr/bdrp:images/bdrp:image) &gt; 0">
					<xsl:for-each select="bdrp:bermdrip_status_attr/bdrp:bermdrip_image_attr/bdrp:images/bdrp:image">
					<dms-graphics>
							<graphic-id>
								<xsl:value-of select="bdrp:image_number" />
							</graphic-id>
							<bitmap>
								<xsl:value-of select="bdrp:image_binary" />
							</bitmap>
							<height><xsl:value-of select="bdrp:image_height"/></height>
							<width><xsl:value-of select="bdrp:image_width"/></width>
					</dms-graphics>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</dms-status-item>
	</xsl:template>

</xsl:stylesheet>