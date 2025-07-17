package com.astolfo.robotservice.common.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; // 引入 @Component 注解

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Component
public class TimeConverter {

    private final ObjectMapper objectMapper;

    private final String timeZone;


    public TimeConverter(ObjectMapper objectMapper, @Value("${spring.jackson.time-zone}") String timeZone) {
        this.objectMapper = objectMapper;
        this.timeZone = timeZone;
    }

    public static LocalDateTime secondsToDate(long totalSeconds, String timeZone) {
        if (Objects.isNull(timeZone) || timeZone.trim().isEmpty()) {
            log.error("Warning: ZoneId string is null or empty, using system default zone.");

            return secondsToDate(totalSeconds, ZoneId.systemDefault());
        }
        try {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(totalSeconds), ZoneId.of(timeZone));
        } catch (java.time.zone.ZoneRulesException e) {
            log.error("Error: Invalid ZoneId string provided: {}. Using system default zone.", timeZone);

            return secondsToDate(totalSeconds, ZoneId.systemDefault());
        }
    }

    public static LocalDateTime secondsToDate(long totalSeconds, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(totalSeconds), zoneId);
    }

    public LocalDateTime secondsToLocalDateTime(long totalSeconds) {
        return secondsToDate(totalSeconds, timeZone);
    }

    public String secondsToDateString(long totalSeconds) throws JsonProcessingException {
        return objectMapper.writeValueAsString(secondsToLocalDateTime(totalSeconds)).replaceAll("^\"|\"$", "");
    }

     public static LocalDateTime millisToDate(long totalMilliseconds, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(totalMilliseconds), zoneId);
    }

    public LocalDateTime millisToLocalDateTime(long totalMilliseconds) {
        return millisToDate(totalMilliseconds, ZoneId.of(timeZone));
    }

    public String millisToDateString(long totalMilliseconds) throws JsonProcessingException {
        return objectMapper.writeValueAsString(millisToLocalDateTime(totalMilliseconds)).replaceAll("^\"|\"$", "");
    }

}
