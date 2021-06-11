<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:ns3="http://www.ntcip.org/c2c-message-administration"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis"/>
	<xsl:variable name="message_created_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))"/>

	<xsl:template match="tmdd:deviceInformationRequestMsg">
		<detector_si_info_request xmlns="http://detector.si.cdm.charm.cgi.com" version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_created_date"/>
				</message_create_date>
			</meta>
			<info_spec>
				<inventory>false</inventory>
				<status>true</status>
				<data>false</data>
			</info_spec>
			<!-- TODO: How to differentiate between different(detector/fep/os) devices?? -->
		<!-- TODO: How to differentiate between different(detector/fep/os) devices?? -->
			 <xsl:choose>
				<xsl:when test="device-filter">
					<xsl:for-each select="device-filter/device-id-list/device-id">
						<fep_spec>
							<fep_id>
								<id>
									<xsl:value-of select="." />
								</id>
							</fep_id>
						</fep_spec>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<all_feps>true</all_feps>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="device-filter">
					<xsl:for-each select="device-filter/device-id-list/device-id">
						<outstation_spec>
							<outstation_id>
								<id>
									<xsl:value-of select="." />
								</id>
							</outstation_id>
						</outstation_spec>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<all_outstations>true</all_outstations>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="device-filter">
					<xsl:for-each select="device-filter/device-id-list/device-id">
						<detector_spec>
							<detector_id>
								<id>
									<xsl:value-of select="."/>
								</id>
							</detector_id>
						</detector_spec>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<all_detectors>true</all_detectors>
				</xsl:otherwise>
			</xsl:choose>		
			<all_subscriptions>false</all_subscriptions>
		</detector_si_info_request>
	</xsl:template>
</xsl:stylesheet>
