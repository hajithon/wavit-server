package xyz.wavit.domain.challenge.dto;

public record ChallengeMyReportDto(Long completedChallengeCount, Long totalNominatedUserCount) {
    public static ChallengeMyReportDto of(Long completedChallengeCount, Long totalNominatedUserCount) {
        return new ChallengeMyReportDto(completedChallengeCount, totalNominatedUserCount);
    }
}
