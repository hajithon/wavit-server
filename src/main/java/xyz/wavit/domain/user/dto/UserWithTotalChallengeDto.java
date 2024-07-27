package xyz.wavit.domain.user.dto;

import xyz.wavit.domain.user.domain.User;

public record UserWithTotalChallengeDto(UserFullDto user, Long totalChallengeCount) {

    public static UserWithTotalChallengeDto from(User user, Long totalChallengeCount) {
        return new UserWithTotalChallengeDto(UserFullDto.from(user), totalChallengeCount);
    }
}
