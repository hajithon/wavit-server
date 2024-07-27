package xyz.wavit.domain.challenge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wavit.domain.challenge.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {}
