package com.astolfo.robotservice.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class CommonUtil {

    public static long random(long begin, long end) throws NumberFormatException {

        log.debug("random begin: {}, end: {}", begin, end);

        return ThreadLocalRandom.current().nextLong(begin, end + 1);
    }
}
