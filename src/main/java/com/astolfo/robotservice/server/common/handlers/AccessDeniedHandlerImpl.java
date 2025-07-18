package com.astolfo.robotservice.server.common.handlers;

import com.astolfo.robotservice.server.common.enums.HttpCode;
import com.astolfo.robotservice.server.common.responses.R;
import com.astolfo.robotservice.infrastructure.utils.ServletUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Resource
    private ServletUtil servletUtil;


    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        servletUtil.renderJson(response, R.failure(HttpCode.FORBIDDEN));
    }

}
