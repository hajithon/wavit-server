package xyz.wavit.domain.challenge.dao;

import java.time.LocalDate;

public interface ChallengeCustomRepository {

    Long findChallengeOrderForToday(Long challengeId, LocalDate today);
}
