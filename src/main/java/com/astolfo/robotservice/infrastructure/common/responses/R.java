package com.astolfo.robotservice.infrastructure.common.responses;

import com.astolfo.robotservice.infrastructure.common.enums.HttpCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class R<T> {

    private Integer code;

    private String message;

    private T data;


    public static <T> R<T> success() {
        return new R<>(HttpCode.SUCCESS.getCode(), HttpCode.SUCCESS.getMessage(), null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(HttpCode.SUCCESS.getCode(), HttpCode.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> success(String message) {
        return new R<>(HttpCode.SUCCESS.getCode(), message, null);
    }

    public static <T> R<T> success(HttpCode httpCode) {
        return new R<>(httpCode.getCode(), httpCode.getMessage(), null);
    }

    public static <T> R<T> success(HttpCode httpCode, T data) {
        return new R<>(httpCode.getCode(), httpCode.getMessage(), data);
    }

    public static <T> R<T> success(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R<T> success(
            Integer code,
            String message,
            T data
    ) {
        return new R<>(code, message, data);
    }

    public static <T> R<T> failure() {
        return new R<>(HttpCode.FAILURE.getCode(), HttpCode.FAILURE.getMessage(), null);
    }

    public static <T> R<T> failure(T data) {
        return new R<>(HttpCode.FAILURE.getCode(), HttpCode.FAILURE.getMessage(), data);
    }

    public static <T> R<T> failure(String message) {
        return new R<>(HttpCode.FAILURE.getCode(), message, null);
    }

    public static <T> R<T> failure(HttpCode httpCode) {
        return new R<>(httpCode.getCode(), httpCode.getMessage(), null);
    }

    public static <T> R<T> failure(HttpCode httpCode, T data) {
        return new R<>(httpCode.getCode(), httpCode.getMessage(), data);
    }

    public static <T> R<T> failure(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R<T> failure(
            Integer code,
            String message,
            T data
    ) {
        return new R<>(code, message, data);
    }
}
