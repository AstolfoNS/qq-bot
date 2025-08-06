package com.astolfo.robotservice.infrastructure.common.handlers;

import com.astolfo.robotservice.infrastructure.common.enums.HttpCode;
import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import com.astolfo.robotservice.infrastructure.common.utils.JwtUtils;
import com.astolfo.robotservice.infrastructure.common.utils.RedisCacheUtils;
import com.astolfo.robotservice.infrastructure.common.constants.RedisCacheConstant;
import com.astolfo.robotservice.domain.service.AuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Lazy
    @Resource
    private AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");

        if (!this.isValidAuthHeader(authHeader)) {
            filterChain.doFilter(request, response);

            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            Optional<LoginUser> optionalLoginUser = Optional.of(redisCacheUtils.get(RedisCacheConstant.Login_USER_PERFIX.concat(jwtUtils.parseToken(token).getStringId())));

            optionalLoginUser.ifPresent(loginUser -> authenticationService.setAuthentication(loginUser));
        } catch (Exception exception) {
            log.error("JWT认证失败，错误信息: {}", exception.getMessage());

            response.sendError(HttpCode.UNAUTHORIZED.getCode());
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidAuthHeader(String authHeader) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ");
    }
}
