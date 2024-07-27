package xyz.wavit.domain.challenge.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.common.model.ImageUploadStatus;
import xyz.wavit.domain.user.domain.User;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeCustomRepository {

    Long countByChallengedUser(User challengedUser);

    List<Challenge> findByChallengedUser(User user);

    Optional<Challenge> findByUploadStatusAndChallengedUser(ImageUploadStatus uploadStatus, User challengedUser);

    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    int countByUploadStatusAndCreatedAtBetween(
            ImageUploadStatus imageUploadStatus, LocalDateTime start, LocalDateTime end);
}
