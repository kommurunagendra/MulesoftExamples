<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fi="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tmdd="http://www.tmdd.org/3/messages"
	exclude-result-prefixes="#all">

	<!-- time: HHMMSSssss -->

	<xsl:template name="isodate_to_tmdd_date">
		<xsl:param name="isoDateTimeInUtc" />

		<date>
			<xsl:value-of select="format-dateTime($isoDateTimeInUtc, '[Y][M01][D01]')" />
		</date>
		<time>
			<xsl:value-of
				select="format-dateTime($isoDateTimeInUtc, '[H01][m01][s01][f001]')" />
		</time>
	</xsl:template>

	<xsl:template name="isodate_to_tmdd_date_no_ms">
		<xsl:param name="isoDateTimeInUtc" />

		<date>
			<xsl:value-of select="format-dateTime($isoDateTimeInUtc, '[Y][M01][D01]')" />
		</date>
		<time>
			<xsl:value-of
					select="format-dateTime($isoDateTimeInUtc, '[H01][m01][s01]')" />
		</time>
	</xsl:template>

	<!-- TODO there should be an easier way to do this -->
	<xsl:template name="tmdd_date_to_isodate">
		<xsl:param name="tmddDate" />

		<xsl:variable name="date"
			select="concat(substring($tmddDate/date, 1, 4), '-', substring($tmddDate/date, 5, 2), '-', substring($tmddDate/date, 7, 2))" />

		<xsl:choose>
			<xsl:when test="string-length($tmddDate/time) > 6">
				<xsl:variable name="time"
					select="concat(substring($tmddDate/time, 1, 2), ':', substring($tmddDate/time, 3, 2), ':', substring($tmddDate/time, 5, 2),'.', substring($tmddDate/time, 7,3))" />

				<xsl:value-of
					select="fi:adjust-dateTime-to-timezone(xs:dateTime(concat($date, 'T', $time)), xs:dayTimeDuration('PT0H'))" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="time"
					select="concat(substring($tmddDate/time, 1, 2), ':', substring($tmddDate/time, 3, 2), ':', substring($tmddDate/time, 5, 2))" />
				<xsl:value-of
					select="fi:adjust-dateTime-to-timezone(xs:dateTime(concat($date, 'T', $time)), xs:dayTimeDuration('PT0H'))" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>