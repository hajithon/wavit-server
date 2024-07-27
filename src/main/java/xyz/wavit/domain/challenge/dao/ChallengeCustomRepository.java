package xyz.wavit.domain.challenge.dao;

import java.time.LocalDate;
import org.springframework.data.domain.Slice;
import xyz.wavit.domain.challenge.domain.Challenge;

public interface ChallengeCustomRepository {

    Long findChallengeOrderForToday(Long challengeId, LocalDate today);

    Slice<Challenge> findTodayCompletedChallenges(int size, Long lastId);
}
