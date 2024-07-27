package xyz.wavit.domain.challenge.dto;

import java.time.LocalDateTime;
import xyz.wavit.domain.challenge.domain.Challenge;

public record ChallengeFeedDto(
        Long challengeId,
        String challengeImageUrl,
        String comment,
        LocalDateTime completedAt,
        Long userId,
        String userProfileImageUrl,
        String nickname) {
    public static ChallengeFeedDto from(Challenge challenge) {
        return new ChallengeFeedDto(
                challenge.getId(),
                challenge.getImageUrl(),
                challenge.getComment(),
                challenge.getCompletedAt(),
                challenge.getChallengedUser().getId(),
                challenge.getChallengedUser().getProfileImageUrl(),
                challenge.getChallengedBy().getNickname());
    }
}
