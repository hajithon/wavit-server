package xyz.wavit.domain.challenge.dao;

import static xyz.wavit.domain.challenge.domain.QChallenge.*;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

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
}
