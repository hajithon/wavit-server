package xyz.wavit.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.wavit.domain.notification.dto.MessageRequest;
import xyz.wavit.domain.notification.service.NotificationService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity pushMessage(@RequestBody MessageRequest messageRequest) throws IOException {

        notificationService.sendMessageTo(
                messageRequest.getTargetToken(),
                messageRequest.getTitle(),
                messageRequest.getBody());
        return ResponseEntity.ok().build();
    }
}
