package youtube.domain.member.persist;

import jakarta.persistence.*;
import lombok.Builder;
import youtube.domain.BaseTimeEntity;
import youtube.domain.member.vo.*;

import java.time.LocalDate;

@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Nickname nickname;

    @Embedded
    private LoginId loginId;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    private long likedVideosCount;

    private long watchLaterVideosCount;

    protected Member() {
    }

    @Builder
    private Member(final Nickname nickname, final LoginId loginId, final Password password,
                   final RoleType roleType, final Gender gender, final LocalDate birthDate,
                   final long likedVideosCount, final long watchLaterVideosCount) {
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.roleType = roleType;
        this.gender = gender;
        this.birthDate = birthDate;
        this.likedVideosCount = likedVideosCount;
        this.watchLaterVideosCount = watchLaterVideosCount;
    }
}
