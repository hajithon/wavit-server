package xyz.wavit.domain.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.wavit.domain.auth.dto.AccessTokenDto;
import xyz.wavit.domain.user.domain.UserRole;
import xyz.wavit.global.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public AccessTokenDto createAccessToken(Long userId, UserRole userRole) {
        return jwtUtil.generateAccessToken(userId, userRole);
    }

    public AccessTokenDto parseAccessToken(String accessToken) {
        return jwtUtil.parseAccessToken(accessToken);
    }
}
