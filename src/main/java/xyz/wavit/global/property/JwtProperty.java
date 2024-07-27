package xyz.wavit.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

    private final String issuer;
    private final String secret;
    private final Integer expiration; // seconds
}
