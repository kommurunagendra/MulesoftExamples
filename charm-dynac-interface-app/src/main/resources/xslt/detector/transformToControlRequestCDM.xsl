<?xml version="1.0" encoding="UTF-8"?>
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

	<xsl:template match="/tmdd:detectorControlRequestMsg">
		<detector_si_control_request xmlns="http://detector.si.cdm.charm.cgi.com"
			version="1.0.0">
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
			<control>

				<xsl:choose>
					<xsl:when test="boolean(detector-mode)">
						<xsl:variable name="detectorModeVar" select="detector-mode" />
						<xsl:choose>
<!-- FEP control attributes -->
							<xsl:when
								test="$detectorModeVar='listen' or $detectorModeVar='normal' or $detectorModeVar='shutdown' or $detectorModeVar='standbye' or $detectorModeVar='recover'">
								<fep_control>
									<fep_id>
										<id>
											<xsl:value-of select="device-control-request-header/device-id" />
										</id>
									</fep_id>
									<fep_control_attr>
										<control_timestamp>
											<xsl:call-template name="tmdd_date_to_isodate">
												<xsl:with-param name="tmddDate"
													select="device-control-request-header/command-request-time" />
											</xsl:call-template>
										</control_timestamp>
										<fep_mode>
											<xsl:value-of select="detector-mode" />
										</fep_mode>
										<!-- xsl:value-of select="detector-mode" / -->

									</fep_control_attr>
								</fep_control>
							</xsl:when>
<!-- outstation control attributes -->

							<xsl:otherwise>
								<outstation_control>
									<outstation_id>
										<id>
											<xsl:value-of select="device-control-request-header/device-id" />
										</id>
									</outstation_id>
									<outstation_control_attr>
										<control_timestamp>
											<xsl:call-template name="tmdd_date_to_isodate">
												<xsl:with-param name="tmddDate"
													select="device-control-request-header/command-request-time" />
											</xsl:call-template>
										</control_timestamp>
										<os_mode>
											<xsl:value-of select="detector-mode" />
										</os_mode>
										<dim_state>
											<xsl:value-of select="dim-state" />
										</dim_state>
										<dynamic_max_speed>
											<xsl:value-of select="dynamic-max-speed" />
										</dynamic_max_speed>
										<measure_number>
											<xsl:value-of select="measure-number" />
										</measure_number>
									</outstation_control_attr>
								</outstation_control>
							</xsl:otherwise>
						</xsl:choose>

					</xsl:when>
					
					<!-- detector control attributes -->

					<xsl:otherwise>
						<detector_control>
							<detector_id>
										<id>
											<xsl:value-of select="device-control-request-header/device-id" />
										</id>
									</detector_id>
									<detector_control_attr>
										<control_timestamp>
											<xsl:call-template name="tmdd_date_to_isodate">
												<xsl:with-param name="tmddDate"
													select="device-control-request-header/command-request-time" />
											</xsl:call-template>
										</control_timestamp>
										<detector_state_legend>
											<xsl:value-of select="state/legend" />
										</detector_state_legend>
										<detector_state_opa><xsl:value-of select="state/opa" /></detector_state_opa>
								</detector_control_attr>
						
						</detector_control>
					</xsl:otherwise>
				</xsl:choose>


			</control>
		</detector_si_control_request>
	</xsl:template>
</xsl:stylesheet>
