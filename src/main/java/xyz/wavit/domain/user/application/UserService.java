package xyz.wavit.domain.user.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wavit.domain.challenge.dao.ChallengeRepository;
import xyz.wavit.domain.user.dao.UserRepository;
import xyz.wavit.domain.user.domain.User;
import xyz.wavit.domain.user.dto.UserWithTotalChallengeDto;
import xyz.wavit.global.util.UserUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserWithTotalChallengeDto> getCandidateUsers() {
        User currentUser = userUtil.getCurrentUser();

        List<User> users = userRepository.findCandidateUsers(currentUser, 10);

        return users.stream()
                .map(user -> UserWithTotalChallengeDto.from(user, challengeRepository.countByChallengedUser(user)))
                .toList();
    }
}
