package xyz.wavit.domain.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wavit.domain.auth.application.LoginService;
import xyz.wavit.domain.auth.dto.AccessTokenDto;
import xyz.wavit.domain.auth.dto.LoginRequest;
import xyz.wavit.domain.user.dto.UserFullDto;

@Tag(name = "[Auth]", description = "로그인 및 회원가입 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<UserFullDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        var pair = loginService.login(loginRequest);
        UserFullDto response = pair.getFirst();
        AccessTokenDto accessTokenDto = pair.getSecond();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessTokenDto.tokenValue());

        return ResponseEntity.ok().headers(headers).body(response);
    }
}
