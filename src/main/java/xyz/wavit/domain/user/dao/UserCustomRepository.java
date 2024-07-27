package xyz.wavit.domain.user.dao;

import java.util.List;
import xyz.wavit.domain.user.domain.User;

public interface UserCustomRepository {

    List<User> findCandidateUsers(User currentUser, int limit);
}
