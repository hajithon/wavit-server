package xyz.wavit.global.util;

import static xyz.wavit.global.constant.SecurityConstant.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wavit.domain.auth.dto.AccessTokenDto;
import xyz.wavit.domain.user.domain.UserRole;
import xyz.wavit.global.property.JwtProperty;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperty jwtProperty;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public JwtUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
        this.algorithm = Algorithm.HMAC512(jwtProperty.getSecret());
        this.jwtVerifier = buildJwtVerifier(jwtProperty, algorithm);
    }

    private static JWTVerifier buildJwtVerifier(JwtProperty jwtProperty, Algorithm algorithm) {
        return JWT.require(algorithm)
                .withIssuer(jwtProperty.getIssuer())
                .withClaim(RegisteredClaims.SUBJECT, (userId, ignored) -> Long.parseLong(userId.asString()) > 0)
                .withClaim(JWT_ROLE_CLAIM_NAME, (role, ignored) -> role.as(UserRole.class) != null)
                .build();
    }

    public AccessTokenDto generateAccessToken(Long userId, UserRole userRole) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(jwtProperty.getExpiration());

        String accessToken = buildJwt(userId, userRole, issuedAt, expiresAt);

        return AccessTokenDto.of(accessToken, userId, userRole);
    }

    private String buildJwt(Long userId, UserRole userRole, Instant issuedAt, Instant expiresAt) {
        return JWT.create()
                .withIssuer(jwtProperty.getIssuer())
                .withSubject(userId.toString())
                .withClaim(JWT_ROLE_CLAIM_NAME, userRole.toString())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public AccessTokenDto parseAccessToken(String accessToken) throws JWTVerificationException {
        DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);

        return AccessTokenDto.of(
                decodedJWT.getToken(),
                Long.parseLong(decodedJWT.getSubject()),
                decodedJWT.getClaim(JWT_ROLE_CLAIM_NAME).as(UserRole.class));
    }
}
