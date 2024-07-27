package xyz.wavit.global.util;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.wavit.domain.user.dao.UserRepository;
import xyz.wavit.domain.user.domain.User;
import xyz.wavit.domain.user.domain.UserRole;
import xyz.wavit.global.exception.CustomException;
import xyz.wavit.global.exception.ErrorCode;
import xyz.wavit.global.security.CustomUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository
                .findById(getCurrentUserId())
                .orElseThrow(() -> CustomException.from(ErrorCode.USER_NOT_FOUND));
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        validateAuthenticationNotNull(authentication);

        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            log.error("[UserUtil] 현재 사용자 ID 파싱 실패: name={}", authentication.getName());
            throw CustomException.from(ErrorCode.AUTH_NOT_PARSABLE);
        }
    }

    public UserRole getCurrentUserRole() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(CustomUserDetails.class::cast)
                .map(CustomUserDetails::getUserRole)
                .orElseThrow(() -> CustomException.from(ErrorCode.AUTH_NOT_PARSABLE));
    }

    private void validateAuthenticationNotNull(Authentication authentication) {
        if (authentication == null) {
            log.error("[UserUtil] 시큐리티 인증 정보 비어있음");
            throw CustomException.from(ErrorCode.AUTH_NOT_EXIST);
        }
    }
}
