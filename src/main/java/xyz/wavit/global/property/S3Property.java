package xyz.wavit.global.property;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "storage")
public class S3Property {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
}
