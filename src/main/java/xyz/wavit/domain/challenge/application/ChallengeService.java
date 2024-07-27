package xyz.wavit.domain.challenge.application;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wavit.domain.challenge.dao.ChallengeRepository;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.challenge.domain.ChallengeValidator;
import xyz.wavit.domain.challenge.dto.ChallengeCreateRequest;
import xyz.wavit.domain.challenge.dto.ChallengeIncompleteDto;
import xyz.wavit.domain.common.model.ImageUploadStatus;
import xyz.wavit.domain.user.dao.UserRepository;
import xyz.wavit.domain.user.domain.User;
import xyz.wavit.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeValidator challengeValidator;

    @Transactional
    public void createChallengeBySystem(ChallengeCreateRequest request) {
        List<User> nominatedUsers = userRepository.findAllById(request.nominatedUserIds());

        validateAlreadyNominated(nominatedUsers);

        List<Challenge> challenges =
                nominatedUsers.stream().map(Challenge::createBySystem).toList();

        challengeRepository.saveAll(challenges);

        log.info("[ChallengeService] 시스템에 의한 챌린지 생성: ids={}", request.nominatedUserIds());
    }

    @Transactional
    public void createChallengeByUser(ChallengeCreateRequest request) {
        User nominator = userUtil.getCurrentUser();

        List<User> nominatedUsers = userRepository.findAllById(request.nominatedUserIds());

        validateAlreadyNominated(nominatedUsers);

        List<Challenge> challenges = nominatedUsers.stream()
                .map(nominatee -> Challenge.createByUser(nominator, nominatee))
                .toList();

        challengeRepository.saveAll(challenges);

        log.info("[ChallengeService] 사용자에 의한 챌린지 생성: ids={}", request.nominatedUserIds());
    }

    private void validateAlreadyNominated(List<User> nominatedUsers) {
        nominatedUsers.forEach(nominee -> {
            var challengesByNominee = challengeRepository.findByChallengedUser(nominee);
            challengeValidator.validateAlreadyNominated(challengesByNominee);
        });
    }

    @Transactional(readOnly = true)
    public ChallengeIncompleteDto getMyIncompleteChallenge() {
        User currentUser = userUtil.getCurrentUser();

        return challengeRepository
                .findByUploadStatusAndChallengedUser(ImageUploadStatus.PENDING, currentUser)
                .filter(this::isToday)
                .map(challenge -> ChallengeIncompleteDto.of(challenge, getTodayChallengeCount(challenge)))
                .orElse(ChallengeIncompleteDto.createEmpty());
    }

    private boolean isToday(Challenge challenge) {
        return challenge.getCreatedAt().toLocalDate().equals(LocalDate.now());
    }

    private Long getTodayChallengeCount(Challenge challenge) {
        return challengeRepository.findChallengeOrderForToday(challenge.getId(), LocalDate.now());
    }
}
