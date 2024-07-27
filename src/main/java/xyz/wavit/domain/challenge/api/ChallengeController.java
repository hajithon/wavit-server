package xyz.wavit.domain.challenge.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wavit.domain.challenge.application.ChallengeService;
import xyz.wavit.domain.challenge.dto.ChallengeCreateRequest;

@Tag(name = "[Challenge]", description = "챌린지 API")
@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "시스템에 의한 챌린지 생성", description = "시스템에 의해 지정된 사용자들에게 챌린지를 생성합니다.")
    @PostMapping("/system")
    public ResponseEntity<Void> createChallengeBySystem(@Valid @RequestBody ChallengeCreateRequest request) {
        challengeService.createChallengeBySystem(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "현재 사용자에 의한 챌린지 생성", description = "현재 사용자가 지정한 사용자를 지목하여 챌린지를 생성합니다.")
    @PostMapping("/user")
    public ResponseEntity<Void> createChallengeByUser(@Valid @RequestBody ChallengeCreateRequest request) {
        challengeService.createChallengeByUser(request);
        return ResponseEntity.ok().build();
    }
}
