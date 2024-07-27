package xyz.wavit.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import xyz.wavit.domain.common.model.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Comment("사용자 권한")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Comment("사용자 이름")
    private String name;

    @Comment("사용자 닉네임")
    private String nickname;

    @Comment("사용자 ID")
    private String username;

    @Comment("사용자 비밀번호")
    private String password;

    private String profileImageUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private User(UserRole role, String name, String nickname, String username, String password) {
        this.role = role;
        this.name = name;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
    }

    public static User create(String name, String nickname, String username, String password) {
        return User.builder()
                .role(UserRole.USER)
                .name(name)
                .nickname(nickname)
                .username(username)
                .password(password)
                .build();
    }
}
