package xyz.wavit.global.config;//package xyz.wavit.global.config.s3;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class S3Config
//{
//    @Bean
//    public S3Key s3Key() {
//        Dotenv dotenv = Dotenv.configure().load();
//
//        S3Key s3Key = new S3Key();
//        s3Key.setAccessKey(dotenv.get("AWS_ACCESS_KEY_ID"));
//        s3Key.setSecretKey(dotenv.get("AWS_SECRET_ACCESS_KEY"));
//        s3Key.setBucketName(dotenv.get("AWS_S3_BUCKET_NAME"));
//        s3Key.setRegion(dotenv.get("AWS_REGION"));
//        return s3Key;
//    }
//
//    @Bean
//    public AmazonS3 s3Client(S3Key s3Key) {
//        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Key.getAccessKey(), s3Key.getSecretKey());
//
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
//                .withRegion(s3Key.getRegion())
//                .build();
//    }
//}

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wavit.global.property.S3Property;

import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(S3Property.class)
@RequiredArgsConstructor
public class S3Config {

    private final S3Property s3Property;

    @Bean
    public AmazonS3 s3Client() {

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Property.getAccessKey(), s3Property.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3Property.getRegion())
                .build();
    }
}
