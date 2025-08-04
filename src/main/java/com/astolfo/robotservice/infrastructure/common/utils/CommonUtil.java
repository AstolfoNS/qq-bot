package com.astolfo.robotservice.infrastructure.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class CommonUtil {

    public static long random(long begin, long end) throws NumberFormatException {
        return ThreadLocalRandom.current().nextLong(begin, end + 1);
    }

    public static long randomFromZero(long end) throws NumberFormatException {
        return ThreadLocalRandom.current().nextLong(end + 1);
    }

    public static int praseIntElseMax(String numberString) {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException exception) {
            return Integer.MAX_VALUE;
        }
    }

    public static long praseLongElseMax(String numberString) {
        try {
            return Long.parseLong(numberString);
        } catch (NumberFormatException exception) {
            return Long.MAX_VALUE;
        }
    }
}
