package xyz.wavit.domain.user.dao;

import static xyz.wavit.domain.challenge.domain.QChallenge.*;
import static xyz.wavit.domain.user.domain.QUser.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import xyz.wavit.domain.common.model.ImageUploadStatus;
import xyz.wavit.domain.user.domain.User;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 현재 PENDING인 챌린지를 가지지 않은 유저를 limit만큼, 최근 가입한 순으로 조회한다.
     * 자기 자신은 조회되지 않는다.
     */
    @Override
    public List<User> findCandidateUsers(User currentUser, int limit) {
        return queryFactory
                .selectDistinct(user)
                .leftJoin(challenge)
                .on(challenge.challengedUser.eq(user).and(challenge.uploadStatus.eq(ImageUploadStatus.PENDING)))
                .where(user.ne(currentUser), challenge.isNull())
                .orderBy(user.createdAt.desc())
                .limit(limit)
                .fetch();
    }
}
