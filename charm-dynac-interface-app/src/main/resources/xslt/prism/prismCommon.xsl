<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:prism="http://prism.cdm.charm.cgi.com" exclude-result-prefixes="#all">

	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="organization_id" />
	<xsl:template match="prism:prism_publish">

		<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organization_id" />
					</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="prism:prism_id/prism:id" />
				</device-id>
				<xsl:choose>
					<xsl:when test="prism:prism_status_attr">
						<xsl:choose>
							<xsl:when test="prism:prism_status_attr/prism:operating_mode='ok'">
								<device-status>on</device-status>
							</xsl:when>
							<xsl:when
								test="prism:prism_status_attr/prism:operating_mode='downgraded'">
								<device-status>marginal</device-status>
							</xsl:when>
							<xsl:when test="prism:prism_status_attr/prism:operating_mode='defect'">
								<device-status>failed</device-status>
							</xsl:when>
							<xsl:otherwise>
								<device-status>unknown</device-status>
							</xsl:otherwise>
						</xsl:choose>
						<last-comm-time>
							<xsl:call-template name="isodate_to_tmdd_date_no_ms">
								<xsl:with-param name="isoDateTimeInUtc"
									select="prism:prism_status_attr/prism:last_status_update" />
							</xsl:call-template>
						</last-comm-time>
						<device-type>cms sign</device-type>
					</xsl:when>
					<xsl:otherwise>
						<device-status>unknown</device-status>
						<last-comm-time>
							<date>19700101</date>
							<time>000000</time>
						</last-comm-time>
						<device-type>cms sign</device-type>
					</xsl:otherwise>
				</xsl:choose>
			</device-status-header>
			
			<xsl:choose>
				<xsl:when test="prism:prism_status_attr">
					<xsl:choose>
						<xsl:when test="prism:prism_status_attr/prism:prism_state_mnemonic">
							<current-message>
								<xsl:value-of
									select="prism:prism_status_attr/prism:prism_state_mnemonic" />
							</current-message>
						</xsl:when>
						<xsl:otherwise>
							<current-message>0</current-message>
						</xsl:otherwise>
					</xsl:choose>
					<message-number>
						<xsl:value-of select="prism:prism_status_attr/prism:prism_state_nr" />
					</message-number>
					
					<xsl:choose>
							<xsl:when test="fi:lower-case(prism:prism_status_attr/prism:prism_test)='ok'">
								<sign-test>on</sign-test>
							</xsl:when>
							<xsl:when
								test="fi:lower-case(prism:prism_status_attr/prism:prism_test)='not ok'">
								<sign-test>failed</sign-test>
							</xsl:when>
							<xsl:otherwise>
								<sign-test>unknown</sign-test>
							</xsl:otherwise>
						</xsl:choose>				
				</xsl:when>
				
				<xsl:otherwise>
					<current-message>0</current-message>
				</xsl:otherwise>
			</xsl:choose>
			
			
		</dms-status-item>
	</xsl:template>
</xsl:stylesheet>
	