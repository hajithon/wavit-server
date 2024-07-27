package xyz.wavit.domain.auth.dto;

import xyz.wavit.domain.user.domain.UserRole;

public record AccessTokenDto(String tokenValue, Long userId, UserRole userRole) {

    public static AccessTokenDto of(String tokenValue, Long userId, UserRole userRole) {
        return new AccessTokenDto(tokenValue, userId, userRole);
    }
}
