package xyz.wavit.domain.challenge.dto;

public record ChallengeReportDto(Integer myChallengeCount, int totalChallengeCount, int pendingChallengeCount) {
    public static ChallengeReportDto of(Integer myChallengeCount, int totalChallengeCount, int pendingChallengeCount) {
        return new ChallengeReportDto(myChallengeCount, totalChallengeCount, pendingChallengeCount);
    }
}
