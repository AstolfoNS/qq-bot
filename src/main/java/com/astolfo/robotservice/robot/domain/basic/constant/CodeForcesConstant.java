package com.astolfo.robotservice.robot.domain.basic.constant;

import java.util.regex.Pattern;

public class CodeForcesConstant {

    public static final int MAX_REQUEST_USER_LIST_SIZE = 10;

    public static final int MAX_RATING_HISTORY_SIZE = 50;

    public static final String OK = "OK";

    public static final String FAILED = "FAILED";

    public static final Pattern INVALID_HANDLE_PATTERN = Pattern.compile("handles:\\sUser\\swith\\shandle\\s(.*?)\\snot\\sfound");
}
