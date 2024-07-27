package xyz.wavit.domain.user.dto;

import xyz.wavit.domain.user.domain.User;
import xyz.wavit.domain.user.domain.UserRole;

public record UserFullDto(Long userId, UserRole role, String name, String nickname, String username) {
    public static UserFullDto from(User user) {
        return new UserFullDto(user.getId(), user.getRole(), user.getName(), user.getNickname(), user.getUsername());
    }
}
