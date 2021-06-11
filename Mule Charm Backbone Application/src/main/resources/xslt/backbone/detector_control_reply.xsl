<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fep="http://fep.cdm.charm.cgi.com">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="/root">
		<detector_control_reply>
			<xsl:apply-templates select="detector_control_reply[1]/meta" />
			<xsl:for-each
				select="detector_control_reply/detector_state_address_attr_reply">
				<detector_state_address_attr_reply>
					<detector_id>
						<line_id>
							<xsl:value-of select="detector_id/line_id" />
						</line_id>
						<position_id>
							<xsl:value-of select="detector_id/position_id" />
						</position_id>
						<xsl:if test="reference_road_code != ''">
							<reference_road_code>
								<xsl:value-of select="detector_id/reference_road_code" />
							</reference_road_code>
						</xsl:if>
						<xsl:if test="reference_km != ''">
							<reference_km>
								<xsl:value-of select="detector_id/reference_km" />
							</reference_km>
						</xsl:if>
					</detector_id>
					<change_state_reply>
						<mus_reply>
							<xsl:call-template name="replyNodeTemplate" />
							<mus_value>
								<xsl:value-of select="change_state_reply/mus_reply/mus_value" />
							</mus_value>
						</mus_reply>
						<light_intensity_reply>
							<xsl:call-template name="replyNodeTemplate" />
							<intensity>
								<xsl:value-of
									select="change_state_reply/light_intensity_reply/intensity" />
							</intensity>
						</light_intensity_reply>
						<dynamic_maximum_speed_reply>
							<xsl:call-template name="replyNodeTemplate" />
							<speed>
								<xsl:value-of
									select="change_state_reply/dynamic_maximum_speed_reply/speed" />
							</speed>
						</dynamic_maximum_speed_reply>
						<line_cycle_shift_reply>
							<cycle_length>
								<xsl:value-of
									select="change_state_reply/line_cycle_shift_reply/cycle_length" />
							</cycle_length>
						</line_cycle_shift_reply>
						<sent_time>
							<send_time_on_line>
								<xsl:value-of select="change_state_reply/sent_time/send_time_on_line" />
							</send_time_on_line>
						</sent_time>
						<type>
							<change_type>
								<xsl:value-of select="change_state_reply/type/change_type" />
							</change_type>
						</type>
					</change_state_reply>
				</detector_state_address_attr_reply>
			</xsl:for-each>
		</detector_control_reply>
	</xsl:template>
	<xsl:template match="detector_control_reply[1]/meta">
		<meta>
			<message_id>
				<uuid>
					<xsl:value-of select="message_id/uuid" />
				</uuid>
			</message_id>
			<message_create_date>
				<xsl:value-of select="message_create_date" />
			</message_create_date>
		</meta>
	</xsl:template>
	<xsl:template name="replyNodeTemplate">
		<reply>
			<status>
				<xsl:value-of select="change_state_reply/mus_reply/reply/status" />
			</status>
			<measure_number>
				<xsl:value-of select="change_state_reply/mus_reply/reply/measure_number" />
			</measure_number>
			<local_identity>
				<xsl:value-of select="change_state_reply/mus_reply/reply/local_identity" />
			</local_identity>
		</reply>
	</xsl:template>
</xsl:stylesheet>