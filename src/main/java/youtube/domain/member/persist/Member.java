package youtube.domain.member.persist;

import jakarta.persistence.*;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.LoginId;
import youtube.domain.member.vo.Nickname;
import youtube.domain.member.vo.RoleType;

import java.time.LocalDate;

@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Nickname nickname;

    @Embedded
    private LoginId loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    private long likedVideosCount;

    private long watchLaterVideosCount;

    protected Member() {
    }
}
