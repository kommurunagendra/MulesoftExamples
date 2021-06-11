<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:msi="http://msi.cdm.charm.cgi.com" exclude-result-prefixes="#all">

	<xsl:param name="organization_id" />

	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:template match="msi:msi_publish">

		<dms-status-item xsi:type="tmdd:DMSDeviceStatusCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<device-status-header xsi:type="tmdd:DeviceStatusHeaderCharm"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<organization-information>
					<organization-id>
						<xsl:value-of select="$organization_id" />
					</organization-id>
				</organization-information>
				<device-id>
					<xsl:value-of select="msi:msi_id/msi:id" />
				</device-id>
				<xsl:choose>
				<xsl:when test="msi:msi_status_attr">
					<xsl:if test="msi:msi_status_attr/msi:msi_status_information">

						<xsl:choose>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:image_status='ok'">
								<device-status>on</device-status>
							</xsl:when>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:image_status='downgraded'">
								<device-status>marginal</device-status>
							</xsl:when>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:image_status='defect'">
								<device-status>failed</device-status>
							</xsl:when>
							<xsl:otherwise>
								<device-status>unavailable</device-status>
							</xsl:otherwise>
						</xsl:choose>


						<xsl:choose>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:msi_state='ok'">
								<device-comm-status>operational</device-comm-status>
							</xsl:when>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:msi_state='no_specification'">
								<device-comm-status>unknown</device-comm-status>
							</xsl:when>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:msi_state='no_communication'">
								<device-comm-status>offline</device-comm-status>
							</xsl:when>
							<xsl:when
								test="msi:msi_status_attr/msi:msi_status_information/msi:msi_state='fatal'">
								<device-comm-status>failed</device-comm-status>
							</xsl:when>
							<xsl:otherwise>
								<device-comm-status>unknown</device-comm-status>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					
						<last-comm-time>
					<xsl:call-template name="isodate_to_tmdd_date_no_ms">
						<xsl:with-param name="isoDateTimeInUtc"
							select="msi:msi_status_attr/msi:last_status_update" />
					</xsl:call-template>
				</last-comm-time>
			    

				</xsl:when>
				<xsl:otherwise>
						<device-status>unavailable</device-status>
						<device-comm-status>unknown</device-comm-status>
				</xsl:otherwise>
				</xsl:choose>
				
			
		
				<device-type>msi</device-type>


			</device-status-header>
		
				<current-message>
					<xsl:value-of
						select="msi:msi_status_attr/msi:msi_status_information/msi:image_mnemonic" />
				</current-message>

			<xsl:for-each select="msi:msi_error">
				<msi-error>
					<class><xsl:value-of select="msi:error_class"/></class>
					<subclass><xsl:value-of select="msi:error_subclass"/></subclass>
					<code><xsl:value-of select="msi:error_code"/></code>
					<description><xsl:value-of select="msi:error_description"/></description>
				</msi-error>
			</xsl:for-each>
		</dms-status-item>
	</xsl:template>
</xsl:stylesheet>