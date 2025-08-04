package com.astolfo.robotservice.infrastructure.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapConverterUtil {

    public static <K, V> Map<K, V> convertMap(
            Map<Object, Object> rawMap,
            Class<K> keyClass,
            Class<V> valueClass
    ) {
        if (Objects.isNull(rawMap)) {
            return new HashMap<>();
        }

        return rawMap
                .entrySet()
                .stream()
                .filter(e -> keyClass.isInstance(e.getKey()) && valueClass.isInstance(e.getValue()))
                .collect(Collectors.toMap(e -> keyClass.cast(e.getKey()), e -> valueClass.cast(e.getValue())));
    }

}
