package xyz.wavit.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    // DB에 이미지 정보 저장
//    @PostMapping("/image/info")
//    public ResponseEntity<Image> saveImageInfo(@RequestParam("originName") String originName,
//                                               @RequestParam("storedImagePath") String storedImagePath) {
//        Image image = imageS3Service.saveImageInfo(originName, storedImagePath);
//        return ResponseEntity.ok(image);
//    }

    // 이미지 조회를 위한 presigned-url 반환
    @GetMapping("/image")
    public ResponseEntity<String> viewImage(@RequestParam("storedImagePath") String storedImagePath) {
        String presignedUrl = imageS3Service.getPresignedUrlForView(storedImagePath);
        return ResponseEntity.ok(presignedUrl);
    }
}
