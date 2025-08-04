package com.astolfo.robotservice.infrastructure.common.handlers;

import com.astolfo.robotservice.infrastructure.common.enums.HttpCode;
import com.astolfo.robotservice.infrastructure.common.responses.R;
import com.astolfo.robotservice.infrastructure.common.utils.ServletUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Resource
    private ServletUtil servletUtil;


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        servletUtil.renderJson(response, R.failure(HttpCode.UNAUTHORIZED));
    }

}
