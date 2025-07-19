package com.astolfo.robotservice.robot.lolicon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoliconResponse<T> {

    private String error;

    private List<T> data;


    public static <T> LoliconResponse<T> emptyResponse() {
        LoliconResponse<T> emptyResponse = new LoliconResponse<>();

        emptyResponse.setData(new ArrayList<>());

        return emptyResponse;
    }

    public static <T> LoliconResponse<T> errorResponse() {
        return errorResponse("null");
    }

    public static <T> LoliconResponse<T> errorResponse(String error) {
        LoliconResponse<T> response = new LoliconResponse<>();

        response.setError(error);
        response.setData(new ArrayList<>());

        return response;
    }
}
