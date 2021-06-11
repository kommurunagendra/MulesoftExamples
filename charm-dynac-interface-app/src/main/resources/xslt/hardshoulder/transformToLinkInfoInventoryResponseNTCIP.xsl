
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:hardshoulder="http://hardshoulder.cdm.charm.cgi.com">

	<xsl:include href="../util/date_conversion.xsl" />
	<xsl:param name="message_uuid" />
	<xsl:param name="organization_id" />
	<xsl:template match="/hardshoulder:hardshoulder_info_reply">
		<tmdd:linkInventoryMsg xmlns:tmdd="http://www.tmdd.org/3/messages">
		  <xsl:if test="hardshoulder:hardshoulder_publish">
			<xsl:for-each select="hardshoulder:hardshoulder_publish">
				<link-inventory-item>
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<link-list>
						<link xsi:type="tmdd:LinkInventoryListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<network-id>
								<xsl:value-of select="hardshoulder:hardshoulder_id/hardshoulder:id" />
							</network-id>
							<link-id>
								<xsl:value-of select="hardshoulder:hardshoulder_id/hardshoulder:id" />
							</link-id>
							<link-name>
								<xsl:value-of
									select="hardshoulder:hardshoulder_inventory_attr/hardshoulder:name" />
							</link-name>
							<link-type>unknown</link-type>

							<xsl:if
								test="not(hardshoulder:hardshoulder_location_attr/hardshoulder:start_position/hardshoulder:position_bps)">
								<link-begin-node-id>0</link-begin-node-id>
							</xsl:if>
							<xsl:if
								test="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_bps">
								<link-begin-node-id>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_bps" />
								</link-begin-node-id>
							</xsl:if>

							<link-begin-node-location>
								<latitude>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:start_position/hardshoulder:position_wgs84/hardshoulder:latitude" />
								</latitude>
								<longitude>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:start_position/hardshoulder:position_wgs84/hardshoulder:longitude" />
								</longitude>
							</link-begin-node-location>

							<xsl:if
								test="not(hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_bps)">
								<link-end-node-id>0</link-end-node-id>
							</xsl:if>
							<xsl:if
								test="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_bps">
								<link-end-node-id>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_bps" />
								</link-end-node-id>
							</xsl:if>

							<link-end-node-location>
								<latitude>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_wgs84/hardshoulder:latitude" />
								</latitude>
								<longitude>
									<xsl:value-of
										select="hardshoulder:hardshoulder_location_attr/hardshoulder:end_position/hardshoulder:position_wgs84/hardshoulder:longitude" />
								</longitude>
							</link-end-node-location>

							<xsl:if
								test="hardshoulder:hardshoulder_inventory_attr/hardshoulder:description">
								<link-description>
									<xsl:value-of
										select="hardshoulder:hardshoulder_inventory_attr/hardshoulder:description" />
								</link-description>
							</xsl:if>
							<network-info-type>hard shoulder inventory</network-info-type>
						</link>

						<!-- Segments -->
						<xsl:for-each select="hardshoulder:segment_inventory">
							<link xsi:type="tmdd:LinkInventoryListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
								<network-id>
									<xsl:value-of select="hardshoulder:segment_id/hardshoulder:id" />
								</network-id>
								<link-id>
									<xsl:value-of select="hardshoulder:segment_id/hardshoulder:id" />
								</link-id>
								<link-name>
									<xsl:value-of
										select="hardshoulder:segment_inventory_attr/hardshoulder:name" />
								</link-name>
								<link-type>unknown</link-type>
								<xsl:if
									test="not(hardshoulder:segment_location_attr/hardshoulder:start_position/hardshoulder:position_bps)">
									<link-begin-node-id>0</link-begin-node-id>
								</xsl:if>
								<xsl:if
									test="hardshoulder:segment_location_attr/hardshoulder:start_position/hardshoulder:position_bps">
									<link-begin-node-id>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:start_position/hardshoulder:position_bps" />
									</link-begin-node-id>
								</xsl:if>
								<link-begin-node-location>
									<latitude>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:start_position/hardshoulder:position_wgs84/hardshoulder:latitude" />
									</latitude>
									<longitude>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:start_position/hardshoulder:position_wgs84/hardshoulder:longitude" />
									</longitude>
								</link-begin-node-location>

								<xsl:if
									test="not(hardshoulder:segment_location_attr/hardshoulder:end_position/hardshoulder:position_bps)">
									<link-end-node-id>0</link-end-node-id>
								</xsl:if>
								<xsl:if
									test="hardshoulder:segment_location_attr/hardshoulder:end_position/hardshoulder:position_bps">
									<link-end-node-id>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:end_position/hardshoulder:position_bps" />
									</link-end-node-id>
								</xsl:if>
								<link-end-node-location>
									<latitude>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:end_position/hardshoulder:position_wgs84/hardshoulder:latitude" />
									</latitude>
									<longitude>
										<xsl:value-of
											select="hardshoulder:segment_location_attr/hardshoulder:end_position/hardshoulder:position_wgs84/hardshoulder:longitude" />
									</longitude>
								</link-end-node-location>

								<xsl:if
									test="hardshoulder:segment_inventory_attr/hardshoulder:description">
									<link-description>
										<xsl:value-of
											select="hardshoulder:segment_inventory_attr/hardshoulder:description" />
									</link-description>
								</xsl:if>
								<network-info-type>hard shoulder inventory</network-info-type>
							</link>
						</xsl:for-each>
					</link-list>
				</link-inventory-item>
			</xsl:for-each>
		  </xsl:if>
		  <xsl:if test="not(hardshoulder:hardshoulder_publish)">
		  	<link-inventory-item>
					<organization-information>
						<organization-id>
							<xsl:value-of select="$organization_id" />
						</organization-id>
					</organization-information>
					<link-list>
						<link xsi:type="tmdd:LinkInventoryListCharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
							<network-id>
								unknown
							</network-id>
							<link-id>
								unknown
							</link-id>
							<link-type>unknown</link-type>
							<link-begin-node-id>0</link-begin-node-id>
                  			<link-begin-node-location>
                  				<latitude>0</latitude>
                  				<longitude>0</longitude>
                  			</link-begin-node-location>
                   			<link-end-node-id>0</link-end-node-id>
                   			<link-end-node-location>
                  				<latitude>0</latitude>
                  				<longitude>0</longitude>
                  			</link-end-node-location>
						</link>
					</link-list>
			</link-inventory-item>
		  </xsl:if>
		</tmdd:linkInventoryMsg>
	</xsl:template>
</xsl:stylesheet>
      