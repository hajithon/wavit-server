package xyz.wavit.domain.challenge.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wavit.domain.challenge.dao.ChallengeRepository;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.challenge.dto.ChallengeCreateRequest;
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

    @Transactional
    public void createChallengeBySystem(ChallengeCreateRequest request) {
        List<User> nominatedUsers = userRepository.findAllById(request.nominatedUserIds());

        List<Challenge> challenges =
                nominatedUsers.stream().map(Challenge::createBySystem).toList();

        challengeRepository.saveAll(challenges);
    }

    @Transactional
    public void createChallengeByUser(ChallengeCreateRequest request) {
        User nominator = userUtil.getCurrentUser();

        List<User> nominatedUsers = userRepository.findAllById(request.nominatedUserIds());

        List<Challenge> challenges = nominatedUsers.stream()
                .map(nominatee -> Challenge.createByUser(nominator, nominatee))
                .toList();

        challengeRepository.saveAll(challenges);
    }
}
