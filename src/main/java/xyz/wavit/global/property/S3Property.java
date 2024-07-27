package xyz.wavit.global.property;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "storage")
public class S3Property {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
}