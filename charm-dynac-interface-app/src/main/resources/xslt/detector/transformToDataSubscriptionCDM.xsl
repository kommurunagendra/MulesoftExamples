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

	<xsl:template match="tmdd:deviceInformationSubscriptionMsg">
		<detector_si_data_subscription xmlns="http://detector.si.cdm.charm.cgi.com" version="1.0.0">
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
			<subscription_config>
				<subscription_id>
					<uuid>
						<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
					</uuid>
				</subscription_id>
				<subscription_attr>
					<subscription_name>
						<xsl:value-of select="c2cMsgAdmin/subscriptionName" />
					</subscription_name>
					<subscription_type>data</subscription_type>
				</subscription_attr>
				<all_feps>false</all_feps>
				<all_outstations>false</all_outstations>
				<xsl:choose>
					<xsl:when test="device-filter">
						<xsl:for-each select="device-filter/device-id-list/device-id">
							<detector_spec>
								<detector_id>
									<id>
										<xsl:value-of select="." />
									</id>
								</detector_id>
							</detector_spec>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<all_detectors>true</all_detectors>
					</xsl:otherwise>
				</xsl:choose>
			</subscription_config>
		</detector_si_data_subscription>
	</xsl:template>
</xsl:stylesheet>
