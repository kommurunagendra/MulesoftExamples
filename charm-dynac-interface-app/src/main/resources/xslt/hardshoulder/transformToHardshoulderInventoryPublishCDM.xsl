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


	<xsl:template match="/">
		<hardshoulder_inventory_publish
			xmlns="http://hardshoulder.cdm.charm.cgi.com" version="1.0.0">
			<meta>
				<message_id>
					<uuid>
						<xsl:value-of select="$message_uuid" />
					</uuid>
				</message_id>
				<message_create_date>
					<xsl:value-of select="$message_created_date"></xsl:value-of>
					<!-- <xsl:call-template name="tmdd_date_to_isodate"> <xsl:with-param 
						name="tmddDate" select="$current_time_in_millis" /> </xsl:call-template> -->
				</message_create_date>
			</meta>

			<subscription_id>
				<uuid>
					<xsl:value-of
						select="tmdd:linkInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
				</uuid>
			</subscription_id>
			<inventory_config>
				<xsl:choose>
					<xsl:when
						test="tmdd:linkInventoryUpdateMsg/c2cMsgAdmin/informationalText = 'FULLSYNC'">
						<full_sync>true</full_sync>
					</xsl:when>
					<xsl:otherwise>
						<full_sync>false</full_sync>
					</xsl:otherwise>
				</xsl:choose>
				<inventory_id>
					<uuid>
						<xsl:value-of
							select="tmdd:linkInventoryUpdateMsg/c2cMsgAdmin/subscriptionID" />
					</uuid>
				</inventory_id>

				<xsl:for-each
					select="tmdd:linkInventoryUpdateMsg/link-inventory-list/link-inventory-item">
					<xsl:variable name="par_node" select="." />
					<xsl:for-each select="link-list/link">
						<xsl:if test="normalize-space(alternate-link-name)=''">
							<hardshoulder_inventory>
								<xsl:variable name="link_id" select="link-id" />
								<action>
									<xsl:value-of select="inventory-action" />
								</action>
								<hardshoulder_id>
									<id>
										<xsl:value-of select="link-id" />
									</id>
								</hardshoulder_id>
								<hardshoulder_inventory_attr>
									<name>
										<xsl:value-of select="link-name" />
									</name>
									<description>
										<xsl:value-of select="link-description" />
									</description>
								</hardshoulder_inventory_attr>
								<hardshoulder_location_attr>
									<start_position>
										
											<position_bps>
												<xsl:value-of select="link-begin-node-id" />
											</position_bps>
									
										<position_wgs84>
											<latitude>
												<xsl:value-of select="link-begin-node-location/latitude" />
											</latitude>
											<longitude>
												<xsl:value-of select="link-begin-node-location/longitude" />
											</longitude>
										</position_wgs84>
									</start_position>
									<end_position>
										
											<position_bps>
												<xsl:value-of select="link-end-node-id" />
											</position_bps>
										
										<position_wgs84>
											<latitude>
												<xsl:value-of select="link-end-node-location/latitude" />
											</latitude>
											<longitude>
												<xsl:value-of select="link-end-node-location/longitude" />
											</longitude>
										</position_wgs84>
									</end_position>
								</hardshoulder_location_attr>



								<!-- Segment Inventory -->

								<xsl:for-each select="$par_node/link-list/link">
									<xsl:if test="not(normalize-space(alternate-link-name)='')">
										<xsl:if test="alternate-link-name=$link_id">
											<segment_inventory>
												<segment_id>
													<id>
														<xsl:value-of select="link-id" />
													</id>
												</segment_id>
												<segment_inventory_attr>
													<name>
														<xsl:value-of select="link-name" />
													</name>
													<description>
														<xsl:value-of select="link-description" />
													</description>
												</segment_inventory_attr>
												<segment_location_attr>
													<start_position>
														<position_bps>
															<xsl:value-of select="link-begin-node-id" />
														</position_bps>
														<position_wgs84>
															<latitude>
																<xsl:value-of select="link-begin-node-location/latitude" />
															</latitude>
															<longitude>
																<xsl:value-of select="link-begin-node-location/longitude" />
															</longitude>
														</position_wgs84>
													</start_position>
													<end_position>
														<position_bps>
															<xsl:value-of select="link-end-node-id" />
														</position_bps>
														<position_wgs84>
															<latitude>
																<xsl:value-of select="link-end-node-location/latitude" />
															</latitude>
															<longitude>
																<xsl:value-of select="link-end-node-location/longitude" />
															</longitude>
														</position_wgs84>
													</end_position>
												</segment_location_attr>
											</segment_inventory>
										</xsl:if>
									</xsl:if>
								</xsl:for-each>
							</hardshoulder_inventory>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>
			</inventory_config>
		</hardshoulder_inventory_publish>
	</xsl:template>
</xsl:stylesheet>
