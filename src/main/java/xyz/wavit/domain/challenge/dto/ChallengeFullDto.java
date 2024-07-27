package xyz.wavit.domain.challenge.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.user.dto.UserFullDto;

public record ChallengeFullDto(
        Long challengeId,
        LocalDateTime startAt,
        LocalDateTime finishAt,
        Duration remainingTime,
        UserFullDto challengedBy) {
    public static ChallengeFullDto from(Challenge challenge) {
        return new ChallengeFullDto(
                challenge.getId(),
                challenge.getStartAt(),
                challenge.getFinishAt(),
                challenge.getRemainingTime(),
                UserFullDto.from(challenge.getChallengedBy()));
    }

    public static ChallengeFullDto createEmpty() {
        return new ChallengeFullDto(null, null, null, null, null);
    }
}
