package com.astolfo.robotservice.infrastructure.common.handlers;

import com.astolfo.robotservice.infrastructure.common.enums.HttpCode;
import com.astolfo.robotservice.infrastructure.common.responses.R;
import com.astolfo.robotservice.infrastructure.common.utils.ServletUtils;
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
    private ServletUtils servletUtils;


    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        servletUtils.renderJson(response, R.failure(HttpCode.FORBIDDEN));
    }

}
