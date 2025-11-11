package com.betservice.provider.ergast;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SessionResponse(
        @JsonProperty("circuit_key") Integer circuitKey,
        @JsonProperty("circuit_short_name") String circuitShortName,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("country_key") Integer countryKey,
        @JsonProperty("country_name") String countryName,
        @JsonProperty("date_end") String dateEnd,
        @JsonProperty("date_start") String dateStart,
        @JsonProperty("gmt_offset") String gmtOffset,
        @JsonProperty("location") String location,
        @JsonProperty("meeting_key") Integer meetingKey,
        @JsonProperty("session_key") Integer sessionKey,
        @JsonProperty("session_name") String sessionName,
        @JsonProperty("session_type") String sessionType,
        @JsonProperty("year") Integer year
) {}

