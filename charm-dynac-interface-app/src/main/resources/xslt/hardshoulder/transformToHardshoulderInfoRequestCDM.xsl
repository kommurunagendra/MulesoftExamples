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


	<xsl:template match="/tmdd:linkInventoryRequestMsg">
		<hardshoulder_info_request xmlns="http://hardshoulder.cdm.charm.cgi.com" version="1.0.0">
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
			<info_spec>
				<inventory>true</inventory>
				<status>false</status>
			</info_spec>
			<xsl:choose>
				<xsl:when test="roadway-network-id-list">
					<xsl:for-each select="roadway-network-id-list/roadway-network-id">
						<hardshoulder_spec>
							<hardshoulder_id>
								<id>
									<xsl:value-of select="."/>
								</id>
							</hardshoulder_id>
						</hardshoulder_spec>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<all_hardshoulders>true</all_hardshoulders>
				</xsl:otherwise>
			</xsl:choose>			
			<all_subscriptions>false</all_subscriptions>
		</hardshoulder_info_request>
	</xsl:template>
</xsl:stylesheet>
