package xyz.wavit.global.config.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import xyz.wavit.global.property.JwtProperty;

@Getter
@RequiredArgsConstructor
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Property {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
}
