package com.astolfo.robotservice.robot.lolicon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoliconResponseV2<T> {

    private String error;

    private List<T> data;


    public static <T> LoliconResponseV2<T> emptyResponse() {
        LoliconResponseV2<T> emptyResponse = new LoliconResponseV2<>();

        emptyResponse.setData(new ArrayList<>());

        return emptyResponse;

    }

    public static <T> LoliconResponseV2<T> errorResponse() {
        return errorResponse("null");
    }

    public static <T> LoliconResponseV2<T> errorResponse(String error) {
        LoliconResponseV2<T> response = new LoliconResponseV2<>();

        response.setError(error);
        response.setData(new ArrayList<>());

        return response;
    }
}
