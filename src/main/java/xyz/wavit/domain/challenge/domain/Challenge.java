package xyz.wavit.domain.challenge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import xyz.wavit.domain.common.model.BaseEntity;
import xyz.wavit.domain.common.model.ImageUploadStatus;
import xyz.wavit.domain.user.domain.User;
import xyz.wavit.global.exception.CustomException;
import xyz.wavit.global.exception.ErrorCode;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseEntity {

    public static final Duration CHALLENGE_DURATION = Duration.ofHours(6);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @Comment("챌린지 시작 시간 (지목받은 시간)")
    private LocalDateTime startAt;

    @Comment("챌린지 종료 시간")
    private LocalDateTime finishAt;

    @Enumerated(EnumType.STRING)
    private ImageUploadStatus uploadStatus;

    private String imageUrl;

    private String comment;

    @Comment("챌린지를 수행한 유저")
    @ManyToOne(fetch = FetchType.LAZY)
    private User challengedUser;

    @Comment("나를 지목한 유저")
    @ManyToOne(fetch = FetchType.LAZY)
    private User challengedBy;

    @Builder(access = AccessLevel.PRIVATE)
    private Challenge(
            LocalDateTime startAt,
            LocalDateTime finishAt,
            ImageUploadStatus uploadStatus,
            String imageUrl,
            String comment,
            User challengedUser,
            User challengedBy) {
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.uploadStatus = uploadStatus;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.challengedUser = challengedUser;
        this.challengedBy = challengedBy;
    }

    public static Challenge createBySystem(User challengedUser) {
        LocalDateTime now = LocalDateTime.now();
        return Challenge.builder()
                .startAt(now)
                .finishAt(calculateFinishAt(now))
                .uploadStatus(ImageUploadStatus.PENDING)
                .challengedUser(challengedUser)
                .build();
    }

    public static Challenge createByUser(User challengedUser, User challengedBy) {
        validateCreate(challengedUser, challengedBy);
        LocalDateTime now = LocalDateTime.now();
        return Challenge.builder()
                .startAt(now)
                .finishAt(calculateFinishAt(now))
                .uploadStatus(ImageUploadStatus.PENDING)
                .challengedUser(challengedUser)
                .challengedBy(challengedBy)
                .build();
    }

    private static void validateCreate(User challengedUser, User challengedBy) {
        if (challengedUser.getId().equals(challengedBy.getId())) {
            throw CustomException.from(ErrorCode.CHALLENGE_NOMINATED_BY_ME);
        }
    }

    /**
     * 챌린지 종료 시간을 계산합니다.
     * 시작 시간으로부터 6시간 후로 설정하며, 만약 6시간 후가 다음 날로 넘어간다면 현재 날짜의 23:59:59.999999999로 설정합니다.
     */
    private static LocalDateTime calculateFinishAt(LocalDateTime startAt) {
        LocalDateTime finishAt = startAt.plus(CHALLENGE_DURATION);
        // 종료 시간이 다음 날로 넘어가는지 확인
        if (!startAt.toLocalDate().equals(finishAt.toLocalDate())) {
            // 종료 시간을 현재 날짜의 23:59:59.999999999로 설정
            finishAt = startAt.toLocalDate().atTime(LocalTime.MAX);
        }
        return finishAt;
    }

    // 데이터 조회 로직

    /**
     * 챌린지 종료까지 남은 시간을 반환합니다.
     */
    public Duration getRemainingTime() {
        return Duration.between(LocalDateTime.now(), finishAt);
    }

    public boolean isCompleted() {
        return uploadStatus == ImageUploadStatus.COMPLETE;
    }

    // 데이터 변경 로직

    /**
     * 챌린지를 완료 상태로 변경합니다.
     */
    public void complete(String imageUrl, String comment) {
        this.uploadStatus = ImageUploadStatus.COMPLETE;
        this.imageUrl = imageUrl;
        this.comment = comment;
    }
}
