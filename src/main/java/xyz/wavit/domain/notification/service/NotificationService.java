package xyz.wavit.domain.notification.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;
import xyz.wavit.domain.notification.dto.FcmMessage;
import xyz.wavit.global.property.FcmProperty;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/" + "wavit-92b96/messages:send";
    private final Gson gson = new Gson();

    private final FcmProperty fcmProperty;

    // FcmMessage를 json 형태로 변환
    private String makeMessage(String targetToken, String title, String body)
            throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .build())
                .validateOnly(false)
                .build();
        return gson.toJson(fcmMessage);
    }

    public String getAccessToken() throws IOException {
        // JSON 문자열 생성
        String jsonContent = fcmProperty.toString();

        // JSON 문자열을 ByteArrayInputStream으로 변환
        ByteArrayInputStream jsonInputStream = new ByteArrayInputStream(jsonContent.getBytes());

        // GoogleCredentials 객체 생성
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(jsonInputStream)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // 토큰 갱신
        googleCredentials.refreshIfExpired();

        // 액세스 토큰 반환
        return googleCredentials.getAccessToken().getTokenValue();
    }

    // 토큰, title, body -> Firebase에 request
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }
}
