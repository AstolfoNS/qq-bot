package com.astolfo.robotservice.infrastructure.configs.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JacksonProperties {

    @Value("${spring.jackson.date-time-format}")
    private String dateTimeFormat;

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Value("${spring.jackson.time-format}")
    private String timeFormat;

    @Value("${spring.jackson.time-zone}")
    private String timeZone;

}
