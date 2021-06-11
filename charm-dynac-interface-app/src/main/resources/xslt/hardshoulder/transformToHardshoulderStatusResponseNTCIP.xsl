<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:c2c="http://www.ntcip.org/c2c-message-administration"
	xmlns:hardshoulder="http://hardshoulder.cdm.charm.cgi.com" xmlns:tmdd="http://www.tmdd.org/3/messages">

	<xsl:output method="xml" indent="no" />
	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:param name="message_uuid" />
	<xsl:param name="organization_id" />
	<xsl:param name="current_time_in_millis" />

	<xsl:template match="/hardshoulder:hardshoulder_info_reply">
		<tmdd:linkStatusMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
	      <xsl:if test="hardshoulder:hardshoulder_publish">
			<xsl:for-each select="hardshoulder:hardshoulder_publish">
				<link-status-item>

					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>

					<link-list>
						<link xsi:type="tmdd:LinkStatusListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<network-id>
								<xsl:value-of select="hardshoulder:hardshoulder_id/hardshoulder:id" />
							</network-id>
							<link-id>
								<xsl:value-of select="hardshoulder:hardshoulder_id/hardshoulder:id" />
							</link-id>
						   <xsl:if test="hardshoulder:hardshoulder_status_attr">
							<link-status>
								<xsl:variable name="linkStatusVar"
									select="hardshoulder:hardshoulder_status_attr/hardshoulder:status" />
								<xsl:choose>
									<xsl:when test="$linkStatusVar = 'open'">
										<xsl:text>open</xsl:text>
									</xsl:when>
									<xsl:when test="$linkStatusVar = 'partly_open'">
										<xsl:text>partly open</xsl:text>
									</xsl:when>
									<xsl:when test="$linkStatusVar = 'closed'">
										<xsl:text>closed</xsl:text>
									</xsl:when>
									<xsl:when test="$linkStatusVar = 'opening'">
										<xsl:text>opening</xsl:text>
									</xsl:when>
									<xsl:when test="$linkStatusVar = 'closing'">
										<xsl:text>closing</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>no determination</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</link-status>

							<last-update-time>
								<xsl:call-template name="isodate_to_tmdd_date">
									<xsl:with-param name="isoDateTimeInUtc"
										select="hardshoulder:hardshoulder_status_attr/hardshoulder:timestamp" />
								</xsl:call-template>
							</last-update-time>
							<traffic-network-type>hard shoulder status</traffic-network-type>
						  </xsl:if>
						  <xsl:if test="not(hardshoulder:hardshoulder_status_attr)">
						  	<link-status>no determination</link-status>
						  </xsl:if>
						</link>
				
						<!-- Segments -->
						<xsl:if test="hardshoulder:hardshoulder_status_attr">
						<xsl:for-each
							select="hardshoulder:hardshoulder_status_attr/hardshoulder:segment_status">

							<link xsi:type="tmdd:LinkStatusListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
								<network-id>
									<xsl:value-of select="hardshoulder:segment_id/hardshoulder:id" />
								</network-id>
								<link-id>
									<xsl:value-of select="hardshoulder:segment_id/hardshoulder:id" />
								</link-id>

								<link-status>
									<xsl:variable name="linkStatusVar"
										select="hardshoulder:segment_status_attr/hardshoulder:status" />
									<xsl:choose>
										<xsl:when test="$linkStatusVar = 'open'">
											<xsl:text>open</xsl:text>
										</xsl:when>
										<xsl:when test="$linkStatusVar = 'closed'">
											<xsl:text>closed</xsl:text>
										</xsl:when>
										<xsl:when test="$linkStatusVar = 'opening'">
											<xsl:text>opening</xsl:text>
										</xsl:when>
										<xsl:when test="$linkStatusVar = 'closing'">
											<xsl:text>closing</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<xsl:text>no determination</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
								</link-status>
								
									<last-update-time>
										<xsl:call-template name="isodate_to_tmdd_date">
											<xsl:with-param name="isoDateTimeInUtc"
												select="hardshoulder:segment_status_attr/hardshoulder:timestamp" />
										</xsl:call-template>
								</last-update-time>
								
								<!-- added condition here , because hardshoulder status response 
									will not have inventory_attr element populated -->
								
								<traffic-network-type>hard shoulder status</traffic-network-type>
							</link>
						</xsl:for-each>
					 </xsl:if>
					</link-list>
				</link-status-item>
			</xsl:for-each>
			</xsl:if>
			<xsl:if test="not(hardshoulder:hardshoulder_publish)">
				<link-status-item>
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<link-list>
						<link xsi:type="tmdd:LinkStatusListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<network-id>unknown</network-id>
							<link-id>unknown</link-id>
							<link-status>closing</link-status>
						</link>
					</link-list>
				</link-status-item>
			</xsl:if>
		</tmdd:linkStatusMsg>
	</xsl:template>
</xsl:stylesheet>
