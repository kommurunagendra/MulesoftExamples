<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">
	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />

	<xsl:variable name="bericht_aanmaak"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />

	<xsl:template match="/tmdd:dMSStatusRequestMsg">
		<prism_info_request xmlns="http://prism.cdm.charm.cgi.com" version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$bericht_aanmaak" />
				</message_create_date>
			</meta>
			<info_spec>
				<inventory>false</inventory>
				<status>true</status>
				<library>false</library>
			</info_spec>
			<xsl:choose>
				<xsl:when test="device-filter/device-id-list">
					<xsl:for-each select="device-filter/device-id-list/device-id">
						<prism_spec>
							<prism_id>
								<id>
									<xsl:value-of select="." />
								</id>
							</prism_id>
						</prism_spec>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<all_prisms>true</all_prisms>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="device-filter/data-owner">
					<owner>
						<xsl:value-of select="device-filter/data-owner" />
					</owner>
				</xsl:when>
				<xsl:otherwise>
					<owner>all</owner>
				</xsl:otherwise>
			</xsl:choose>
			<all_subscriptions>false</all_subscriptions>
		</prism_info_request>
	</xsl:template>
</xsl:stylesheet>