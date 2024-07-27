package xyz.wavit.domain.challenge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.user.domain.User;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    int countByChallengedUser(User challengedUser);
}
