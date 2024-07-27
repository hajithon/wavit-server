package xyz.wavit.domain.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.wavit.domain.auth.dto.AccessTokenDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    public AccessTokenDto parseAccessToken(String accessToken) {
        return null;
    }
}
