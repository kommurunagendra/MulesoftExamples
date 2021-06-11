<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	xmlns:hardshoulder="http://hardshoulder.cdm.charm.cgi.com"
	exclude-result-prefixes="#all">

	<xsl:include href="../util/date_conversion.xsl" />

	<xsl:param name="message_uuid" />
	<xsl:param name="current_time_in_millis" />

	<xsl:variable name="message_created_date"
		select="fi:adjust-dateTime-to-timezone(xs:dateTime('1970-01-01T00:00:00') + xs:decimal($current_time_in_millis) * xs:dayTimeDuration('PT0.001S'), xs:dayTimeDuration('PT0H'))" />



	<xsl:template match="/tmdd:linkStatusUpdateMsg">

		<hardshoulder_status_publish xmlns="http://hardshoulder.cdm.charm.cgi.com"
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
			<subscription_id>
				<uuid>
					<xsl:value-of select="c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>

			<xsl:for-each select="link-status-list/link-status-item">

				<xsl:variable name="linkListVar" select="link-list" />
				<xsl:for-each select="link-list/link">
					<xsl:if test="normalize-space(alternate-link-name)=''">
						<hardshoulder_publish>
							<xsl:variable name="link_id" select="link-id" />
							<hardshoulder_id>
								<id>
									<xsl:value-of select="link-id" />
								</id>
							</hardshoulder_id>

							<hardshoulder_status_attr>
								<timestamp>
									<xsl:value-of
										select="concat(substring(last-update-time/date, 1, 4), '-', substring(last-update-time/date, 5, 2), '-', substring(last-update-time/date, 7, 2), 'T', substring(last-update-time/time, 1, 2) , ':' ,substring(last-update-time/time, 3, 2) , ':',substring(last-update-time/time, 5, 2),'Z' )" />
								</timestamp>
								<status>
									<xsl:variable name="linkStatusVar" select="link-status" />
									<xsl:choose>
										<xsl:when test="$linkStatusVar = 'open'">
											<xsl:text>open</xsl:text>
										</xsl:when>
										<xsl:when test="$linkStatusVar = 'partly open'">
											<xsl:text>partly_open</xsl:text>
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
											<xsl:text>unknown</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
								</status>
								<xsl:for-each select="$linkListVar/link">
									<xsl:if test="not(normalize-space(alternate-link-name)='')">
										<xsl:if test="alternate-link-name=$link_id">
											<segment_status>
												<segment_id>
													<id>
														<xsl:value-of select="link-id" />
													</id>
												</segment_id>
												<segment_status_attr>
													<timestamp>
														<xsl:value-of
															select="concat(substring(last-update-time/date, 1, 4), '-', substring(last-update-time/date, 5, 2), '-', substring(last-update-time/date, 7, 2), 'T', substring(last-update-time/time, 1, 2) , ':' ,substring(last-update-time/time, 3, 2) , ':',substring(last-update-time/time, 5, 2),'Z' )" />
													</timestamp>
													<status>
														<xsl:variable name="linkStatusVar" select="link-status" />
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
																<xsl:text>unknown</xsl:text>
															</xsl:otherwise>
														</xsl:choose>
													</status>
												</segment_status_attr>
											</segment_status>
										</xsl:if>
									</xsl:if>
								</xsl:for-each>
							</hardshoulder_status_attr>
						</hardshoulder_publish>
					</xsl:if>
				</xsl:for-each>
			</xsl:for-each>
		</hardshoulder_status_publish>
	</xsl:template>
</xsl:stylesheet>