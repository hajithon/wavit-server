package xyz.wavit.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.wavit.domain.image.dto.PresignedUrlResponse;
import xyz.wavit.domain.image.service.ImageS3Service;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageS3Service imageS3Service;

    // 이미지 업로드 시 사용할 presignedUrl
    @PostMapping("/image/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(@RequestParam("image") MultipartFile image) {
        PresignedUrlResponse response = imageS3Service.getPresignedUrl(image);
        return ResponseEntity.ok(response);
    }

    // 이미지 업로드 완료시 호출
    @PostMapping("/image/upload-complete")
    public void imageUploadComplete(@RequestParam("imageName") String imageName) {
        imageS3Service.uploadComplete(imageName);
    }
}
