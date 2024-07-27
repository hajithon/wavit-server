package xyz.wavit.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.wavit.domain.image.dto.PresignedUrlResponse;
import xyz.wavit.global.property.S3Property;

@Service
@RequiredArgsConstructor
public class ImageS3Service {

    private final S3Property s3Property;
    private final AmazonS3 amazonS3;

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    public String generatePresignedUrl(String imageName) {
        // PresignedUrl 만료시간 설정 (10분)
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);

        // presigned url 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                        s3Property.getBucket(), imageName)
                .withMethod(com.amazonaws.HttpMethod.PUT)
                .withExpiration(expiration);

        String presignedUrl =
                amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();

        return presignedUrl;
    }

    public PresignedUrlResponse getPresignedUrl(MultipartFile image) {
        String originName = image.getOriginalFilename();
        String changedName = changedImageName(originName);
        String presignedUrl = generatePresignedUrl(changedName);

        // presignedUrl : 이미지 업로드 하기 위한 url
        return new PresignedUrlResponse(presignedUrl, changedName);
    }

    public void uploadComplete(String imageName) {
        String storedImagePath =
                amazonS3.getUrl(s3Property.getBucket(), imageName).toString();

        // challengeRecord에 storedImagePath 추가

    }
}
