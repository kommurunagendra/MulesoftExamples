<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<xsl:output method="xml" indent="no" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />
<xsl:include href="../util/date_conversion.xsl" />
		
		<xsl:variable name="message_create_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />
	
	<xsl:template match="/tmdd:dMSControlRequestMsg">
		<msi_control_request xmlns="http://msi.cdm.charm.cgi.com"
			version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
						<xsl:value-of select="$message_create_date" />
				</message_create_date>
			</meta>
				<msi_control>
					<msi_id>
						<id>
							<xsl:value-of select="device-control-request-header/device-id" />
						</id>
					</msi_id>
					<msi_control_attr>
						<control_timestamp>
						<xsl:call-template name="tmdd_date_to_isodate">
								<xsl:with-param name="tmddDate" select="device-control-request-header/command-request-time" />
							</xsl:call-template>
						</control_timestamp>
						<control_message_type>SET</control_message_type>
						<control_source><xsl:value-of select="fi:upper-case(control-source)" /></control_source>
						<msi_control_information>
							<image_mnemonic>
								<xsl:value-of select="dms-message" />
							</image_mnemonic>
						</msi_control_information>
						<msi_control_data/>
					</msi_control_attr>
				</msi_control>
		</msi_control_request>
	</xsl:template>
</xsl:stylesheet>
