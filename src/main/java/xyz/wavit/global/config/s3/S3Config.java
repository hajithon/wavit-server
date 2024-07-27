package xyz.wavit.global.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config
{
    @Bean
    public S3Key s3Key() {
        Dotenv dotenv = Dotenv.configure().load();

        S3Key s3Key = new S3Key();
        s3Key.setAccessKey(dotenv.get("AWS_ACCESS_KEY_ID"));
        s3Key.setSecretKey(dotenv.get("AWS_SECRET_ACCESS_KEY"));
        s3Key.setBucketName(dotenv.get("AWS_S3_BUCKET_NAME"));
        s3Key.setRegion(dotenv.get("AWS_REGION"));
        return s3Key;
    }

    @Bean
    public AmazonS3 s3Client(S3Key s3Key) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Key.getAccessKey(), s3Key.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3Key.getRegion())
                .build();
    }
}
