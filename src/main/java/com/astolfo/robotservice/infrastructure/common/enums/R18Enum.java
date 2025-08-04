package com.astolfo.robotservice.infrastructure.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum R18Enum {

    RAND("2", "--rand"),
    TRUE("1", "--true"),
    FALSE("0", "--false");

    private final String value;

    private final String param;

     private static final Map<String, R18Enum> PARAM_TO_ENUM_MAP;

     static {
         PARAM_TO_ENUM_MAP = Arrays.stream(values()).collect(Collectors.toMap(R18Enum::getParam, Function.identity()));
     }

    public static R18Enum typeOf(String param) {
        return PARAM_TO_ENUM_MAP.getOrDefault(param, RAND);
    }
}
