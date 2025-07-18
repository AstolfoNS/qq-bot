package com.astolfo.robotservice.server.common.handlers;

import com.astolfo.robotservice.server.common.userdetails.LoginUser;
import com.astolfo.robotservice.infrastructure.utils.JwtUtil;
import com.astolfo.robotservice.infrastructure.utils.RedisCacheUtil;
import com.astolfo.robotservice.server.common.constant.RedisCacheConstant;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisCacheUtil redisCacheUtil;


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");

        if (!(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);

            return;
        }

        String token = authHeader.substring("Bearer ".length());

        String userId = jwtUtil.parseToken(token).getStringId();

        LoginUser loginUser = redisCacheUtil.get(RedisCacheConstant.Login_USER_PERFIX.concat(userId));

        if (Objects.isNull(loginUser)) {
            log.error("JWT认证过程中未从redis中找到用户，用户未登录，UserId: {}", userId);

            throw new RuntimeException("用户未登录");
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}
