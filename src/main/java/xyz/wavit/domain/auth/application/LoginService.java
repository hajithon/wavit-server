package xyz.wavit.domain.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wavit.domain.auth.dto.AccessTokenDto;
import xyz.wavit.domain.auth.dto.LoginRequest;
import xyz.wavit.domain.auth.dto.SignupRequest;
import xyz.wavit.domain.user.dao.UserRepository;
import xyz.wavit.domain.user.domain.User;
import xyz.wavit.domain.user.dto.UserFullDto;
import xyz.wavit.global.exception.CustomException;
import xyz.wavit.global.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Pair<UserFullDto, AccessTokenDto> login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> CustomException.from(ErrorCode.USER_NOT_FOUND));

        validatePasswordMatches(user, request);

        log.info("[LoginService] 로그인 성공: username={}", request.username());

        var accessTokenDto = jwtService.createAccessToken(user.getId(), user.getRole());
        var userFullDto = UserFullDto.from(user);

        return Pair.of(userFullDto, accessTokenDto);
    }

    private void validatePasswordMatches(User user, LoginRequest request) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("[LoginService] 비밀번호 불일치: username={}, password={}", request.username(), request.password());
            throw CustomException.from(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw CustomException.from(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.create(
                request.name(), request.nickname(), request.username(), passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }
}
