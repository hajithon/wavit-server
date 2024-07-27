package xyz.wavit.domain.user.dto;

import xyz.wavit.domain.user.domain.User;

public record UserWithTotalChallengeDto(UserFullDto user, int totalChallengeCount) {

    public static UserWithTotalChallengeDto from(User user, int totalChallengeCount) {
        return new UserWithTotalChallengeDto(UserFullDto.from(user), totalChallengeCount);
    }
}
