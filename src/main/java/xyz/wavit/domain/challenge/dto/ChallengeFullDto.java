package xyz.wavit.domain.challenge.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.user.dto.UserFullDto;

public record ChallengeFullDto(
        Long challengeId,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        Duration remainingTime,
        UserFullDto challengedBy,
        String imageUrl,
        String comment) {
    public static ChallengeFullDto from(Challenge challenge) {
        // 시스템에 의해 생성된 챌린지의 경우 challengedBy가 null이므로 Optional로 감싸서 처리
        return new ChallengeFullDto(
                challenge.getId(),
                challenge.getStartAt(),
                challenge.getFinishAt(),
                challenge.getRemainingTime(),
                Optional.ofNullable(challenge.getChallengedBy())
                        .map(UserFullDto::from)
                        .orElse(UserFullDto.createEmpty()),
                challenge.getImageUrl(),
                challenge.getComment());
    }

    public static ChallengeFullDto createEmpty() {
        return new ChallengeFullDto(null, null, null, null, UserFullDto.createEmpty(), null, null);
    }
}
