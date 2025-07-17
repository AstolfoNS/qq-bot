package com.astolfo.robotservice.lolicon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoliconResponseV1<T> {

    private int code;

    private int count;

    private String msg;

    private List<T> data;


    public static <T> LoliconResponseV1<T> emptyResponse() {
        LoliconResponseV1<T> emptyResponse = new LoliconResponseV1<>();

        emptyResponse.setCode(0);
        emptyResponse.setMsg("OK");
        emptyResponse.setData(new ArrayList<>());

        return emptyResponse;

    }

    public static <T> LoliconResponseV1<T> errorResponse() {
        return errorResponse("null");
    }

    public static <T> LoliconResponseV1<T> errorResponse(String msg) {
        LoliconResponseV1<T> response = new LoliconResponseV1<>();

        response.setCode(400);
        response.setMsg(msg);
        response.setData(new ArrayList<>());

        return response;
    }
}
