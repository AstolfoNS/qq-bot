package com.astolfo.robotservice.infrastructure.common.utils;

import com.astolfo.robotservice.infrastructure.common.details.LoginUser;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@Component
public class JwtUtil {

    @Resource
    private JwtEncoder jwtEncoder;

    @Resource
    private JwtDecoder jwtDecoder;

    @Value("#{jwtProperties.expire}")
    private Long expire;

    @Value("#{jwtProperties.issuer}")
    private String issuer;

    @Value("#{jwtProperties.jwtAlgorithm}")
    private String jwtAlgorithm;


    private JwtClaimsSet buildChaim(
            LoginUser loginUser,
            Instant issuedAt,
            Long expiresInMillis
    ) {
        return JwtClaimsSet
                .builder()
                .subject(loginUser.getIdToString())
                .issuedAt(issuedAt)
                .issuer(issuer)
                .expiresAt(issuedAt.plusMillis(expiresInMillis))
                .build();
    }

     public String generateToken(
            LoginUser loginUser,
            Instant issuedAt,
            Long expiresInMillis
    ) {
        return generateToken(buildChaim(loginUser, issuedAt, expiresInMillis));
    }

    public String generateToken(@NotNull JwtClaimsSet claim) {
        return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.valueOf(jwtAlgorithm)).build(), claim)).getTokenValue();
    }


    public String generateToken(LoginUser loginUserDetails, Long expireInMillis) {
        return generateToken(loginUserDetails, Instant.now(), expireInMillis);
    }

    public String generateToken(LoginUser loginUserDetails) {
        return generateToken(loginUserDetails, expire);
    }

    public ParseToken parseToken(String token) throws JwtException {
        try {
            return new ParseToken(jwtDecoder.decode(token));
        } catch (JwtException exception) {
            log.error("无效的JWT token");

            throw new JwtException("无效的JWT token", exception);
        }
    }

    @AllArgsConstructor
    public static class ParseToken {

        private final Jwt jwt;


        public String getStringId() {
            return jwt.getSubject();
        }

        public Boolean isValid() {
            return !(Objects.nonNull(jwt.getExpiresAt()) && jwt.getExpiresAt().isBefore(Instant.now()));
        }
    }

}
