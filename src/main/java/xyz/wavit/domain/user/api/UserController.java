package xyz.wavit.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wavit.domain.user.application.UserService;
import xyz.wavit.domain.user.dto.UserWithTotalChallengeDto;

@Tag(name = "[User]", description = "사용자 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "지목 가능한 사용자 목록 조회", description = "현재 지목 가능한 사용자 목록을 최신 가입일자 순서로 조회합니다. 자기 자신은 제외됩니다.")
    @PostMapping("/candidates")
    public ResponseEntity<List<UserWithTotalChallengeDto>> getCandidateUsers() {
        var response = userService.getCandidateUsers();
        return ResponseEntity.ok(response);
    }
}
