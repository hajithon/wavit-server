package xyz.wavit.domain.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageUploadStatus {
    PENDING("업로드 없음"),
    COMPLETE("업로드 완료");

    private final String value;
}
