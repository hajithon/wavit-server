package xyz.wavit.global.config;//package xyz.wavit.global.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import xyz.wavit.global.property.FcmProperty;
//import xyz.wavit.global.property.S3Property;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableConfigurationProperties(FcmProperty.class)
//public class FcmConfig {
//
//    private final FcmProperty fcmProperty;
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//
//        String privateKey = fcmProperty.getPrivate_key().replace("\\n", "\n");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(privateKey.getBytes())))
//                .setProjectId(fcmProperty.getProject_id())
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        } else {
//            FirebaseApp.getInstance();
//        }
//
//        return FirebaseApp.getInstance();
//    }
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wavit.global.property.FcmProperty;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FcmProperty.class)
public class FcmConfig {

    private final FcmProperty fcmProperty;

    @PostConstruct
    public void firebaseApp() throws IOException {

        // ObjectMapper를 사용하여 FcmProperty 객체를 JSON 문자열로 변환합니다
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(fcmProperty);

        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options =
                        new FirebaseOptions.Builder()
                                .setCredentials(
                                        GoogleCredentials.fromStream(
                                                new ByteArrayInputStream(
                                                        jsonContent.getBytes(StandardCharsets.UTF_8))))
                                .build();
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
//            log.error("FCM initializing Exception: {}", e.getStackTrace()[0]);
        }
    }
}
