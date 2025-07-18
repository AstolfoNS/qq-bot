package com.astolfo.robotservice.server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpCode {

    SUCCESS(200, "操作成功"),
    FAILURE(400, "操作失败"),
    LOGIN_SUCCESS(200, "登录成功"),
    LOGOUT_SUCCESS(200, "退出成功"),

    NO_OPERATOR_AUTH(301, "当前没有权限操作"),

    UNAUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "用户权限不足"),

    NEED_LOGIN(501, "需要登录后操作"),
    USERNAME_EXIST(504, "该用户名已被使用"),
    EMAIL_EXIST(505, "该邮箱已被使用"),
    REQUIRE_USERNAME(506, "必须填写用户名"),
    LOGIN_FAILED(507, "用户名或密码错误"),
    USER_NOT_EXIST(508, "该用户不存在"),
    LOGOUT_FAILED(509, "退出失败"),
    USER_UPDATE_FAILED(510, "用户信息更新失败"),
    USER_REGISTER_FAILED(511, "用户注册失败");

    private final Integer code;

    private final String message;

}
