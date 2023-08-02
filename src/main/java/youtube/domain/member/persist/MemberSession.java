package youtube.domain.member.persist;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import youtube.domain.member.vo.*;

import java.io.Serializable;
import java.time.LocalDate;

import static youtube.global.constant.SessionConstant.*;

public class MemberSession implements Serializable {

    private long id;

    private Nickname nickname;

    private LoginId loginId;

    private Password password;

    private RoleType roleType;

    private Gender gender;

    private LocalDate birthDate;

    private long likedVideosCount;

    private long watchLaterVideosCount;

    @Builder
    private MemberSession(final long id, final Nickname nickname, final LoginId loginId,
                          final Password password, final RoleType roleType, final Gender gender,
                          final LocalDate birthDate, final long likedVideosCount,
                          final long watchLaterVideosCount) {
        this.id = id;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.roleType = roleType;
        this.gender = gender;
        this.birthDate = birthDate;
        this.likedVideosCount = likedVideosCount;
        this.watchLaterVideosCount = watchLaterVideosCount;
    }

    public void makeSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(MEMBER_SESSION.value, this);
    }
}
