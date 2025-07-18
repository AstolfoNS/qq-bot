package com.astolfo.robotservice.infrastructure.configs;

import com.astolfo.robotservice.server.common.handlers.AccessDeniedHandlerImpl;
import com.astolfo.robotservice.server.common.handlers.AuthenticationEntryPointImpl;
import com.astolfo.robotservice.server.common.handlers.JwtAuthenticationFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/register")
                                .permitAll()
                                .requestMatchers("/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
//                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
