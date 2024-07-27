package xyz.wavit.domain.challenge.dao;

import static xyz.wavit.domain.challenge.domain.QChallenge.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import xyz.wavit.domain.challenge.domain.Challenge;
import xyz.wavit.domain.common.model.ImageUploadStatus;

@RequiredArgsConstructor
public class ChallengeCustomRepositoryImpl implements ChallengeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findChallengeOrderForToday(Long challengeId, LocalDate today) {
        return queryFactory
                .select(challenge.id.count())
                .from(challenge)
                .where(challenge
                        .startAt
                        .goe(LocalDate.now().atStartOfDay())
                        .and(challenge.startAt.lt(JPAExpressions.select(challenge.startAt)
                                .from(challenge)
                                .where(challenge.id.eq(challengeId)))))
                .fetchOne();
    }

    @Override
    public Slice<Challenge> findTodayCompletedChallenges(int size, Long lastId) {
        List<Challenge> challenges = queryFactory
                .selectFrom(challenge)
                .join(challenge.challengedUser)
                .fetchJoin()
                .where(isCompleted().and(isCreatedToday()).and(ltChallengeId(lastId)))
                .orderBy(challenge.completedAt.desc())
                .limit((long) size + 1)
                .fetch();

        boolean hasNext = getHasNext(challenges, size);

        return new SliceImpl<>(challenges, Pageable.ofSize(size), hasNext);
    }

    private static BooleanExpression isCompleted() {
        return challenge.uploadStatus.eq(ImageUploadStatus.COMPLETE);
    }

    private BooleanExpression isCreatedToday() {
        LocalDate now = LocalDate.now();
        return challenge.createdAt.goe(now.atStartOfDay()).and(challenge.createdAt.lt(now.atTime(LocalTime.MAX)));
    }

    private BooleanExpression ltChallengeId(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return challenge.id.lt(lastId);
    }

    private boolean getHasNext(List<?> list, int size) {
        boolean hasNext = false;
        if (list.size() > size) {
            list.remove(size);
            hasNext = true;
        }
        return hasNext;
    }
}
