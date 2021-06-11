<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:weather="http://weather.cdm.charm.cgi.com"
	xmlns:tmdd="http://www.tmdd.org/3/messages" exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />

	<xsl:param name="organizationId" />

	<xsl:template match="/weather:weather_status_publish">
		<tmdd:eSSObservationReportUpdateMsg>
			<c2cMsgAdmin>
				<subscriptionID>
					<xsl:value-of select="weather:subscription_id/weather:uuid" />
				</subscriptionID>
			</c2cMsgAdmin>
			<xsl:apply-templates select="weather:weather_publish" />
		</tmdd:eSSObservationReportUpdateMsg>
	</xsl:template>

	<xsl:template match="weather:weather_publish">
		<ess-observation-report-item>
				<organization-information>
				<organization-id>
					<xsl:value-of select="$organizationId" />
				</organization-id>
			</organization-information>
			<device_type>environmental sensor station</device_type>
			<station-id>
				<xsl:value-of select="weather:weather_id/weather:id" />
			</station-id>
			<ess-sensor-list>
				<ess-sensor xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					xsi:type="tmdd:ESSObservationReportDetailCharm">
					<ess-sensor-id>
						<xsl:value-of select="weather:weather_id/weather:id" />
					</ess-sensor-id>
					<ess-observation-timestamp>
						<date>
							<xsl:value-of
								select="concat(substring(weather:weather_status_attr/weather:timestamp, 1, 4),substring(weather:weather_status_attr/weather:timestamp, 6, 2),substring(weather:weather_status_attr/weather:timestamp, 9, 2))" />
						</date>
						<time>
							<xsl:value-of
								select="concat(substring(weather:weather_status_attr/weather:timestamp, 12, 2),substring(weather:weather_status_attr/weather:timestamp, 15, 2),substring(weather:weather_status_attr/weather:timestamp, 18, 2),0,0,0)" />
						</time>
					</ess-observation-timestamp>
					<ess-observation-type>
						<pavement-data>
							<surface-temperature>0</surface-temperature>
							<pavement-temperature>0</pavement-temperature>
						</pavement-data>
					</ess-observation-type>
					<ess-data-set-file-name>
						<xsl:value-of select="weather:weather_status_attr/weather:filename" />
					</ess-data-set-file-name>
					<ess-data-set-file-directory-path>
						<xsl:value-of select="weather:weather_status_attr/weather:path" />
					</ess-data-set-file-directory-path>
				</ess-sensor>
			</ess-sensor-list>
		</ess-observation-report-item>
	</xsl:template>
</xsl:stylesheet>