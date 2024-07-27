package xyz.wavit.s3config;

import lombok.Data;

@Data
public class S3Key {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
}