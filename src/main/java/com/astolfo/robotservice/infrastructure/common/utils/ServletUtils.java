package com.astolfo.robotservice.infrastructure.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ServletUtils {

    @Resource
    private ObjectMapper objectMapper;


    public void renderString(HttpServletResponse response, String string) throws IOException {
        this.renderString(response, HttpServletResponse.SC_OK, string);
    }

    public void renderString(
            HttpServletResponse response,
            int status,
            String string
    ) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print(string);
    }

    public void renderJson(HttpServletResponse response, Object data) throws IOException {
        this.renderString(response, objectMapper.writeValueAsString(data));
    }

}
