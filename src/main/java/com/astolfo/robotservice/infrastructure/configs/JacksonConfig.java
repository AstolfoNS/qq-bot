package com.astolfo.robotservice.infrastructure.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Value("#{jacksonProperties.dateTimeFormat}")
    private String dateTimeFormat;

    @Value("#{jacksonProperties.dateFormat}")
    private String dateFormat;

    @Value("#{jacksonProperties.timeFormat}")
    private String timeFormat;

    @Value("#{jacksonProperties.timeZone}")
    private String timeZone;


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            builder.timeZone(TimeZone.getTimeZone(timeZone));

            JavaTimeModule module = new JavaTimeModule();

            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));

            module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)));

            module.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)));
            module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)));

            builder.modules(module);

            builder.featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        };
    }
}

