<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:drip="http://drip.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:param name="organization_id" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:template match="/drip:drip_info_reply">
		<tmdd:dMSStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
			<xsl:if test="drip:drip_publish/drip:drip_status_attr">
				<xsl:for-each select="drip:drip_publish">
					<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
						<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:value-of select="./drip:drip_id/drip:id" />
							</device-id>
							<device-status>
								<xsl:value-of
									select="./drip:drip_status_attr/drip:general_status/drip:device_status" />
							</device-status>
							<device-comm-status>
								<xsl:value-of
									select="./drip:drip_status_attr/drip:general_status/drip:device_comm_status" />
							</device-comm-status>
							<device-type>text dms</device-type>
						</device-status-header>
						<current-message>
						<xsl:for-each select="./drip:drip_image_attr/drip:text/drip:line">
							<xsl:if test="fi:position() != 1">[nl]</xsl:if>
							<xsl:for-each select="./drip:text_alignment">
								<xsl:analyze-string select="." regex="(\w+)">
									<xsl:matching-substring>
										<xsl:choose>
											<xsl:when test="regex-group(1) = 'other'">[jl1]</xsl:when>
											<xsl:when test="regex-group(1) = 'left'">[jl2]</xsl:when>
											<xsl:when test="regex-group(1) = 'center'">[jl3]</xsl:when>
											<xsl:when test="regex-group(1) = 'right'">[jl4]</xsl:when>
											<!--<xsl:if test="number(regex-group(3)) = 5">full</xsl:if> -->
											<!--<xsl:value-of select="fi:regex-group(1)"/> -->
										</xsl:choose>
									</xsl:matching-substring>
								</xsl:analyze-string>
							</xsl:for-each>
							<xsl:value-of select="./drip:text_specific" />
						</xsl:for-each>
						<xsl:for-each select="./drip:drip_image_attr/drip:pictogram">
							<xsl:value-of
								select="fi:concat('[g',./drip:pictogram_number,',',./drip:pictogram_index,']')" />
						</xsl:for-each>
					
					  </current-message>
					  <xsl:if test="./drip:drip_control_attr">
						<dms-control-attr>
							<operation-mode>
								<xsl:value-of select="./drip:drip_control_attr/drip:operation_mode" />
							</operation-mode>
							<dim-mode>
								<xsl:value-of
									select="./drip:drip_control_attr/drip:dim_status/drip:dim_mode" />
							</dim-mode>
							<dim-status>
								<xsl:value-of
									select="./drip:drip_control_attr/drip:dim_status/drip:dim_state" />
							</dim-status>
						</dms-control-attr>
					</xsl:if>
						<dms-status-attr>


							<climate-status>
						<xsl:choose>
							<xsl:when test="./drip:drip_status_attr/drip:general_status/drip:climate_mode = 'automatic'">1</xsl:when>
							<xsl:when test="./drip:drip_status_attr/drip:general_status/drip:climate_mode = 'manual'">2</xsl:when>
							<xsl:when test="./drip:drip_status_attr/drip:general_status/drip:climate_mode = 'automatic_manual'">3</xsl:when>
							<xsl:when test="./drip:drip_status_attr/drip:general_status/drip:climate_mode = 'none'">0</xsl:when>
							<xsl:otherwise>0</xsl:otherwise>
						</xsl:choose>
					</climate-status>
							<xsl:choose>
								<xsl:when test="./drip:drip_status_attr/drip:display_status">
									<display-status>
										<display-mode>
										<xsl:choose>
											<xsl:when test="./drip:drip_status_attr/drip:display_status/drip:display_mode">1</xsl:when>
											<xsl:otherwise>0</xsl:otherwise>
										</xsl:choose>
									</display-mode>
										<display-state>
											<xsl:value-of
												select="./drip:drip_status_attr/drip:display_status/drip:display_state" />
										</display-state>
										<flasher-state>
											<xsl:choose>
												<xsl:when
													test="./drip:drip_status_attr/drip:display_status/drip:flasher_state = 'not_available'">
													<xsl:value-of select="'not available'" />
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of
														select="./drip:drip_status_attr/drip:display_status/drip:flasher_state" />
												</xsl:otherwise>
											</xsl:choose>
										</flasher-state>
										<pictogram-state>
											<xsl:choose>
												<xsl:when
													test="./drip:drip_status_attr/drip:display_status/drip:pictogram_state = 'not_available'">
													<xsl:value-of select="'not available'" />
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of
														select="./drip:drip_status_attr/drip:display_status/drip:pictogram_state" />
												</xsl:otherwise>
											</xsl:choose>
										</pictogram-state>
									</display-status>
								</xsl:when>
								<xsl:otherwise>
									<display-status>
										<display-mode>0</display-mode>
										<display-state>false</display-state>
										<flasher-state>not available</flasher-state>
										<pictogram-state>not available</pictogram-state>
									</display-status>
								</xsl:otherwise>
							</xsl:choose>
						<xsl:if test="./drip:drip_status_attr/drip:other_information">
							<other-information>
								<drip-type>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:drip_type" />
								</drip-type>
								<serial-number>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:serial_number" />
								</serial-number>
								<drip-version>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:drip_version" />
								</drip-version>
								<location-code>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:location_code" />
								</location-code>
								<protocol-config>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:protocol_version" />
								</protocol-config>
								<font-name>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:font_name" />
								</font-name>
								<regel-config>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:regel_config" />
								</regel-config>
								<temperature>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:temperature" />
								</temperature>
								<ventilator-status>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:ventilator_status" />
								</ventilator-status>
							</other-information>
						</xsl:if>
							<xsl:if test="count(drip:drip_status_attr/drip:door_status) &gt; 0 ">
								<door-status-list>
									<xsl:for-each select="drip:drip_status_attr/drip:door_status">
										<dms-door>
											<door-nr>
												<xsl:value-of select="drip:door_number" />
											</door-nr>
											<door-state>
												<xsl:value-of select="drip:door_open" />
											</door-state>
										</dms-door>
									</xsl:for-each>
								</door-status-list>
							</xsl:if>

							<test-result>
								<heater-circuit-failed>false</heater-circuit-failed>
								<internal-communication-failed>false
								</internal-communication-failed>
								<power-supply-failed>false</power-supply-failed>
								<watchdog-reset>false</watchdog-reset>
								<high-temperature>false</high-temperature>
								<!--Optional: -->
								<selftest-start-time>
									<xsl:value-of
										select="fi:substring(./drip:drip_status_attr/drip:other_information/drip:selftest_start_time,1,10)" />
								</selftest-start-time>
								<!--Optional: -->
								<selftest-end-time>
									<xsl:value-of
										select="fi:substring(./drip:drip_status_attr/drip:other_information/drip:selftest_end_time,1,10)" />
								</selftest-end-time>
								<!--Optional: -->
								<watchdog-reset-counter>
									<xsl:value-of
										select="./drip:drip_status_attr/drip:other_information/drip:watchdog_reset_counter" />
								</watchdog-reset-counter>
							</test-result>
						</dms-status-attr>
					</dms-status-item>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="not(drip:drip_publish/drip:drip_status_attr)">
				<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm">
						<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm">
							<organization-information>
								<organization-id>
									<xsl:value-of select="$organization_id" />
								</organization-id>
							</organization-information>
							<device-id>
								<xsl:choose>
									<xsl:when test="drip:drip_publish/drip:drip_id/drip:id">
										<xsl:value-of select="drip:drip_publish/drip:drip_id/drip:id" />
									</xsl:when>
									<xsl:otherwise>unknown</xsl:otherwise>
								</xsl:choose>
							</device-id>
							<device-status>unknown</device-status>
					</device-status-header>
					<current-message />
				</dms-status-item>
			</xsl:if>
		</tmdd:dMSStatusMsg>
		
	</xsl:template>
</xsl:stylesheet>