package xyz.wavit.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresignedUrlResponse {

    private String presignedUrl;

    private String imageName;
}
