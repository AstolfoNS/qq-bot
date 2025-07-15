package com.astolfo.robotservice.common.infrastructure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpCode {

    SUCCESS(200, "操作成功"),
    FAILURE(400, "操作失败");


    private final Integer code;

    private final String message;

}
