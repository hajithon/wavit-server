package xyz.wavit.domain.notification.dto;

import lombok.Data;

@Data
public class MessageRequest {

    private String targetToken;
    private String title;
    private String body;
}
