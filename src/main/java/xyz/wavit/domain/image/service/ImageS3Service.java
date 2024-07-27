package xyz.wavit.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.wavit.domain.image.dto.PresignedUrlResponse;
import xyz.wavit.global.property.S3Property;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {

    private final S3Property s3Property;
    private final AmazonS3 amazonS3;

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    public String generatePresignedUrl(String imageName, com.amazonaws.HttpMethod method) {
        // PresignedUrl 만료시간 설정 (10분)
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);

        // presigned url 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3Property.getBucket(), imageName)
                        .withMethod(method)
                        .withExpiration(expiration);

        String presignedUrl =
                amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();

        return presignedUrl;
    }

    public PresignedUrlResponse getPresignedUrl(MultipartFile image) {
        String originName = image.getOriginalFilename();
        String changedName = changedImageName(originName);
        String presignedUrl = generatePresignedUrl(changedName, com.amazonaws.HttpMethod.PUT);
        String storedImagePath = amazonS3.getUrl(s3Property.getBucket(), changedName).toString();

        // presignedUrl : 이미지 업로드 하기 위한 url
        // storedImagePath : 이미지가 업로드 될 위치
        return new PresignedUrlResponse(presignedUrl, storedImagePath);
    }


    // 이미지 조회에 필요한 presigned url
    public String getPresignedUrlForView(String storedImagePath) {
        String imageName = storedImagePath.substring(storedImagePath.lastIndexOf("/") + 1);
        return generatePresignedUrl(imageName, com.amazonaws.HttpMethod.GET);
    }

}
