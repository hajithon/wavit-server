package xyz.wavit.domain.challenge.domain;

import java.util.List;
import org.springframework.stereotype.Component;
import xyz.wavit.global.exception.CustomException;
import xyz.wavit.global.exception.ErrorCode;

@Component
public class ChallengeValidator {

    public void validateAlreadyNominated(List<Challenge> challengesByNominee) {
        // 이미 지목된 사용자에게 챌린지를 생성할 수 없음
        if (!challengesByNominee.isEmpty()) {
            throw CustomException.from(ErrorCode.CHALLENGE_USER_ALREADY_NOMINATED);
        }
    }
}
