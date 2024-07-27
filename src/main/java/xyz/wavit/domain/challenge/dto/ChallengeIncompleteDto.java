package xyz.wavit.domain.challenge.dto;

import xyz.wavit.domain.challenge.domain.Challenge;

public record ChallengeIncompleteDto(ChallengeFullDto challenge, long challengeOrder) {
    public static ChallengeIncompleteDto of(Challenge challenge, Long challengeOrder) {
        return new ChallengeIncompleteDto(ChallengeFullDto.from(challenge), challengeOrder);
    }

    public static ChallengeIncompleteDto createEmpty() {
        return new ChallengeIncompleteDto(ChallengeFullDto.createEmpty(), 0);
    }
}
