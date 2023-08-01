package youtube.domain.member.persist;

import jakarta.persistence.*;
import youtube.domain.BaseTimeEntity;
import youtube.domain.member.vo.Gender;
import youtube.domain.member.vo.RoleType;

import java.time.LocalDate;

@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String loginId;

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
