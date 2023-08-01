package youtube.domain.member.persist;

import jakarta.persistence.*;
import lombok.Getter;
import youtube.domain.BaseTimeEntity;
import youtube.domain.member.vo.*;

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
}
